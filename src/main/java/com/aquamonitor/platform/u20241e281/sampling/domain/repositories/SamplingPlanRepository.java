package com.aquamonitor.platform.u20241e281.sampling.domain.repositories;

import com.aquamonitor.platform.u20241e281.sampling.domain.exceptions.DuplicatePlanCodeException;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;

/**
 * Repository port for the {@link SamplingPlan} aggregate.
 */
public interface SamplingPlanRepository {

    /**
     * Saves a sampling plan.
     *
     * @param samplingPlan the plan to save
     * @return the saved plan with its id
     * @throws DuplicatePlanCodeException when the UNIQUE constraint of the plan code is violated
     */
    SamplingPlan save(SamplingPlan samplingPlan);

    /**
     * Checks whether a plan code is already registered.
     *
     * @param planCode the plan code
     * @return {@code true} when it exists
     */
    boolean existsByPlanCode(PlanCode planCode);
}
