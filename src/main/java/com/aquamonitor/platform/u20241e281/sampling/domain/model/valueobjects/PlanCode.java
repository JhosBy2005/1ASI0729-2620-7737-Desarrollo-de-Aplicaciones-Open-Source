package com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects;

/**
 * PlanCode value object: business code of a sampling plan, for example {@code SAM-001}.
 *
 * @param code the plan code, required and not blank
 */
public record PlanCode(String code) {

    /**
     * Creates a plan code.
     *
     * @throws IllegalArgumentException when the code is null or blank
     */
    public PlanCode {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Plan code is required and must not be blank");
        }
        code = code.trim();
    }
}
