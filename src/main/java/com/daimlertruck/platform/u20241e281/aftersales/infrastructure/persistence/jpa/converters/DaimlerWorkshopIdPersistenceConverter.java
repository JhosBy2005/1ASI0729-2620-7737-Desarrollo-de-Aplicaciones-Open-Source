package com.daimlertruck.platform.u20241e281.aftersales.infrastructure.persistence.jpa.converters;

import com.daimlertruck.platform.u20241e281.aftersales.domain.model.valueobjects.DaimlerWorkshopId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link DaimlerWorkshopId} values between the domain model and a database column.
 *
 * @author José Antonio Santana Luna
 */
@Converter(autoApply = false)
public class DaimlerWorkshopIdPersistenceConverter implements AttributeConverter<DaimlerWorkshopId, String> {

    /**
     * Converts the value object to its column value.
     *
     * @param attribute the Daimler workshop id
     * @return the GUID as string, or {@code null}
     */
    @Override
    public String convertToDatabaseColumn(DaimlerWorkshopId attribute) {
        return attribute == null ? null : attribute.toString();
    }

    /**
     * Converts the column value to the value object.
     *
     * @param dbData the stored GUID
     * @return the Daimler workshop id, or {@code null}
     */
    @Override
    public DaimlerWorkshopId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new DaimlerWorkshopId(dbData);
    }
}
