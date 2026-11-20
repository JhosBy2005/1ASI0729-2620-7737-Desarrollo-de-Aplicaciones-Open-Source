package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources;

import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.EmailAddress;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request resource to register a new authorized workshop.
 *
 * @param managerFirstName    the workshop manager first name
 * @param managerLastName     the workshop manager last name
 * @param contactPhone        the contact phone number in international format
 * @param contactEmail        the contact e-mail address
 * @param workshopLegalName   the workshop legal name
 * @param city                the city
 * @param provinceOrState     the province or state, optional
 * @param countryOrRegion     the country or region
 * @param maintenanceAuthCode the 8-character maintenance authorization code
 * @author José Antonio Santana Luna
 */
@Schema(name = "CreateAuthorizedWorkshopRequest", description = "Request payload to register an authorized workshop")
public record CreateAuthorizedWorkshopResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Workshop manager first name", example = "John")
        String managerFirstName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Workshop manager last name", example = "Doe")
        String managerLastName,

        @NotBlank(message = "{validation.not-blank}")
        @Pattern(regexp = PhoneNumber.PHONE_REGEX, message = "{validation.phone}")
        @Schema(description = "Contact phone number in international format", example = "+51987654321")
        String contactPhone,

        @NotBlank(message = "{validation.not-blank}")
        @Pattern(regexp = EmailAddress.EMAIL_REGEX, message = "{validation.email}")
        @Schema(description = "Contact e-mail address", example = "john.doe@workshop.com")
        String contactEmail,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Workshop legal name", example = "Lima Trucks S.A.C.")
        String workshopLegalName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "City", example = "Lima")
        String city,

        @Schema(description = "Province or state (optional)", example = "Lima")
        String provinceOrState,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Country or region", example = "Peru")
        String countryOrRegion,

        @NotBlank(message = "{validation.not-blank}")
        @Size(min = 8, max = 8, message = "{validation.auth-code.size}")
        @Schema(description = "Maintenance authorization code (exactly 8 characters)", example = "AB12CD34")
        String maintenanceAuthCode) {
}
