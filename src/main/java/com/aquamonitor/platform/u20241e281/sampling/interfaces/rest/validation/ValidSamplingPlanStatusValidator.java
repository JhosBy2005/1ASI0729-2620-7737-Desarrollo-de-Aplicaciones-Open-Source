package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.resources.CreateSamplingPlanResource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link ValidSamplingPlanStatus}.
 */
public class ValidSamplingPlanStatusValidator
        implements ConstraintValidator<ValidSamplingPlanStatus, CreateSamplingPlanResource> {

    /**
     * Accepts an omitted status or a recognized status name; rejects an explicit null or an unknown value.
     *
     * @param resource the request resource
     * @param context  the validation context
     * @return {@code true} when the status is valid
     */
    @Override
    public boolean isValid(CreateSamplingPlanResource resource, ConstraintValidatorContext context) {
        if (resource == null || !resource.isStatusPresent()) {
            return true;
        }
        String template;
        if (resource.getStatus() == null) {
            template = "{validation.status.not-null}";
        } else if (SamplingPlanStatus.findByName(resource.getStatus()).isEmpty()) {
            template = context.getDefaultConstraintMessageTemplate();
        } else {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template)
                .addPropertyNode("status")
                .addConstraintViolation();
        return false;
    }
}
