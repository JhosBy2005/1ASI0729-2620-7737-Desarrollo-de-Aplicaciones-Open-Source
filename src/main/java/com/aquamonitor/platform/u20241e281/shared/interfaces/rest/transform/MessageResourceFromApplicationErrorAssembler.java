package com.aquamonitor.platform.u20241e281.shared.interfaces.rest.transform;

import com.aquamonitor.platform.u20241e281.shared.application.result.ApplicationError;
import com.aquamonitor.platform.u20241e281.shared.interfaces.rest.resources.MessageResource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Assembler that converts an {@link ApplicationError} into a localized {@link MessageResource}
 * wrapped in a {@link ResponseEntity} with the corresponding HTTP status.
 */
public final class MessageResourceFromApplicationErrorAssembler {

    private MessageResourceFromApplicationErrorAssembler() {
    }

    /**
     * Converts the application error into a localized message resource.
     * The locale is the one resolved from the {@code Accept-Language} header.
     *
     * @param error         the application error
     * @param messageSource the source of localized messages
     * @return the message resource
     */
    public static MessageResource toResourceFromApplicationError(ApplicationError error, MessageSource messageSource) {
        var message = messageSource.getMessage(
                error.messageKey(),
                error.arguments().toArray(),
                error.defaultMessage(),
                LocaleContextHolder.getLocale());
        return new MessageResource(message);
    }

    /**
     * Maps the application error category to an HTTP status.
     *
     * @param error the application error
     * @return the HTTP status
     */
    public static HttpStatus toHttpStatusFromApplicationError(ApplicationError error) {
        return switch (error.type()) {
            case VALIDATION, BUSINESS_RULE_VIOLATION -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case UNEXPECTED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /**
     * Converts the application error into an HTTP response with a localized message body.
     *
     * @param error         the application error
     * @param messageSource the source of localized messages
     * @return the response entity
     */
    public static ResponseEntity<MessageResource> toResponseEntityFromApplicationError(
            ApplicationError error, MessageSource messageSource) {
        return ResponseEntity
                .status(toHttpStatusFromApplicationError(error))
                .body(toResourceFromApplicationError(error, messageSource));
    }
}
