package com.aquamonitor.platform.u20241e281.sampling.application.internal.commandservices;

import com.aquamonitor.platform.u20241e281.sampling.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.u20241e281.sampling.application.commandservices.SamplingPlanCommandFailure;
import com.aquamonitor.platform.u20241e281.sampling.application.commandservices.SamplingPlanCommandService;
import com.aquamonitor.platform.u20241e281.sampling.domain.exceptions.DuplicatePlanCodeException;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.domain.repositories.SamplingPlanRepository;
import com.aquamonitor.platform.u20241e281.shared.application.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;

/**
 * Implementation of {@link SamplingPlanCommandService}.
 *
 * <p>Checks the uniqueness of the plan code, creates the aggregate with the current instant
 * (taken from the {@link Clock}) and saves it. A UNIQUE constraint violation detected by the
 * database is also reported as a duplicated plan code.</p>
 */
@Service
public class SamplingPlanCommandServiceImpl implements SamplingPlanCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SamplingPlanCommandServiceImpl.class);

    private final SamplingPlanRepository samplingPlanRepository;
    private final Clock clock;

    /**
     * Constructor.
     *
     * @param samplingPlanRepository the {@link SamplingPlanRepository}
     * @param clock                  the clock used for audit instants
     */
    public SamplingPlanCommandServiceImpl(SamplingPlanRepository samplingPlanRepository, Clock clock) {
        this.samplingPlanRepository = samplingPlanRepository;
        this.clock = clock;
    }

    // inherited javadoc
    @Override
    public Result<SamplingPlan, SamplingPlanCommandFailure> handle(CreateSamplingPlanCommand command) {
        try {
            var samplingPlan = SamplingPlan.create(command.planCode(), command.stationId(), command.samplingWindow(),
                    command.sampleVolume(), command.priorityLevel(), command.status(), command.samplingNotes(),
                    clock.instant());
            if (samplingPlanRepository.existsByPlanCode(samplingPlan.getPlanCode())) {
                return Result.failure(new SamplingPlanCommandFailure.DuplicatePlanCode(samplingPlan.getPlanCode().code()));
            }
            return Result.success(samplingPlanRepository.save(samplingPlan));
        } catch (DuplicatePlanCodeException e) {
            return Result.failure(new SamplingPlanCommandFailure.DuplicatePlanCode(command.planCode().code()));
        } catch (IllegalArgumentException e) {
            return Result.failure(new SamplingPlanCommandFailure.InvalidData(e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Unexpected error while creating a sampling plan", e);
            return Result.failure(new SamplingPlanCommandFailure.Unexpected());
        }
    }
}
