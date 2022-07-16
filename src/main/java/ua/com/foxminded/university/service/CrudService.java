package ua.com.foxminded.university.service;

import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.Entity;

import java.util.List;

public interface CrudService<T extends Entity<K>, K extends Number> {
    T findById(K id) throws NotFoundException;

    T save(T entity);

    int deleteById(K id);

    int deleteById(T entity);

    List<T> findAll();
}
