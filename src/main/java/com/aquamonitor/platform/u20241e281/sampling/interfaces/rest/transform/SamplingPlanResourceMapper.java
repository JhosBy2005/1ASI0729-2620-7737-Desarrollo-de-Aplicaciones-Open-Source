package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.transform;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.resources.SamplingPlanResource;

/**
 * Maps a {@link SamplingPlan} aggregate into a {@link SamplingPlanResource}.
 */
public final class SamplingPlanResourceMapper {

    private SamplingPlanResourceMapper() {
    }

    /**
     * Flattens the aggregate value objects into the response resource. Dates use ISO 8601 (yyyy-MM-dd).
     *
     * @param samplingPlan the aggregate
     * @return the response resource
     */
    public static SamplingPlanResource toResourceFromEntity(SamplingPlan samplingPlan) {
        return new SamplingPlanResource(
                samplingPlan.getId(),
                samplingPlan.getPlanCode().code(),
                samplingPlan.getStationId().value().toString(),
                samplingPlan.getSamplingWindow().startDate().toString(),
                samplingPlan.getSamplingWindow().endDate().toString(),
                samplingPlan.getSampleVolume().quantity(),
                samplingPlan.getSampleVolume().unit(),
                samplingPlan.getPriorityLevel(),
                samplingPlan.getStatus().name(),
                samplingPlan.getSamplingNotes());
    }
}
