package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.Entity;

import java.util.List;

public interface CrudService<T extends Entity<K>, K extends Number> {
    T findById(K id);

    T save(T entity);

    void deleteById(K id);

    void deleteById(T entity);

    List<T> findAll();
}
