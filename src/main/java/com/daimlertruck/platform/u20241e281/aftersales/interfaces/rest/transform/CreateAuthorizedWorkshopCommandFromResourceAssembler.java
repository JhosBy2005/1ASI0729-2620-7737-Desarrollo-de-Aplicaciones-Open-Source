package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.transform;

import com.daimlertruck.platform.u20241e281.aftersales.application.commands.CreateAuthorizedWorkshopCommand;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources.CreateAuthorizedWorkshopResource;

/**
 * Assembler that converts a {@link CreateAuthorizedWorkshopResource} into a {@link CreateAuthorizedWorkshopCommand}.
 *
 * @author José Antonio Santana Luna
 */
public final class CreateAuthorizedWorkshopCommandFromResourceAssembler {

    private CreateAuthorizedWorkshopCommandFromResourceAssembler() {
    }

    /**
     * Converts the request resource into a command.
     *
     * @param resource the request resource
     * @return the create command
     */
    public static CreateAuthorizedWorkshopCommand toCommandFromResource(CreateAuthorizedWorkshopResource resource) {
        return new CreateAuthorizedWorkshopCommand(
                resource.managerFirstName(),
                resource.managerLastName(),
                resource.contactPhone(),
                resource.contactEmail(),
                resource.workshopLegalName(),
                resource.city(),
                resource.provinceOrState(),
                resource.countryOrRegion(),
                resource.maintenanceAuthCode());
    }
}
