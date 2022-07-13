package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends Entity<K>, K extends Number> {

    Optional<T> retrieve(K id);

    T save(T entity);

    void delete(K id);

    void delete(T entity);

    List<T> findAll();
}
