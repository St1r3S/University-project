package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorDao extends CrudDao<Educator, Long> {
    List<Educator> findAllBySpecialismId(Long specialismId);

    int enrollEducatorSpecialism(Long educatorId, Long specialismId);

    int expelEducatorSpecialism(Long educatorId, Long specialismId);

}
