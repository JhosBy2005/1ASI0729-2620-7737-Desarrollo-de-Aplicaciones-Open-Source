package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.transform;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources.AuthorizedWorkshopResource;

/**
 * Assembler that converts an {@link AuthorizedWorkshop} aggregate into an {@link AuthorizedWorkshopResource}.
 *
 * @author José Antonio Santana Luna
 */
public final class AuthorizedWorkshopResourceFromEntityAssembler {

    private AuthorizedWorkshopResourceFromEntityAssembler() {
    }

    /**
     * Converts the aggregate into its response resource, excluding the encrypted authorization code.
     *
     * @param entity the authorized workshop aggregate
     * @return the response resource
     */
    public static AuthorizedWorkshopResource toResourceFromEntity(AuthorizedWorkshop entity) {
        return new AuthorizedWorkshopResource(
                entity.getId(),
                entity.getWorkshopId().toString(),
                entity.getManagerName().firstName(),
                entity.getManagerName().lastName(),
                entity.getContactEmail().address(),
                entity.getContactPhone().number(),
                entity.getWorkshopLegalName(),
                entity.getCity(),
                entity.getProvinceOrState(),
                entity.getCountryOrRegion());
    }
}
