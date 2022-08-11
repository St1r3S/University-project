package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCrudDao<T extends Entity<K>, K extends Number> implements CrudDao<T, K> {

    @Override
    public T save(T entity) {
        return entity.getId() == null ? create(entity) : update(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        List<T> result = new ArrayList<>();
        entities.forEach(e -> {
            T saved = save(e);
            result.add(saved);
        });
        return result;
    }

    protected abstract T create(T entity);

    protected abstract T update(T entity);
}
