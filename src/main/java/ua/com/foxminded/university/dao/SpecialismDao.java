package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.misc.Specialism;

import java.util.List;
import java.util.Optional;

public interface SpecialismDao extends CrudDao<Specialism, Long> {

    List<Specialism> findAllByDisciplineId(Long disciplineId);

    List<Specialism> findAllByEducatorId(Long educatorId);

    Optional<Specialism> findBySpecialismName(String specialismName);
}
