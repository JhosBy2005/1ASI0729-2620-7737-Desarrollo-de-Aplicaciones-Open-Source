package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.repositories;

import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.entities.AuthorizedWorkshopPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link AuthorizedWorkshopPersistenceEntity}.
 *
 * @author José Antonio Santana Luna
 */
@Repository
public interface AuthorizedWorkshopPersistenceRepository extends JpaRepository<AuthorizedWorkshopPersistenceEntity, Long> {

    /**
     * Counts the workshops registered for a manager, workshop legal name and country or region.
     *
     * @param firstName         the manager first name
     * @param lastName          the manager last name
     * @param workshopLegalName the workshop legal name
     * @param countryOrRegion   the country or region
     * @return the number of matching workshops
     */
    @Query("""
            select count(w) from AuthorizedWorkshopPersistenceEntity w
            where w.managerName.firstName = :firstName
              and w.managerName.lastName = :lastName
              and w.workshopLegalName = :workshopLegalName
              and w.countryOrRegion = :countryOrRegion
            """)
    long countByManagerNameAndWorkshopLegalNameAndCountryOrRegion(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("workshopLegalName") String workshopLegalName,
            @Param("countryOrRegion") String countryOrRegion);
}
