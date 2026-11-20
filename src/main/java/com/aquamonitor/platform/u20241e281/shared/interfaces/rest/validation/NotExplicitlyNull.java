package com.aquamonitor.platform.u20241e281.shared.interfaces.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that an optional {@code JsonNullable} attribute, when present in the request,
 * is not sent with an explicit {@code null} value. Omitting the attribute is allowed.
 */
@Documented
@Constraint(validatedBy = NotExplicitlyNullValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExplicitlyNull {

    /**
     * Gets the error message template.
     *
     * @return the message template
     */
    String message() default "{validation.not-explicitly-null}";

    /**
     * Gets the validation groups.
     *
     * @return the groups
     */
    Class<?>[] groups() default {};

    /**
     * Gets the payload.
     *
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};
}
