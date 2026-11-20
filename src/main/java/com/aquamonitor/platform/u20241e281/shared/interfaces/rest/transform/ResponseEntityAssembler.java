package com.aquamonitor.platform.u20241e281.shared.interfaces.rest.transform;

import com.aquamonitor.platform.u20241e281.shared.application.result.ApplicationError;
import com.aquamonitor.platform.u20241e281.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

/**
 * Assembler that translates application {@link Result} values into HTTP responses.
 */
public final class ResponseEntityAssembler {

    private ResponseEntityAssembler() {
    }

    /**
     * Converts a result into an HTTP response.
     * A success is mapped with the given resource assembler; a failure becomes a localized
     * {@code {"message": "..."}} body with the HTTP status of its error category.
     *
     * @param result                   the application result
     * @param successResourceAssembler function that maps the success value to a response resource
     * @param successStatus            HTTP status used for successful responses
     * @param messageSource            the source of localized messages
     * @param <T>                      success value type
     * @param <R>                      success response resource type
     * @return the response entity for the success or the failure
     */
    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> successResourceAssembler,
            HttpStatusCode successStatus,
            MessageSource messageSource) {
        return switch (result) {
            case Result.Success<T, ApplicationError> success ->
                    ResponseEntity.status(successStatus).body(successResourceAssembler.apply(success.value()));
            case Result.Failure<T, ApplicationError> failure ->
                    MessageResourceFromApplicationErrorAssembler
                            .toResponseEntityFromApplicationError(failure.error(), messageSource);
        };
    }
}
