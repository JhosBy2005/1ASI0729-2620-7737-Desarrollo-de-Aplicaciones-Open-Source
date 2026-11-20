package com.aquamonitor.platform.u20241e281.sampling.domain.exceptions;

/**
 * Thrown when the persistence layer detects that a plan code is already registered.
 */
public class DuplicatePlanCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param planCode the duplicated plan code
     * @param cause    the original exception
     */
    public DuplicatePlanCodeException(String planCode, Throwable cause) {
        super("Plan code '%s' is already registered".formatted(planCode), cause);
    }
}
