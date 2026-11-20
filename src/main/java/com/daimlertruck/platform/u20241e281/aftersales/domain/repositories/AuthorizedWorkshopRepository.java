package com.daimlertruck.platform.u20241e281.aftersales.domain.repositories;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PersonName;

import java.util.Optional;

/**
 * Repository port for the {@link AuthorizedWorkshop} aggregate.
 *
 * @author José Antonio Santana Luna
 */
public interface AuthorizedWorkshopRepository {

    /**
     * Saves an authorized workshop.
     *
     * @param authorizedWorkshop the aggregate to save
     * @return the saved aggregate with its generated id
     */
    AuthorizedWorkshop save(AuthorizedWorkshop authorizedWorkshop);

    /**
     * Finds an authorized workshop by its database id.
     *
     * @param id the database id
     * @return the authorized workshop, if found
     */
    Optional<AuthorizedWorkshop> findById(Long id);

    /**
     * Checks whether a workshop manager with the same name is already registered
     * for the same workshop legal name in the same country or region.
     *
     * @param managerName       the manager first and last name
     * @param workshopLegalName the workshop legal name
     * @param countryOrRegion   the country or region
     * @return {@code true} when such a registration already exists
     */
    boolean existsByManagerNameAndWorkshopLegalNameAndCountryOrRegion(
            PersonName managerName, String workshopLegalName, String countryOrRegion);
}
