package com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects;

import java.util.Arrays;
import java.util.Optional;

/**
 * Status of a sampling plan. {@link #PENDING} is assigned when no status is provided.
 */
public enum SamplingPlanStatus {
    /** Registered and waiting for approval (default). */
    PENDING,
    /** Approved. */
    APPROVED,
    /** Samples collected. */
    COMPLETED,
    /** Cancelled. */
    CANCELLED;

    /**
     * Finds the status whose name matches exactly.
     *
     * @param name the status name
     * @return the status, or an empty optional when the name is null or not recognized
     */
    public static Optional<SamplingPlanStatus> findByName(String name) {
        return Arrays.stream(values()).filter(status -> status.name().equals(name)).findFirst();
    }
}
