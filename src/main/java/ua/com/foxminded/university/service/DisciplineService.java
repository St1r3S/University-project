package ua.com.foxminded.university.service;

import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;

import java.util.List;

public interface DisciplineService extends CrudService<Discipline, Long> {
    List<Discipline> findAllBySpecialismId(Long specialismId);

    List<Discipline> findAllBySpecialismId(Specialism specialism);

    Discipline findByDisciplineName(String disciplineName) throws NotFoundException;

    int enrollDisciplineSpecialism(Discipline discipline, Specialism specialism);

    int expelDisciplineSpecialism(Discipline discipline, Specialism specialism);
}