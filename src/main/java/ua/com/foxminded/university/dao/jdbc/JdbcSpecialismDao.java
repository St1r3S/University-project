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
    private static final String SPECIALISMS_BY_EDUCATOR_ID = "SELECT s.id, s.specialism_name FROM specialism AS s " +
            "INNER JOIN educator_specialism AS es ON s.id = es.specialism_id " +
            "INNER JOIN educator AS e ON es.educator_id = e.id where e.id = ?";
    private static final String SPECIALISM_BY_SPECIALISM_NAME = "SELECT id, specialism_name " +
            "FROM specialism WHERE specialism_name = ?";

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
    public List<Specialism> findAll() {
        return jdbcTemplate.query(FIND_ALL, new SpecialismRowMapper());
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
