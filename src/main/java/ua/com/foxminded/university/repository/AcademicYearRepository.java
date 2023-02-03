package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

    List<AcademicYear> findByYearNumber(Integer yearNumber);

    List<AcademicYear> findBySemesterType(SemesterType semesterType);

    Optional<AcademicYear> findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType);

}
