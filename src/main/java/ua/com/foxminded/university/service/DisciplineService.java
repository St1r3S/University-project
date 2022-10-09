package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lesson.Discipline;

import java.util.List;

public interface DisciplineService extends CrudService<Discipline, Long> {
    Discipline findByDisciplineName(String disciplineName);

    List<Discipline> findAllBySpecialismId(Long specialismId);

    List<Discipline> findAllByAcademicYearId(Long academicYearId);

    Discipline findByEducatorId(Long educatorId);
}
