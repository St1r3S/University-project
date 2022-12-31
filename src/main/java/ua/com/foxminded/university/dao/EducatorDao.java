package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorDao extends CrudUserDao<Educator, Long> {
    
    List<Educator> findAllByAcademicRank(AcademicRank academicRank);

}
