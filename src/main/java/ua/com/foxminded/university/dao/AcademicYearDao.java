package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

public interface AcademicYearDao extends CrudDao<AcademicYear, Long> {

    List<AcademicYear> findByYearNumber(Integer yearNumber);

    List<AcademicYear> findBySemesterType(SemesterType semesterType);

    Optional<AcademicYear> findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType);

}
