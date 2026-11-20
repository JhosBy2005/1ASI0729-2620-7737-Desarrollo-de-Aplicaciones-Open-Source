package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a string is a real calendar date in the ISO 8601 format (yyyy-MM-dd). Null values are valid.
 */
@Documented
@Constraint(validatedBy = IsoLocalDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoLocalDate {

    /** @return the message template */
    String message() default "{validation.date.iso}";

    /** @return the validation groups */
    Class<?>[] groups() default {};

    /** @return the payload */
    Class<? extends Payload>[] payload() default {};
}
