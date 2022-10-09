package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Student;

import java.util.List;

public interface StudentDao extends CrudUserDao<Student, Long> {

    List<Student> findAllByGroupId(Long groupId);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllByAcademicYearId(Long academicYearId);

}
