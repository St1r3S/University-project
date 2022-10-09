package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.user.Student;

import java.util.List;

public interface StudentService extends CrudUserService<Student, Long> {
    List<Student> findAllByGroupId(Long groupId);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllByAcademicYearId(Long academicYearId);
}
