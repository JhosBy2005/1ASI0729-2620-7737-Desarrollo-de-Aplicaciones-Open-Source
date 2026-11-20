package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates the optional status of a request: it may be omitted, but it cannot be sent as null and it must be a recognized status.
 */
@Documented
@Constraint(validatedBy = ValidSamplingPlanStatusValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSamplingPlanStatus {

    /** @return the message template */
    String message() default "{validation.status.invalid}";

    /** @return the validation groups */
    Class<?>[] groups() default {};

    /** @return the payload */
    Class<? extends Payload>[] payload() default {};
}
