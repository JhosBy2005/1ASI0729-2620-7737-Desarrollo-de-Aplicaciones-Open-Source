package com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.assemblers;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.embeddables.SampleVolumePersistenceEmbeddable;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.embeddables.SamplingWindowPersistenceEmbeddable;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.entities.SamplingPlanPersistenceEntity;

/**
 * Static assembler between the {@link SamplingPlan} domain aggregate
 * and its {@link SamplingPlanPersistenceEntity} representation.
 */
public final class SamplingPlanPersistenceAssembler {

    private SamplingPlanPersistenceAssembler() {
    }

    /**
     * Converts a persistence entity into a domain aggregate.
     *
     * @param entity the persistence entity
     * @return the domain aggregate, or {@code null}
     */
    public static SamplingPlan toDomainFromPersistence(SamplingPlanPersistenceEntity entity) {
        if (entity == null) return null;
        return SamplingPlan.reconstitute(
                entity.getId(),
                entity.getPlanCode(),
                entity.getStationId(),
                new SamplingWindow(entity.getSamplingWindow().getStartDate(), entity.getSamplingWindow().getEndDate()),
                new SampleVolume(entity.getSampleVolume().getQuantity(), entity.getSampleVolume().getUnit()),
                entity.getPriorityLevel(),
                entity.getStatus(),
                entity.getSamplingNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Converts a domain aggregate into a persistence entity.
     *
     * @param aggregate the domain aggregate
     * @return the persistence entity
     */
    public static SamplingPlanPersistenceEntity toPersistenceFromDomain(SamplingPlan aggregate) {
        var entity = new SamplingPlanPersistenceEntity();
        entity.setId(aggregate.getId());
        entity.setPlanCode(aggregate.getPlanCode());
        entity.setStationId(aggregate.getStationId());
        entity.setSamplingWindow(new SamplingWindowPersistenceEmbeddable(
                aggregate.getSamplingWindow().startDate(), aggregate.getSamplingWindow().endDate()));
        entity.setSampleVolume(new SampleVolumePersistenceEmbeddable(
                aggregate.getSampleVolume().quantity(), aggregate.getSampleVolume().unit()));
        entity.setPriorityLevel(aggregate.getPriorityLevel());
        entity.setStatus(aggregate.getStatus());
        entity.setSamplingNotes(aggregate.getSamplingNotes());
        return entity;
    }
}
