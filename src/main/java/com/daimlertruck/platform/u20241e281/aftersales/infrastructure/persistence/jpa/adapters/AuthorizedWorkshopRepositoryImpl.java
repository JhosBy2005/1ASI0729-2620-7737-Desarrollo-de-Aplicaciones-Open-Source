package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.adapters;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.aftersales.domain.repositories.AuthorizedWorkshopRepository;
import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.assemblers.AuthorizedWorkshopPersistenceAssembler;
import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.repositories.AuthorizedWorkshopPersistenceRepository;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PersonName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository adapter that implements the {@link AuthorizedWorkshopRepository} domain port
 * using Spring Data JPA.
 *
 * @author José Antonio Santana Luna
 */
@Repository
public class AuthorizedWorkshopRepositoryImpl implements AuthorizedWorkshopRepository {

    private final AuthorizedWorkshopPersistenceRepository persistenceRepository;

    /**
     * Constructor.
     *
     * @param persistenceRepository the Spring Data JPA repository
     */
    public AuthorizedWorkshopRepositoryImpl(AuthorizedWorkshopPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    // inherited javadoc
    @Override
    public AuthorizedWorkshop save(AuthorizedWorkshop authorizedWorkshop) {
        var savedEntity = persistenceRepository.save(
                AuthorizedWorkshopPersistenceAssembler.toPersistenceFromDomain(authorizedWorkshop));
        return AuthorizedWorkshopPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    // inherited javadoc
    @Override
    public Optional<AuthorizedWorkshop> findById(Long id) {
        return persistenceRepository.findById(id).map(AuthorizedWorkshopPersistenceAssembler::toDomainFromPersistence);
    }

    // inherited javadoc
    @Override
    public boolean existsByManagerNameAndWorkshopLegalNameAndCountryOrRegion(
            PersonName managerName, String workshopLegalName, String countryOrRegion) {
        return persistenceRepository.countByManagerNameAndWorkshopLegalNameAndCountryOrRegion(
                managerName.firstName(), managerName.lastName(),
                workshopLegalName == null ? null : workshopLegalName.trim(),
                countryOrRegion == null ? null : countryOrRegion.trim()) > 0;
    }
}
