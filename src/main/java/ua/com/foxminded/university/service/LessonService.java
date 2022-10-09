package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import java.util.List;

public interface LessonService extends CrudService<Lesson, Long> {
    List<Lesson> findAllByDisciplineId(Long disciplineId);

    List<Lesson> findAllByGroupId(Long groupId);

    List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber);

    List<Lesson> findAllByRoomId(Long roomId);

    List<Lesson> findAllByScheduleDayId(Long scheduleDayId);
}
