package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.SpecialismDao;
import ua.com.foxminded.university.dao.jdbc.mappers.SpecialismRowMapper;
import ua.com.foxminded.university.model.misc.Specialism;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSpecialismDao extends AbstractCrudDao<Specialism, Long> implements SpecialismDao {
    public static final String SPECIALISM_ID = "id";
    public static final String SPECIALISM_NAME = "specialism_name";
    //    public static final String CREATE = "INSERT INTO specialism(specialism_name) VALUES (?)";
    public static final String RETRIEVE = "SELECT id, specialism_name FROM specialism WHERE id = ?";
    public static final String UPDATE = "UPDATE specialism SET specialism_name = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM specialism WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, specialism_name FROM specialism LIMIT 100";
    public static final String SPECIALISMS_BY_DISCIPLINE_ID = "SELECT s.id, s.specialism_name FROM specialism AS s " +
            "INNER JOIN discipline_specialism AS ds ON s.id = ds.specialism_id " +
            "INNER JOIN discipline AS d ON ds.discipline_id = d.id where d.id = ?";
    public static final String COUNT = "SELECT count(*) FROM specialism";
    public static final String DELETE_ALL_BY_IDS = "DELETE FROM specialism WHERE id IN (%s)";
    private static final String SPECIALISMS_BY_EDUCATOR_ID = "SELECT s.id, s.specialism_name FROM specialism AS s " +
            "INNER JOIN educator_specialism AS es ON s.id = es.specialism_id " +
            "INNER JOIN educator AS e ON es.educator_id = e.id where e.id = ?";
    private static final String SPECIALISM_BY_SPECIALISM_NAME = "SELECT id, specialism_name " +
            "FROM specialism WHERE specialism_name = ?";
    private static final String FIND_ALL_BY_IDS = "SELECT id, specialism_name FROM specialism WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM specialism";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcSpecialismDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("specialism").usingGeneratedKeyColumns("id");
    }

    @Override
    public Specialism create(Specialism entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(SPECIALISM_NAME, entity.getSpecialismName())
        );
        entity.setId(id.longValue());
        return entity;

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
    public Specialism update(Specialism entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getSpecialismName(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
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
    public List<Specialism> findAll() {
        return jdbcTemplate.query(FIND_ALL, new SpecialismRowMapper());
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
    public List<Specialism> findAllByDisciplineId(Long disciplineId) {
        return jdbcTemplate.query(SPECIALISMS_BY_DISCIPLINE_ID, new SpecialismRowMapper(), disciplineId);
    }

    @Override
    public List<Specialism> findAllByEducatorId(Long educatorId) {
        return jdbcTemplate.query(SPECIALISMS_BY_EDUCATOR_ID, new SpecialismRowMapper(), educatorId);
    }

    @Override
    public Optional<Specialism> findBySpecialismName(String specialismName) {
        return jdbcTemplate.query(SPECIALISM_BY_SPECIALISM_NAME, new SpecialismRowMapper(), specialismName)
                .stream()
                .findFirst();
    }
}
