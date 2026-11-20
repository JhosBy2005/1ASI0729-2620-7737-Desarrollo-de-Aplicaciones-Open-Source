package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.transform;

import com.aquamonitor.platform.u20241e281.sampling.application.commandservices.SamplingPlanCommandFailure;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.shared.application.result.Result;
import com.aquamonitor.platform.u20241e281.shared.interfaces.rest.resources.MessageResource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Translates the result of a sampling plan command into an HTTP response.
 */
public final class SamplingPlanCommandHttpResponseMapper {

    private SamplingPlanCommandHttpResponseMapper() {
    }

    /**
     * Converts the result: 201 with the created resource, or a localized {@code {"message": "..."}}
     * with 400, 409 or 500 depending on the failure.
     *
     * @param result        the command result
     * @param messageSource the source of localized messages
     * @return the response entity
     */
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<SamplingPlan, SamplingPlanCommandFailure> result, MessageSource messageSource) {
        return result.fold(
                samplingPlan -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(SamplingPlanResourceMapper.toResourceFromEntity(samplingPlan)),
                failure -> ResponseEntity.status(toHttpStatusFromFailure(failure))
                        .body(new MessageResource(messageSource.getMessage(
                                failure.messageKey(),
                                failure.messageArguments().toArray(),
                                failure.messageKey(),
                                LocaleContextHolder.getLocale()))));
    }

    private static HttpStatus toHttpStatusFromFailure(SamplingPlanCommandFailure failure) {
        return switch (failure) {
            case SamplingPlanCommandFailure.InvalidData invalidData -> HttpStatus.BAD_REQUEST;
            case SamplingPlanCommandFailure.DuplicatePlanCode duplicatePlanCode -> HttpStatus.CONFLICT;
            case SamplingPlanCommandFailure.Unexpected unexpected -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
