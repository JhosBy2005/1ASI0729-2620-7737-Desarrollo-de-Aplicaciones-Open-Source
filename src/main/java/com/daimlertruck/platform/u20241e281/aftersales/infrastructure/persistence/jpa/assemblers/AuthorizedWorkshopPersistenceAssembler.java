package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.assemblers;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.entities.AuthorizedWorkshopPersistenceEntity;
import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PersonName;
import com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;

/**
 * Static assembler between the {@link AuthorizedWorkshop} domain aggregate
 * and its {@link AuthorizedWorkshopPersistenceEntity} representation.
 *
 * @author José Antonio Santana Luna
 */
public final class AuthorizedWorkshopPersistenceAssembler {

    private AuthorizedWorkshopPersistenceAssembler() {
    }

    /**
     * Converts a persistence entity into a domain aggregate.
     *
     * @param entity the persistence entity
     * @return the domain aggregate, or {@code null}
     */
    public static AuthorizedWorkshop toDomainFromPersistence(AuthorizedWorkshopPersistenceEntity entity) {
        if (entity == null) return null;
        return AuthorizedWorkshop.reconstitute(
                entity.getId(),
                entity.getWorkshopId(),
                new PersonName(entity.getManagerName().getFirstName(), entity.getManagerName().getLastName()),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.getWorkshopLegalName(),
                entity.getCity(),
                entity.getProvinceOrState(),
                entity.getCountryOrRegion(),
                entity.getEncryptedAuthCode());
    }

    /**
     * Converts a domain aggregate into a persistence entity.
     *
     * @param aggregate the domain aggregate
     * @return the persistence entity
     */
    public static AuthorizedWorkshopPersistenceEntity toPersistenceFromDomain(AuthorizedWorkshop aggregate) {
        var entity = new AuthorizedWorkshopPersistenceEntity();
        entity.setId(aggregate.getId());
        entity.setWorkshopId(aggregate.getWorkshopId());
        entity.setManagerName(new PersonNamePersistenceEmbeddable(
                aggregate.getManagerName().firstName(), aggregate.getManagerName().lastName()));
        entity.setContactPhone(aggregate.getContactPhone());
        entity.setContactEmail(aggregate.getContactEmail());
        entity.setWorkshopLegalName(aggregate.getWorkshopLegalName());
        entity.setCity(aggregate.getCity());
        entity.setProvinceOrState(aggregate.getProvinceOrState());
        entity.setCountryOrRegion(aggregate.getCountryOrRegion());
        entity.setEncryptedAuthCode(aggregate.getEncryptedAuthCode());
        return entity;
    }
}
