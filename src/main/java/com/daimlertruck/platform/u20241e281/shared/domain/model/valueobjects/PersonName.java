package com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects;

/**
 * PersonName value object.
 *
 * <p>Represents the first and last name of a person.</p>
 *
 * @param firstName the first name, required
 * @param lastName  the last name, required
 * @author José Antonio Santana Luna
 */
public record PersonName(String firstName, String lastName) {

    /**
     * Creates a person name validating that both parts are present.
     *
     * @throws IllegalArgumentException when the first or last name is null or blank
     */
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
    }

    /**
     * Gets the full name.
     *
     * @return the first and last name separated by a space
     */
    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }
}
