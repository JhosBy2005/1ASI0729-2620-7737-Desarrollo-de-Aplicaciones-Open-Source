package com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects;

/**
 * SampleVolume value object: volume of water to collect and its unit, for example {@code 10 LITER}.
 * Quantities are whole units and no unit conversion is applied.
 *
 * @param quantity the quantity, greater than 0
 * @param unit     the unit of measure, required and not blank
 */
public record SampleVolume(Integer quantity, String unit) {

    /**
     * Creates a sample volume.
     *
     * @throws IllegalArgumentException when the quantity is null or not positive, or the unit is null or blank
     */
    public SampleVolume {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Sample volume quantity is required and must be greater than 0");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Sample volume unit is required and must not be blank");
        }
        unit = unit.trim();
    }

    /**
     * Returns a new sample volume with the quantity increased and the same unit; this instance is not modified.
     *
     * @param additionalVolume the quantity to add, greater than 0
     * @return the new {@link SampleVolume}
     * @throws IllegalArgumentException when the increment is null, not positive or the sum exceeds
     *                                  {@link Integer#MAX_VALUE}
     */
    public SampleVolume addVolume(Integer additionalVolume) {
        if (additionalVolume == null || additionalVolume <= 0) {
            throw new IllegalArgumentException("Additional volume is required and must be greater than 0");
        }
        long total = (long) quantity + additionalVolume;
        if (total > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "Resulting sample volume must not exceed %d".formatted(Integer.MAX_VALUE));
        }
        return new SampleVolume((int) total, unit);
    }
}
