package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.user.Student;

import java.util.List;

@Repository
public interface StudentRepository extends UserSearch<Student>, JpaRepository<Student, Long> {

    List<Student> findAllByGroupId(Long groupId);

    List<Student> findAllBySpecialismId(Long specialismId);

    List<Student> findAllByAcademicYearId(Long academicYearId);

}
