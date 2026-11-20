package com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.StationId;
import com.aquamonitor.platform.u20241e281.shared.domain.model.aggregates.AuditableAbstractDomainAggregateRoot;

import java.time.Instant;
import java.util.Objects;

/**
 * SamplingPlan aggregate root: plan to collect water samples at a monitoring station.
 *
 * <p>It is auditable: when it is created, {@code createdAt} and {@code updatedAt} receive the same instant.</p>
 */
public final class SamplingPlan extends AuditableAbstractDomainAggregateRoot {

    /** Lowest priority level. */
    public static final int MIN_PRIORITY_LEVEL = 1;

    /** Highest priority level. */
    public static final int MAX_PRIORITY_LEVEL = 5;

    private final Long id;
    private final PlanCode planCode;
    private final StationId stationId;
    private final SamplingWindow samplingWindow;
    private final SampleVolume sampleVolume;
    private final Integer priorityLevel;
    private final SamplingPlanStatus status;
    private final String samplingNotes;

    private SamplingPlan(Long id, PlanCode planCode, StationId stationId, SamplingWindow samplingWindow,
                         SampleVolume sampleVolume, Integer priorityLevel, SamplingPlanStatus status,
                         String samplingNotes, Instant createdAt, Instant updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.planCode = requireArgument(planCode, "Plan code is required");
        this.stationId = requireArgument(stationId, "Station id is required");
        this.samplingWindow = requireArgument(samplingWindow, "Sampling window is required");
        this.sampleVolume = requireArgument(sampleVolume, "Sample volume is required");
        this.priorityLevel = validatePriorityLevel(priorityLevel);
        this.status = requireArgument(status, "Status must not be null");
        this.samplingNotes = samplingNotes;
    }

    /**
     * Creates a new sampling plan. When {@code status} is {@code null} (omitted) it is {@link SamplingPlanStatus#PENDING}.
     *
     * @param planCode       the plan code
     * @param stationId      the monitoring station id
     * @param samplingWindow the sampling window
     * @param sampleVolume   the volume to collect
     * @param priorityLevel  the priority level, from 1 (lowest) to 5 (highest)
     * @param status         the initial status, or {@code null} for the default status
     * @param samplingNotes  optional notes
     * @param now            the creation instant, used for both audit fields
     * @return the new sampling plan
     * @throws IllegalArgumentException when a business rule is violated
     */
    public static SamplingPlan create(PlanCode planCode, StationId stationId, SamplingWindow samplingWindow,
                                      SampleVolume sampleVolume, Integer priorityLevel, SamplingPlanStatus status,
                                      String samplingNotes, Instant now) {
        return new SamplingPlan(null, planCode, stationId, samplingWindow, sampleVolume, priorityLevel,
                Objects.requireNonNullElse(status, SamplingPlanStatus.PENDING), samplingNotes, now, now);
    }

    /**
     * Rebuilds a persisted sampling plan.
     *
     * @param id             the database id
     * @param planCode       the plan code
     * @param stationId      the monitoring station id
     * @param samplingWindow the sampling window
     * @param sampleVolume   the volume to collect
     * @param priorityLevel  the priority level
     * @param status         the status
     * @param samplingNotes  the notes
     * @param createdAt      the creation instant
     * @param updatedAt      the last update instant
     * @return the sampling plan
     */
    public static SamplingPlan rehydrate(Long id, PlanCode planCode, StationId stationId,
                                         SamplingWindow samplingWindow, SampleVolume sampleVolume,
                                         Integer priorityLevel, SamplingPlanStatus status, String samplingNotes,
                                         Instant createdAt, Instant updatedAt) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid sampling plan id");
        }
        return new SamplingPlan(id, planCode, stationId, samplingWindow, sampleVolume, priorityLevel, status,
                samplingNotes, createdAt, updatedAt);
    }

    private static Integer validatePriorityLevel(Integer priorityLevel) {
        if (priorityLevel == null || priorityLevel < MIN_PRIORITY_LEVEL || priorityLevel > MAX_PRIORITY_LEVEL) {
            throw new IllegalArgumentException("Priority level is required and must be between %d and %d"
                    .formatted(MIN_PRIORITY_LEVEL, MAX_PRIORITY_LEVEL));
        }
        return priorityLevel;
    }

    private static <T> T requireArgument(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /** @return the database id, {@code null} before persisting */
    public Long getId() { return id; }

    /** @return the plan code */
    public PlanCode getPlanCode() { return planCode; }

    /** @return the monitoring station id */
    public StationId getStationId() { return stationId; }

    /** @return the sampling window */
    public SamplingWindow getSamplingWindow() { return samplingWindow; }

    /** @return the volume to collect */
    public SampleVolume getSampleVolume() { return sampleVolume; }

    /** @return the priority level, from 1 to 5 */
    public Integer getPriorityLevel() { return priorityLevel; }

    /** @return the status */
    public SamplingPlanStatus getStatus() { return status; }

    /** @return the notes, or {@code null} */
    public String getSamplingNotes() { return samplingNotes; }
}
