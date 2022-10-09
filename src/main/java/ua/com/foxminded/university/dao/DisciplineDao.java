package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lesson.Discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineDao extends CrudDao<Discipline, Long> {
    Optional<Discipline> findByDisciplineName(String disciplineName);

    List<Discipline> findAllBySpecialismId(Long specialismId);

    List<Discipline> findAllByAcademicYearId(Long academicYearId);

    Optional<Discipline> findByEducatorId(Long educatorId);

}
