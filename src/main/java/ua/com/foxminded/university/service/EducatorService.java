package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorService extends CrudUserService<Educator, Long> {
    List<Educator> findAllByAcademicRank(AcademicRank academicRank);
}
