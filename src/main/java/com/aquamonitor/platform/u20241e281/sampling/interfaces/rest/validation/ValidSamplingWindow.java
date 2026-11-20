package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the sampling window end date of a request is after its start date.
 */
@Documented
@Constraint(validatedBy = ValidSamplingWindowValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSamplingWindow {

    /** @return the message template */
    String message() default "{validation.sampling-window.end-after-start}";

    /** @return the validation groups */
    Class<?>[] groups() default {};

    /** @return the payload */
    Class<? extends Payload>[] payload() default {};
}
