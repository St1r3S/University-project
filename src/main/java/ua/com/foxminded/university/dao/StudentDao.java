package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao extends CrudDao<Student, Long> {
    List<Student> getStudentsByLectureId(Long lectureId);

    List<Student> getStudentsBySpecialismId(Long specialismId);

    List<Student> getStudentsByGroupName(String groupName);

    List<Student> getStudentsByBirthday(LocalDate birthday);

    void enroll(Long lectureId, Long studentId);

    void expel(Long lectureId, Long studentId);
}
