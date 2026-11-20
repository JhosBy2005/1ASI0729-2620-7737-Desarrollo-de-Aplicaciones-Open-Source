package com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.valueobjects.DaimlerWorkshopId;
import com.daimlertruck.platform.u20241e281.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.EmailAddress;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PersonName;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PhoneNumber;
import lombok.Getter;

import java.util.Objects;

/**
 * AuthorizedWorkshop aggregate root.
 *
 * <p>Represents a workshop authorized by Daimler Truck to provide after-sales services.
 * It is auditable: creation and last update dates are tracked by the persistence layer.
 * No JPA annotation is present here; persistence concerns live in the infrastructure layer.</p>
 *
 * @author José Antonio Santana Luna
 */
@Getter
public class AuthorizedWorkshop extends AbstractDomainAggregateRoot<AuthorizedWorkshop> {

    private final Long id;
    private final DaimlerWorkshopId workshopId;
    private final PersonName managerName;
    private final PhoneNumber contactPhone;
    private final EmailAddress contactEmail;
    private final String workshopLegalName;
    private final String city;
    private final String provinceOrState;
    private final String countryOrRegion;
    private final String encryptedAuthCode;

    private AuthorizedWorkshop(Long id, DaimlerWorkshopId workshopId, PersonName managerName,
                               PhoneNumber contactPhone, EmailAddress contactEmail, String workshopLegalName,
                               String city, String provinceOrState, String countryOrRegion,
                               String encryptedAuthCode) {
        this.id = id;
        this.workshopId = Objects.requireNonNull(workshopId, "Workshop id must not be null");
        this.managerName = Objects.requireNonNull(managerName, "Manager name must not be null");
        this.contactPhone = Objects.requireNonNull(contactPhone, "Contact phone must not be null");
        this.contactEmail = Objects.requireNonNull(contactEmail, "Contact email must not be null");
        this.workshopLegalName = requireText(workshopLegalName, "Workshop legal name");
        this.city = requireText(city, "City");
        this.provinceOrState = provinceOrState == null || provinceOrState.isBlank() ? null : provinceOrState.trim();
        this.countryOrRegion = requireText(countryOrRegion, "Country or region");
        this.encryptedAuthCode = requireText(encryptedAuthCode, "Encrypted authorization code");
    }

    /**
     * Registers a new authorized workshop, generating its Daimler workshop id.
     *
     * @param managerName       the workshop manager name
     * @param contactPhone      the contact phone number
     * @param contactEmail      the contact e-mail address
     * @param workshopLegalName the workshop legal name
     * @param city              the city
     * @param provinceOrState   the province or state, optional
     * @param countryOrRegion   the country or region
     * @param encryptedAuthCode the BCrypt-encrypted maintenance authorization code
     */
    public AuthorizedWorkshop(PersonName managerName, PhoneNumber contactPhone, EmailAddress contactEmail,
                              String workshopLegalName, String city, String provinceOrState,
                              String countryOrRegion, String encryptedAuthCode) {
        this(null, new DaimlerWorkshopId(), managerName, contactPhone, contactEmail, workshopLegalName,
                city, provinceOrState, countryOrRegion, encryptedAuthCode);
    }

    /**
     * Reconstitutes an existing authorized workshop from persistence state.
     *
     * @param id                the database identifier
     * @param workshopId        the Daimler workshop id
     * @param managerName       the workshop manager name
     * @param contactPhone      the contact phone number
     * @param contactEmail      the contact e-mail address
     * @param workshopLegalName the workshop legal name
     * @param city              the city
     * @param provinceOrState   the province or state
     * @param countryOrRegion   the country or region
     * @param encryptedAuthCode the encrypted maintenance authorization code
     * @return the reconstituted authorized workshop
     */
    public static AuthorizedWorkshop reconstitute(Long id, DaimlerWorkshopId workshopId, PersonName managerName,
                                                  PhoneNumber contactPhone, EmailAddress contactEmail,
                                                  String workshopLegalName, String city, String provinceOrState,
                                                  String countryOrRegion, String encryptedAuthCode) {
        return new AuthorizedWorkshop(id, workshopId, managerName, contactPhone, contactEmail, workshopLegalName,
                city, provinceOrState, countryOrRegion, encryptedAuthCode);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("%s must not be null or blank".formatted(fieldName));
        }
        return value.trim();
    }
}
