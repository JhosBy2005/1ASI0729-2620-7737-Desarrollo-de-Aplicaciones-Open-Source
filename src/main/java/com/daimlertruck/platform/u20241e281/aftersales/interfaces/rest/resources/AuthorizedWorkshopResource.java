package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource with the information of an authorized workshop.
 * The encrypted authorization code is intentionally not exposed.
 *
 * @param id                the database id
 * @param workshopId        the Daimler workshop id (GUID)
 * @param managerFirstName  the workshop manager first name
 * @param managerLastName   the workshop manager last name
 * @param contactEmail      the contact e-mail address
 * @param contactPhone      the contact phone number
 * @param workshopLegalName the workshop legal name
 * @param city              the city
 * @param provinceOrState   the province or state
 * @param countryOrRegion   the country or region
 * @author José Antonio Santana Luna
 */
@Schema(name = "AuthorizedWorkshopResponse", description = "Authorized workshop information")
public record AuthorizedWorkshopResource(
        @Schema(example = "1") Long id,
        @Schema(example = "3f2b9c1e-7a4d-4c2e-9b1a-8f6d2e4c1a7b") String workshopId,
        @Schema(example = "John") String managerFirstName,
        @Schema(example = "Doe") String managerLastName,
        @Schema(example = "john.doe@workshop.com") String contactEmail,
        @Schema(example = "+51987654321") String contactPhone,
        @Schema(example = "Lima Trucks S.A.C.") String workshopLegalName,
        @Schema(example = "Lima") String city,
        @Schema(example = "Lima") String provinceOrState,
        @Schema(example = "Peru") String countryOrRegion) {
}
