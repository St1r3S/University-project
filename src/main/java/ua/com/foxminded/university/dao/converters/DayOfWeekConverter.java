package ua.com.foxminded.university.dao.converters;

import ua.com.foxminded.university.model.schedule.DayOfWeek;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, String> {
    @Override
    public String convertToDatabaseColumn(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            return null;
        }
        return dayOfWeek.getValue();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return DayOfWeek.get(value);
    }
}
