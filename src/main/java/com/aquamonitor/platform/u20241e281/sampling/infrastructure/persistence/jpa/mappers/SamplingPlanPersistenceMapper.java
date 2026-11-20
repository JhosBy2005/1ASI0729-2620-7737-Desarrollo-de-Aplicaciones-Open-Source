package com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.mappers;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.StationId;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.entities.SamplingPlanJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Maps the {@link SamplingPlan} aggregate to and from its {@link SamplingPlanJpaEntity}.
 */
@Component
public class SamplingPlanPersistenceMapper {

    /**
     * Converts the aggregate into a JPA entity.
     *
     * @param samplingPlan the aggregate
     * @return the JPA entity
     */
    public SamplingPlanJpaEntity toEntity(SamplingPlan samplingPlan) {
        var entity = new SamplingPlanJpaEntity();
        entity.setId(samplingPlan.getId());
        entity.setPlanCode(samplingPlan.getPlanCode().code());
        entity.setStationId(samplingPlan.getStationId().value().toString());
        entity.setSamplingWindowStartDate(samplingPlan.getSamplingWindow().startDate());
        entity.setSamplingWindowEndDate(samplingPlan.getSamplingWindow().endDate());
        entity.setSampleVolumeQuantity(samplingPlan.getSampleVolume().quantity());
        entity.setSampleVolumeUnit(samplingPlan.getSampleVolume().unit());
        entity.setPriorityLevel(samplingPlan.getPriorityLevel());
        entity.setStatus(samplingPlan.getStatus());
        entity.setSamplingNotes(samplingPlan.getSamplingNotes());
        entity.setCreatedAt(samplingPlan.getCreatedAt());
        entity.setUpdatedAt(samplingPlan.getUpdatedAt());
        return entity;
    }

    /**
     * Converts a JPA entity into the aggregate.
     *
     * @param entity the JPA entity
     * @return the aggregate
     */
    public SamplingPlan toDomain(SamplingPlanJpaEntity entity) {
        return SamplingPlan.rehydrate(
                entity.getId(),
                new PlanCode(entity.getPlanCode()),
                StationId.of(entity.getStationId()),
                new SamplingWindow(entity.getSamplingWindowStartDate(), entity.getSamplingWindowEndDate()),
                new SampleVolume(entity.getSampleVolumeQuantity(), entity.getSampleVolumeUnit()),
                entity.getPriorityLevel(),
                entity.getStatus(),
                entity.getSamplingNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
