package ua.com.foxminded.university.dao.interfaces;

import ua.com.foxminded.university.dao.CrudDao;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorDao extends CrudDao<Educator, Long> {
    List<Educator> getEducatorsBySpecialismId(Long specialismId);

    void enroll(Long educatorId, Long specialismId);

    void expel(Long educatorId, Long specialismId);

}
