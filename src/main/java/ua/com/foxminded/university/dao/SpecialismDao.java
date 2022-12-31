package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lesson.Specialism;

import java.util.Optional;

public interface SpecialismDao extends CrudDao<Specialism, Long> {
    Optional<Specialism> findBySpecialismName(String specialismName);
}
