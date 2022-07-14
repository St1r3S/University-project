package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureDao extends CrudDao<Lecture, Long> {
    List<Lecture> getLecturesByStudentId(Long studentId);

    List<Lecture> getLecturesByWeekNumber(Integer weekNumber);

    List<Lecture> getLecturesByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber);

    List<Lecture> getLecturesByDate(LocalDate date);

    List<Lecture> getLecturesByRoomNumber(String roomNumber);
}
