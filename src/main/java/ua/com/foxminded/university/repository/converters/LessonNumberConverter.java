package ua.com.foxminded.university.repository.converters;

import ua.com.foxminded.university.model.lesson.LessonNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LessonNumberConverter implements AttributeConverter<LessonNumber, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LessonNumber lessonNumber) {
        if (lessonNumber == null) {
            return null;
        }
        return lessonNumber.getLessonNumber();
    }

    @Override
    public LessonNumber convertToEntityAttribute(Integer lessonNumber) {
        if (lessonNumber == null) {
            return null;
        }
        return LessonNumber.get(lessonNumber);
    }
}
