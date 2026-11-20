package com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.converters;

import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link EmailAddress} values between the domain model and a database column.
 *
 * @author José Antonio Santana Luna
 */
@Converter(autoApply = false)
public class EmailAddressPersistenceConverter implements AttributeConverter<EmailAddress, String> {

    /**
     * Converts the value object to its column value.
     *
     * @param attribute the e-mail address value object
     * @return the e-mail address as string, or {@code null}
     */
    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute == null ? null : attribute.address();
    }

    /**
     * Converts the column value to the value object.
     *
     * @param dbData the stored e-mail address
     * @return the e-mail address value object, or {@code null}
     */
    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EmailAddress(dbData);
    }
}
