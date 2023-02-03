package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByDisciplineId(Long disciplineId);

    List<Lesson> findAllByGroupId(Long groupId);

    List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber);

    List<Lesson> findAllByRoomId(Long roomId);

    List<Lesson> findAllByScheduleDayId(Long scheduleDayId);
}
