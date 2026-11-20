package com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * PhoneNumber value object.
 *
 * <p>Holds a phone number with a valid international format (E.164),
 * for example {@code +51987654321}.</p>
 *
 * @param number the phone number in international format
 * @author José Antonio Santana Luna
 */
public record PhoneNumber(String number) {

    /**
     * Regular expression for a valid international phone number (E.164). Shared with the REST resources.
     */
    public static final String PHONE_REGEX = "^\\+[1-9]\\d{6,14}$";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * Creates a phone number validating its international format.
     *
     * @throws IllegalArgumentException when the number is null, blank or not a valid international number
     */
    public PhoneNumber {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Phone number must not be null or blank");
        }
        if (!PHONE_PATTERN.matcher(number).matches()) {
            throw new IllegalArgumentException("Phone number must have a valid international format, e.g. +51987654321");
        }
    }
}
