package ua.com.foxminded.university.repository.converters;

import ua.com.foxminded.university.model.schedule.SemesterType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
