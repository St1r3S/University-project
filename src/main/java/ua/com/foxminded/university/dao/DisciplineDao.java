package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lecture.Discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineDao extends CrudDao<Discipline, Long> {
    List<Discipline> findAllBySpecialismId(Long specialismId);

    Optional<Discipline> findByDisciplineName(String disciplineName);

    int enrollDisciplineSpecialism(Long disciplineId, Long specialismId);

    int expelDisciplineSpecialism(Long disciplineId, Long specialismId);
}
