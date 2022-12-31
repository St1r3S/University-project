package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.Entity;

import java.util.List;

public interface CrudService<T extends Entity<K>, K extends Number> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    T findById(K id);

    boolean existsById(K id);

    List<T> findAll();

    List<T> findAllById(List<K> ids);

    long count();

    void deleteById(K id);

    void delete(T entity);

    void deleteAllById(List<K> ids);

    void deleteAll(List<T> entities);

    void deleteAll();


}
