package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureDao extends CrudDao<Lecture, Long> {
    List<Lecture> findAllByStudentId(Long studentId);

    List<Lecture> findAllByWeekNumber(Integer weekNumber);

    List<Lecture> findAllByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber);

    List<Lecture> findAllByDate(LocalDate date);

    List<Lecture> findAllByRoomNumber(String roomNumber);
}
