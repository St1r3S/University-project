package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;

public interface AcademicYearService extends CrudService<AcademicYear, Long> {

    List<AcademicYear> findByYearNumber(Integer yearNumber);

    List<AcademicYear> findBySemesterType(SemesterType semesterType);

    AcademicYear findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType);

    Integer getSemesterNumber(AcademicYear academicYear);

}
