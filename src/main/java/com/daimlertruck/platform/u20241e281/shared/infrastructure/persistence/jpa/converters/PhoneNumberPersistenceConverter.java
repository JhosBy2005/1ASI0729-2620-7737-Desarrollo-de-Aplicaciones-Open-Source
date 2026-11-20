package com.daimlertruck.platform.u20241e281.shared.infrastructure.persistence.jpa.converters;

import com.daimlertruck.platform.u20241e281.shared.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link PhoneNumber} values between the domain model and a database column.
 *
 * @author José Antonio Santana Luna
 */
@Converter(autoApply = false)
public class PhoneNumberPersistenceConverter implements AttributeConverter<PhoneNumber, String> {

    /**
     * Converts the value object to its column value.
     *
     * @param attribute the phone number value object
     * @return the phone number as string, or {@code null}
     */
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute == null ? null : attribute.number();
    }

    /**
     * Converts the column value to the value object.
     *
     * @param dbData the stored phone number
     * @return the phone number value object, or {@code null}
     */
    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new PhoneNumber(dbData);
    }
}
