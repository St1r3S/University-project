package ua.com.foxminded.university.repository.converters;

import ua.com.foxminded.university.model.schedule.SemesterType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SemesterTypeConverter implements AttributeConverter<SemesterType, String> {
    @Override
    public String convertToDatabaseColumn(SemesterType semesterType) {
        if (semesterType == null) {
            return null;
        }
        return semesterType.getValue();
    }

    @Override
    public SemesterType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return SemesterType.get(value);
    }
}
