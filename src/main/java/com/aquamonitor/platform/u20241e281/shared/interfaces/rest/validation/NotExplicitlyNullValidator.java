package com.aquamonitor.platform.u20241e281.shared.interfaces.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Validator for the {@link NotExplicitlyNull} constraint.
 *
 * <p>It supports both ways Hibernate Validator may hand over a {@code JsonNullable} attribute:
 * the container itself, or its unwrapped value (the library registers a value extractor that
 * unwraps the container and skips validation when the attribute was omitted).</p>
 */
public class NotExplicitlyNullValidator implements ConstraintValidator<NotExplicitlyNull, Object> {

    /**
     * Checks that the attribute is either omitted or present with a non-null value.
     *
     * @param value   the {@code JsonNullable} container or its unwrapped value
     * @param context the validation context
     * @return {@code true} when the attribute was omitted or has a non-null value
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof JsonNullable<?> nullable) {
            return !nullable.isPresent() || nullable.get() != null;
        }
        return value != null;
    }
}
