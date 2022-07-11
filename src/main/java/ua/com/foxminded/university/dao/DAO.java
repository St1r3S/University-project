package ua.com.foxminded.university.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<K extends Number, T> {
    void create(T entity);

    Optional<T> retrieve(K id);

    void update(T entity);

    void delete(K id);

    void delete(T entity);

    List<T> findAll();
}
