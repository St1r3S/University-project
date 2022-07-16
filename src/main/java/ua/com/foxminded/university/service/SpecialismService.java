package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface SpecialismService extends CrudService<Specialism, Long> {
    List<Specialism> findAllByDisciplineId(Long disciplineId);

    List<Specialism> findAllByDisciplineId(Discipline discipline);

    List<Specialism> findAllByEducatorId(Long educatorId);

    List<Specialism> findAllByEducatorId(Educator educator);

    Specialism findBySpecialismName(String specialismName);
}
