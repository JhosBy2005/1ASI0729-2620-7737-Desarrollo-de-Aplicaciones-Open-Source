package com.aquamonitor.platform.u20241e281.shared.interfaces.rest;

import com.aquamonitor.platform.u20241e281.shared.interfaces.rest.resources.MessageResource;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler for the REST API.
 *
 * <p>Translates every exception not handled by a controller into a JSON body with the shape
 * {@code {"message": "..."}}, localized according to the {@code Accept-Language} header
 * (English by default, Spanish for {@code es} or {@code es-PE}).</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messageSource;

    /**
     * Constructor.
     *
     * @param messageSource the source of localized messages
     */
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles Bean Validation errors on request bodies.
     *
     * @param ex the validation exception raised by {@code @Valid}
     * @return 400 with the list of invalid fields and their localized messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResource> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        var message = details.isBlank()
                ? localize("error.request.validation-failed", "Request validation failed")
                : details;
        return toResponse(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Handles unreadable request bodies: malformed JSON, unknown attributes or values with a wrong type
     * (for example an invalid UUID or a date not in the yyyy-MM-dd format).
     *
     * @param ex the exception raised when the request body cannot be read
     * @return 400 with a localized message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResource> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof UnrecognizedPropertyException unrecognizedProperty) {
            return toResponse(HttpStatus.BAD_REQUEST, localize("error.request.unknown-field",
                    "Attribute '%s' is not allowed".formatted(unrecognizedProperty.getPropertyName()),
                    unrecognizedProperty.getPropertyName()));
        }
        if (ex.getCause() instanceof MismatchedInputException mismatchedInput && !mismatchedInput.getPath().isEmpty()) {
            var field = toFieldPath(mismatchedInput);
            return toResponse(HttpStatus.BAD_REQUEST, localize("error.request.invalid-field",
                    "Attribute '%s' has an invalid value or format".formatted(field), field));
        }
        return toResponse(HttpStatus.BAD_REQUEST, localize("error.request.malformed", "Malformed request body"));
    }

    /**
     * Handles {@link IllegalArgumentException} raised outside the application layer.
     *
     * @param ex the exception
     * @return 400 with a localized message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResource> handleIllegalArgumentException(IllegalArgumentException ex) {
        return toResponse(HttpStatus.BAD_REQUEST,
                localize("error.request.validation-failed", "Request validation failed"));
    }

    /**
     * Handles any other exception. Spring MVC exceptions keep their own HTTP status
     * (for example 404, 405 or 415); everything else becomes a 500 response.
     *
     * @param ex the exception
     * @return the error response with a localized message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource> handleException(Exception ex) {
        if (ex instanceof ErrorResponse errorResponse) {
            return toResponse(errorResponse.getStatusCode(), localizeHttpStatus(errorResponse.getStatusCode()));
        }
        LOGGER.error("Unexpected error while processing the request", ex);
        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                localize("error.unexpected", "An unexpected error occurred. Please try again later."));
    }

    private String localizeHttpStatus(HttpStatusCode status) {
        return switch (status.value()) {
            case 400 -> localize("error.request.validation-failed", "Request validation failed");
            case 404 -> localize("error.http.not-found", "The requested resource was not found");
            case 405 -> localize("error.http.method-not-allowed", "HTTP method not allowed for this resource");
            case 415 -> localize("error.http.unsupported-media-type", "Content type not supported, use application/json");
            default -> localize("error.http.generic", "The request could not be processed");
        };
    }

    private static String toFieldPath(JsonMappingException ex) {
        return ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("."));
    }

    private String localize(String key, String defaultMessage, Object... arguments) {
        return messageSource.getMessage(key, arguments, defaultMessage, LocaleContextHolder.getLocale());
    }

    private static ResponseEntity<MessageResource> toResponse(HttpStatusCode status, String message) {
        return ResponseEntity.status(status).body(new MessageResource(message));
    }
}
