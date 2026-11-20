package com.aquamonitor.platform.u20241e281.sampling.application.commands;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.StationId;

/**
 * Command to register a new sampling plan, expressed with domain value objects.
 *
 * @param planCode       the plan code
 * @param stationId      the monitoring station id
 * @param samplingWindow the sampling window
 * @param sampleVolume   the volume to collect
 * @param priorityLevel  the priority level, from 1 to 5
 * @param status         the status, or {@code null} when it was omitted (PENDING is assigned)
 * @param samplingNotes  optional notes
 */
public record CreateSamplingPlanCommand(
        PlanCode planCode,
        StationId stationId,
        SamplingWindow samplingWindow,
        SampleVolume sampleVolume,
        Integer priorityLevel,
        SamplingPlanStatus status,
        String samplingNotes) {
}
