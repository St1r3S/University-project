package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;

public interface LectureService extends CrudService<Lecture, Long> {
    List<Lecture> findAllByStudentId(Long studentId);

    List<Lecture> findAllByStudentId(Student student);

    List<Lecture> findAllByWeekNumber(Integer weekNumber);

    List<Lecture> findAllByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber);

    List<Lecture> findAllByDate(LocalDate date);

    List<Lecture> findAllByRoomNumber(String roomNumber);
}
