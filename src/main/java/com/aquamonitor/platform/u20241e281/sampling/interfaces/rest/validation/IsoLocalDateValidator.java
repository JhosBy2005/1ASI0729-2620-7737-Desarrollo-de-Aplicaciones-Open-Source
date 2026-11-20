package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validator for {@link IsoLocalDate}.
 */
public class IsoLocalDateValidator implements ConstraintValidator<IsoLocalDate, String> {

    /**
     * Parses the value with the strict ISO 8601 local date format.
     *
     * @param value   the value
     * @param context the validation context
     * @return {@code true} when the value is null or a valid date
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
