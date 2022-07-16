package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentService extends CrudService<Student, Long> {
    List<Student> findAllByLectureId(Long lectureId);

    List<Student> findAllByLectureId(Lecture lecture);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllBySpecialismId(Specialism specialism);

    List<Student> findAllByGroupName(String groupName);

    List<Student> findAllByBirthday(LocalDate birthday);

    int enrollLectureStudent(Lecture lecture, Student student);

    int expelLectureStudent(Lecture lecture, Student student);
}
