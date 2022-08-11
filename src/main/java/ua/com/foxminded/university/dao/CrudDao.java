package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T extends Entity<K>, K extends Number> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    Optional<T> findById(K id);

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
