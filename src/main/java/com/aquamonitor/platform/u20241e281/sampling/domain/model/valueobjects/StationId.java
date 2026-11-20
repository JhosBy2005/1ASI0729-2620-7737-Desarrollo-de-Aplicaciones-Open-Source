package com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects;

import java.util.UUID;

/**
 * StationId value object: reference to a monitoring station managed by another bounded context.
 *
 * @param value the station UUID, required
 */
public record StationId(UUID value) {

    /**
     * Creates a station id.
     *
     * @throws IllegalArgumentException when the UUID is null
     */
    public StationId {
        if (value == null) {
            throw new IllegalArgumentException("Station id is required and must not be null");
        }
    }

    /**
     * Creates a station id from its string representation.
     *
     * @param value the UUID as string
     * @return the station id
     * @throws IllegalArgumentException when the value is null or not a valid UUID
     */
    public static StationId of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Station id is required and must not be null");
        }
        return new StationId(UUID.fromString(value.trim()));
    }
}
