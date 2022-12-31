package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import java.util.List;

public interface LessonDao extends CrudDao<Lesson, Long> {
    List<Lesson> findAllByDisciplineId(Long disciplineId);

    List<Lesson> findAllByGroupId(Long groupId);

    List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber);

    List<Lesson> findAllByRoomId(Long roomId);

    List<Lesson> findAllByScheduleDayId(Long scheduleDayId);
}
