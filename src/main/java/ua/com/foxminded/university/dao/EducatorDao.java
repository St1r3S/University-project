package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorDao extends CrudDao<Educator, Long> {
    List<Educator> findAllBySpecialismId(Long specialismId);

    void enrollEducatorSpecialism(Long educatorId, Long specialismId) throws NotFoundException;

    void expelEducatorSpecialism(Long educatorId, Long specialismId) throws NotFoundException;

}
