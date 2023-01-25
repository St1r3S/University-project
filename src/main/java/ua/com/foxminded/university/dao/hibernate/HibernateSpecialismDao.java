package ua.com.foxminded.university.dao.hibernate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.SpecialismDao;
import ua.com.foxminded.university.dao.hibernate.mappers.SpecialismRowMapper;
import ua.com.foxminded.university.model.lesson.Specialism;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateSpecialismDao extends AbstractCrudDao<Specialism, Long> implements SpecialismDao {
    public static final String SPECIALISM_ID = "id";
    public static final String SPECIALISM_NAME = "specialism_name";
    private static final String UPDATE = "UPDATE specialisms SET specialism_name = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM specialisms WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM specialisms LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM specialisms WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM specialisms";
    private static final String DELETE = "DELETE FROM specialisms WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM specialisms WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM specialisms";
    private static final String SPECIALISM_BY_SPECIALISM_NAME = "SELECT * FROM specialisms WHERE specialism_name = ?";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Specialism create(Specialism entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Specialism update(Specialism entity) {
        try {
            return entityManager.merge(entity);
        } catch (IllegalArgumentException ex) {
            throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
        }
    }

    @Override
    public Optional<Specialism> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new SpecialismRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Specialism> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new SpecialismRowMapper(), limit);
    }

    @Override
    public List<Specialism> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new SpecialismRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete specialism entity with id" + id, 1);
    }

    @Override
    public void delete(Specialism entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete specialism entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<Specialism> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(Specialism::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<Specialism> findBySpecialismName(String specialismName) {
        return jdbcTemplate.query(SPECIALISM_BY_SPECIALISM_NAME, new SpecialismRowMapper(), specialismName)
                .stream()
                .findFirst();
    }
}
