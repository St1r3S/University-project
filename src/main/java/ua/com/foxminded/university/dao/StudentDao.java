package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao extends CrudDao<Student, Long> {
    List<Student> findAllByLectureId(Long lectureId);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllByGroupName(String groupName);

    List<Student> findAllByBirthday(LocalDate birthday);

    int enrollLectureStudent(Long lectureId, Long studentId);

    int expelLectureStudent(Long lectureId, Long studentId);
}
