package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao extends CrudDao<Student, Long> {
    List<Student> findAllByLectureId(Long lectureId);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllByGroupName(String groupName);

    List<Student> findAllByBirthday(LocalDate birthday);

    void enroll(Long lectureId, Long studentId);

    void expel(Long lectureId, Long studentId);
}
