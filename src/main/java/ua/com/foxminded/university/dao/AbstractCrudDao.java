package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;

public abstract class AbstractCrudDao<T extends Entity<K>, K extends Number> implements CrudDao<T, K> {

    @Override
    public T save(T entity) {
        return entity.getId() == null ? create(entity) : update(entity);
    }

    protected abstract T create(T entity);

    protected abstract T update(T entity);
}
