package com.aquamonitor.platform.u20241e281.shared.interfaces.rest;

import com.aquamonitor.platform.u20241e281.shared.interfaces.rest.resources.MessageResource;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler for the REST API.
 *
 * <p>Extends Spring's {@link ResponseEntityExceptionHandler}, so every Spring MVC exception keeps its
 * standard HTTP status, and replaces every error body with {@code {"message": "..."}}, localized
 * according to the {@code Accept-Language} header (English by default, Spanish for {@code es} or
 * {@code es-PE}).</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource localizedMessages;

    /**
     * Constructor.
     *
     * @param localizedMessages the source of localized messages
     */
    public GlobalExceptionHandler(MessageSource localizedMessages) {
        this.localizedMessages = localizedMessages;
    }

    /**
     * Handles Bean Validation errors on request bodies.
     *
     * @param ex      the validation exception raised by {@code @Valid}
     * @param headers the response headers
     * @param status  the HTTP status (400)
     * @param request the current request
     * @return 400 with the invalid attributes and their localized messages
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        var message = details.isBlank() ? localize("error.request.validation-failed") : details;
        return handleExceptionInternal(ex, new MessageResource(message), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles unreadable request bodies: malformed JSON, attributes outside the contract
     * or values with a wrong JSON type.
     *
     * @param ex      the exception raised when the request body cannot be read
     * @param headers the response headers
     * @param status  the HTTP status (400)
     * @param request the current request
     * @return 400 with a localized message
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        String message;
        if (ex.getCause() instanceof UnrecognizedPropertyException unrecognizedProperty) {
            message = localize("error.request.unknown-field", unrecognizedProperty.getPropertyName());
        } else if (ex.getCause() instanceof MismatchedInputException mismatchedInput
                && !mismatchedInput.getPath().isEmpty()) {
            message = localize("error.request.invalid-field", toFieldPath(mismatchedInput));
        } else {
            message = localize("error.request.malformed");
        }
        return handleExceptionInternal(ex, new MessageResource(message), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles invalid data that reached the domain outside of a command service.
     *
     * @param ex the exception
     * @return 400 with a localized message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResource> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResource(localize("error.request.invalid-data", ex.getMessage())));
    }

    /**
     * Handles any exception not managed by another handler.
     *
     * @param ex the exception
     * @return 500 with a localized generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource> handleUnexpectedException(Exception ex) {
        LOGGER.error("Unexpected error while processing the request", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResource(localize("error.unexpected")));
    }

    /**
     * Creates the error response of every Spring MVC exception, making sure its body is a
     * localized {@link MessageResource}.
     *
     * @param body       the body prepared by the handler
     * @param headers    the response headers
     * @param statusCode the HTTP status
     * @param request    the current request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers,
                                                          HttpStatusCode statusCode, WebRequest request) {
        var messageResource = body instanceof MessageResource resource
                ? resource
                : new MessageResource(localizeHttpStatus(statusCode));
        return new ResponseEntity<>(messageResource, headers, statusCode);
    }

    private String localizeHttpStatus(HttpStatusCode status) {
        return switch (status.value()) {
            case 400 -> localize("error.request.validation-failed");
            case 404 -> localize("error.http.not-found");
            case 405 -> localize("error.http.method-not-allowed");
            case 415 -> localize("error.http.unsupported-media-type");
            case 500 -> localize("error.unexpected");
            default -> localize("error.http.generic");
        };
    }

    private static String toFieldPath(JsonMappingException ex) {
        return ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("."));
    }

    private String localize(String key, Object... arguments) {
        return localizedMessages.getMessage(key, arguments, key, LocaleContextHolder.getLocale());
    }
}
