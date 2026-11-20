package com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects;

import java.time.LocalDate;

/**
 * SamplingWindow value object: period in which the samples must be collected.
 *
 * @param startDate the first day, required
 * @param endDate   the last day, required and after the start date
 */
public record SamplingWindow(LocalDate startDate, LocalDate endDate) {

    /**
     * Creates a sampling window.
     *
     * @throws IllegalArgumentException when a date is null or the end date is not after the start date
     */
    public SamplingWindow {
        if (startDate == null) {
            throw new IllegalArgumentException("Sampling window start date is required and must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("Sampling window end date is required and must not be null");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Sampling window end date must be after the start date");
        }
    }

    /**
     * Checks whether a date is inside the window, both limits included.
     *
     * @param checkDate the date to check
     * @return {@code true} when the date is between the start and end dates (inclusive), {@code false} otherwise
     * @throws IllegalArgumentException when the date is null
     */
    public boolean contains(LocalDate checkDate) {
        if (checkDate == null) {
            throw new IllegalArgumentException("Date to check is required and must not be null");
        }
        return checkDate.compareTo(startDate) >= 0 && checkDate.compareTo(endDate) <= 0;
    }
}
