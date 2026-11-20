package com.daimlertruck.platform.u20241e281.aftersales.domain.model.valueobjects;

import java.util.UUID;

/**
 * DaimlerWorkshopId value object.
 *
 * <p>Business identifier of a Workshop. It is generated at registration time
 * and holds a non-null, non-empty GUID value.</p>
 *
 * @param workshopId the GUID value
 * @author José Antonio Santana Luna
 */
public record DaimlerWorkshopId(UUID workshopId) {

    /**
     * Creates the identifier validating that the GUID is present and not empty.
     *
     * @throws IllegalArgumentException when the GUID is null or empty (all zeros)
     */
    public DaimlerWorkshopId {
        if (workshopId == null) {
            throw new IllegalArgumentException("Daimler workshop id must not be null");
        }
        if (workshopId.getMostSignificantBits() == 0 && workshopId.getLeastSignificantBits() == 0) {
            throw new IllegalArgumentException("Daimler workshop id must not be empty");
        }
    }

    /**
     * Generates a new random Daimler workshop id.
     */
    public DaimlerWorkshopId() {
        this(UUID.randomUUID());
    }

    /**
     * Creates a Daimler workshop id from its string representation.
     *
     * @param workshopId the GUID as string
     */
    public DaimlerWorkshopId(String workshopId) {
        this(UUID.fromString(workshopId));
    }

    /**
     * Gets the identifier as string.
     *
     * @return the GUID as string
     */
    @Override
    public String toString() {
        return workshopId.toString();
    }
}
