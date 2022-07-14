package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends Entity<K>, K extends Number> {

    Optional<T> findById(K id);

    T save(T entity);

    void deleteById(K id);

    void deleteById(T entity);

    List<T> findAll();
}
