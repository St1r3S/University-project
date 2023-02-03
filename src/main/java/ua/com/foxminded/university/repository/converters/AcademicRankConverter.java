package ua.com.foxminded.university.repository.converters;

import ua.com.foxminded.university.model.user.AcademicRank;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AcademicRankConverter implements AttributeConverter<AcademicRank, String> {
    @Override
    public String convertToDatabaseColumn(AcademicRank academicRank) {
        if (academicRank == null) {
            return null;
        }
        return academicRank.getKey();
    }

    @Override
    public AcademicRank convertToEntityAttribute(String key) {
        if (key == null) {
            return null;
        }
        return AcademicRank.get(key);
    }
}
