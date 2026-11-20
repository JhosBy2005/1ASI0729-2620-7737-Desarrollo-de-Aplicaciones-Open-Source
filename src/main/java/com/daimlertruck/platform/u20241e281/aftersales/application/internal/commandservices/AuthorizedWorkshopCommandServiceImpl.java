package com.daimlertruck.platform.u20241e281.aftersales.application.internal.commandservices;

import com.daimlertruck.platform.u20241e281.aftersales.application.commands.CreateAuthorizedWorkshopCommand;
import com.daimlertruck.platform.u20241e281.aftersales.application.commandservices.AuthorizedWorkshopCommandService;
import com.daimlertruck.platform.u20241e281.aftersales.application.internal.outboundservices.hashing.HashingService;
import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.aftersales.domain.repositories.AuthorizedWorkshopRepository;
import com.daimlertruck.platform.u20241e281.shared.application.result.ApplicationError;
import com.daimlertruck.platform.u20241e281.shared.application.result.Result;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.EmailAddress;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PersonName;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PhoneNumber;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthorizedWorkshopCommandService}.
 *
 * <p>Applies the business rules for registering authorized workshops:
 * valid e-mail address, valid international phone number, an 8-character
 * maintenance authorization code encrypted with BCrypt, and no duplicated
 * workshop manager for the same workshop legal name in the same country or region.</p>
 *
 * @author José Antonio Santana Luna
 */
@Service
public class AuthorizedWorkshopCommandServiceImpl implements AuthorizedWorkshopCommandService {

    private static final int MAINTENANCE_AUTH_CODE_LENGTH = 8;

    private final AuthorizedWorkshopRepository authorizedWorkshopRepository;
    private final HashingService hashingService;

    /**
     * Constructor.
     *
     * @param authorizedWorkshopRepository the {@link AuthorizedWorkshopRepository}
     * @param hashingService               the {@link HashingService} used to encrypt the authorization code
     */
    public AuthorizedWorkshopCommandServiceImpl(AuthorizedWorkshopRepository authorizedWorkshopRepository,
                                                HashingService hashingService) {
        this.authorizedWorkshopRepository = authorizedWorkshopRepository;
        this.hashingService = hashingService;
    }

    // inherited javadoc
    @Override
    public Result<AuthorizedWorkshop, ApplicationError> handle(CreateAuthorizedWorkshopCommand command) {
        try {
            var managerName = new PersonName(command.managerFirstName(), command.managerLastName());
            var contactPhone = new PhoneNumber(command.contactPhone());
            var contactEmail = new EmailAddress(command.contactEmail());

            var maintenanceAuthCode = command.maintenanceAuthCode();
            if (maintenanceAuthCode == null || maintenanceAuthCode.length() != MAINTENANCE_AUTH_CODE_LENGTH) {
                return Result.failure(ApplicationError.validationError(
                        "maintenanceAuthCode",
                        "Maintenance authorization code must have exactly %d characters"
                                .formatted(MAINTENANCE_AUTH_CODE_LENGTH)));
            }

            if (authorizedWorkshopRepository.existsByManagerNameAndWorkshopLegalNameAndCountryOrRegion(
                    managerName, command.workshopLegalName(), command.countryOrRegion())) {
                return Result.failure(ApplicationError.conflict(
                        "AUTHORIZED_WORKSHOP",
                        "Workshop manager '%s' is already registered for workshop '%s' in '%s'".formatted(
                                managerName.getFullName(), command.workshopLegalName(), command.countryOrRegion())));
            }

            var authorizedWorkshop = new AuthorizedWorkshop(
                    managerName,
                    contactPhone,
                    contactEmail,
                    command.workshopLegalName(),
                    command.city(),
                    command.provinceOrState(),
                    command.countryOrRegion(),
                    hashingService.encode(maintenanceAuthCode));

            return Result.success(authorizedWorkshopRepository.save(authorizedWorkshop));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AuthorizedWorkshop", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Authorized workshop creation", e.getMessage()));
        }
    }
}
