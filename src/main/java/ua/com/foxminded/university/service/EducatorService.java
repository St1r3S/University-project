package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

public interface EducatorService extends CrudService<Educator, Long> {
    List<Educator> findAllBySpecialismId(Long specialismId);

    List<Educator> findAllBySpecialismId(Specialism specialism);

    int enrollEducatorSpecialism(Educator educator, Specialism specialism);

    int expelEducatorSpecialism(Educator educator, Specialism specialism);
}