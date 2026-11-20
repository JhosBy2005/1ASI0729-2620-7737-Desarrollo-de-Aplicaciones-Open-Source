package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.transform;

import com.aquamonitor.platform.u20241e281.sampling.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.StationId;
import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.resources.CreateSamplingPlanResource;

import java.time.LocalDate;

/**
 * Maps a validated {@link CreateSamplingPlanResource} into a {@link CreateSamplingPlanCommand}.
 */
public final class CreateSamplingPlanCommandMapper {

    private CreateSamplingPlanCommandMapper() {
    }

    /**
     * Builds the command and its value objects. An omitted status is mapped to {@code null},
     * so the domain assigns the default status.
     *
     * @param resource the request resource, already validated
     * @return the command
     * @throws IllegalArgumentException when a value object rejects its data
     */
    public static CreateSamplingPlanCommand toCommandFromResource(CreateSamplingPlanResource resource) {
        var status = resource.isStatusPresent()
                ? SamplingPlanStatus.findByName(resource.getStatus())
                        .orElseThrow(() -> new IllegalArgumentException("Status is not valid"))
                : null;
        return new CreateSamplingPlanCommand(
                new PlanCode(resource.getPlanCode()),
                StationId.of(resource.getStationId()),
                new SamplingWindow(LocalDate.parse(resource.getSamplingWindowStartDate()),
                        LocalDate.parse(resource.getSamplingWindowEndDate())),
                new SampleVolume(resource.getSampleVolumeQuantity(), resource.getSampleVolumeUnit()),
                resource.getPriorityLevel(),
                status,
                resource.getSamplingNotes());
    }
}
