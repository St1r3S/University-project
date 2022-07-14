package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.Entity;

public abstract class AbstractCrudDao<T extends Entity<K>, K extends Number> implements CrudDao<T, K> {

    @Override
    public T save(T entity) {
        try {
            return entity.getId() == null ? create(entity) : update(entity);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T create(T entity);

    protected abstract T update(T entity) throws NotFoundException;
}
