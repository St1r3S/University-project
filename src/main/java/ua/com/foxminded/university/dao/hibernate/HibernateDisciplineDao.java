package ua.com.foxminded.university.dao.hibernate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.DisciplineDao;
import ua.com.foxminded.university.dao.hibernate.mappers.DisciplineRowMapper;
import ua.com.foxminded.university.model.lesson.Discipline;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class HibernateDisciplineDao extends AbstractCrudDao<Discipline, Long> implements DisciplineDao {
    public static final String DISCIPLINE_ID = "id";
    public static final String DISCIPLINE_NAME = "discipline_name";
    public static final String DISCIPLINE_SPECIALISM_ID = "specialism_id";
    public static final String DISCIPLINE_ACADEMIC_YEAR_ID = "academic_year_id";
    public static final String DISCIPLINE_EDUCATOR_ID = "educator_id";
    private static final String UPDATE = "UPDATE disciplines SET discipline_name = ?, specialism_id = ?, academic_year_id = ?, educator_id = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM disciplines WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM disciplines LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM disciplines WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM disciplines";
    private static final String DELETE = "DELETE FROM disciplines WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM disciplines WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM disciplines";
    private static final String DISCIPLINE_BY_DISCIPLINE_NAME = "SELECT * FROM disciplines WHERE discipline_name = ?";
    private static final String DISCIPLINES_BY_SPECIALISM_ID = "SELECT * FROM disciplines WHERE specialism_id = ?";
    private static final String DISCIPLINES_BY_ACADEMIC_YEAR_ID = "SELECT * FROM disciplines WHERE academic_year_id = ?";
    private static final String DISCIPLINES_BY_SPECIALISM_ID_AND_ACADEMIC_YEAR_ID = "SELECT * FROM disciplines WHERE specialism_id = ? AND academic_year_id = ?";
    private static final String DISCIPLINE_BY_EDUCATOR_ID = "SELECT * FROM disciplines WHERE educator_id = ?";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Discipline create(Discipline entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Discipline update(Discipline entity) {
        try {
            return entityManager.merge(entity);
        } catch (IllegalArgumentException ex) {
            throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
        }
    }

    @Override
    public Optional<Discipline> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new DisciplineRowMapper(), id).stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Discipline> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new DisciplineRowMapper(), limit);
    }

    @Override
    public List<Discipline> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new DisciplineRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete discipline entity with id" + id, 1);
    }

    @Override
    public void delete(Discipline entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete discipline entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<Discipline> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(Discipline::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<Discipline> findByDisciplineName(String disciplineName) {
        return jdbcTemplate.query(DISCIPLINE_BY_DISCIPLINE_NAME, new DisciplineRowMapper(), disciplineName)
                .stream()
                .findFirst();
    }

    @Override
    public List<Discipline> findAllBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(DISCIPLINES_BY_SPECIALISM_ID, new DisciplineRowMapper(), specialismId);
    }

    @Override
    public List<Discipline> findAllByAcademicYearId(Long academicYearId) {
        return jdbcTemplate.query(DISCIPLINES_BY_ACADEMIC_YEAR_ID, new DisciplineRowMapper(), academicYearId);
    }

    @Override
    public List<Discipline> findAllBySpecialismIdAndAcademicYearId(Long specialismId, Long academicYearId) {
        return jdbcTemplate.query(DISCIPLINES_BY_SPECIALISM_ID_AND_ACADEMIC_YEAR_ID, new DisciplineRowMapper(), specialismId, academicYearId);
    }

    @Override
    public Optional<Discipline> findByEducatorId(Long educatorId) {
        return jdbcTemplate.query(DISCIPLINE_BY_EDUCATOR_ID, new DisciplineRowMapper(), educatorId)
                .stream()
                .findFirst();
    }
}
