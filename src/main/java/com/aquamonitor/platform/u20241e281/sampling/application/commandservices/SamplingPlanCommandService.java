package com.aquamonitor.platform.u20241e281.sampling.application.commandservices;

import com.aquamonitor.platform.u20241e281.sampling.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.shared.application.result.Result;

/**
 * Command service for the {@link SamplingPlan} aggregate.
 */
public interface SamplingPlanCommandService {

    /**
     * Handles the creation of a sampling plan.
     *
     * @param command the {@link CreateSamplingPlanCommand}
     * @return the created plan, or a {@link SamplingPlanCommandFailure}
     */
    Result<SamplingPlan, SamplingPlanCommandFailure> handle(CreateSamplingPlanCommand command);
}
