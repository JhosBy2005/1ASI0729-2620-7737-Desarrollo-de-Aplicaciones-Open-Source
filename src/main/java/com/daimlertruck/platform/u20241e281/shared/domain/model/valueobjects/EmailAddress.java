package com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * EmailAddress value object.
 *
 * <p>Holds an e-mail address with a valid format.</p>
 *
 * @param address the e-mail address
 * @author José Antonio Santana Luna
 */
public record EmailAddress(String address) {

    /**
     * Regular expression for a valid e-mail address. Shared with the REST resources.
     */
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Creates an e-mail address validating its format.
     *
     * @throws IllegalArgumentException when the address is null, blank or has an invalid format
     */
    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Email address must not be null or blank");
        }
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Email address must have a valid format");
        }
    }
}
