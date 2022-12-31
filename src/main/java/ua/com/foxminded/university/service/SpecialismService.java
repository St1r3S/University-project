package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lesson.Specialism;

public interface SpecialismService extends CrudService<Specialism, Long> {

    Specialism findBySpecialismName(String specialismName);

}
