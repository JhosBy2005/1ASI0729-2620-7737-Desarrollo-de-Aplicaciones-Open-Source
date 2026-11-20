package com.daimlertruck.platform.u20241e281.aftersales.application.commands;

/**
 * Command to register a new authorized workshop.
 *
 * @param managerFirstName    the workshop manager first name
 * @param managerLastName     the workshop manager last name
 * @param contactPhone        the contact phone number in international format
 * @param contactEmail        the contact e-mail address
 * @param workshopLegalName   the workshop legal name
 * @param city                the city
 * @param provinceOrState     the province or state, optional
 * @param countryOrRegion     the country or region
 * @param maintenanceAuthCode the 8-character maintenance authorization code (plain text)
 * @author José Antonio Santana Luna
 */
public record CreateAuthorizedWorkshopCommand(
        String managerFirstName,
        String managerLastName,
        String contactPhone,
        String contactEmail,
        String workshopLegalName,
        String city,
        String provinceOrState,
        String countryOrRegion,
        String maintenanceAuthCode) {
}
