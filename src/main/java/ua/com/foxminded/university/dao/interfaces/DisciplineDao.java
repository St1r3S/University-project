package ua.com.foxminded.university.dao.interfaces;

import ua.com.foxminded.university.dao.CrudDao;
import ua.com.foxminded.university.model.lecture.Discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineDao extends CrudDao<Discipline, Long> {
    List<Discipline> getDisciplinesBySpecialismId(Long specialismId);

    Optional<Discipline> getDisciplineByDisciplineName(String disciplineName);

    void enroll(Long disciplineId, Long specialismId);

    void expel(Long disciplineId, Long specialismId);
}
