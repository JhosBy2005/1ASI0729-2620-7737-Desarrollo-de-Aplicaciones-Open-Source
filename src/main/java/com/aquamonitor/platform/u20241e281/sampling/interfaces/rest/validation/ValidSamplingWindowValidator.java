package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.resources.CreateSamplingPlanResource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Validator for {@link ValidSamplingWindow}. Missing or malformed dates are reported by their own constraints.
 */
public class ValidSamplingWindowValidator
        implements ConstraintValidator<ValidSamplingWindow, CreateSamplingPlanResource> {

    /**
     * Checks that the end date is after the start date.
     *
     * @param resource the request resource
     * @param context  the validation context
     * @return {@code false} only when both dates are valid and the end date is not after the start date
     */
    @Override
    public boolean isValid(CreateSamplingPlanResource resource, ConstraintValidatorContext context) {
        if (resource == null || resource.getSamplingWindowStartDate() == null
                || resource.getSamplingWindowEndDate() == null) {
            return true;
        }
        try {
            var startDate = LocalDate.parse(resource.getSamplingWindowStartDate());
            var endDate = LocalDate.parse(resource.getSamplingWindowEndDate());
            if (endDate.isAfter(startDate)) {
                return true;
            }
        } catch (DateTimeParseException e) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("samplingWindowEndDate")
                .addConstraintViolation();
        return false;
    }
}
