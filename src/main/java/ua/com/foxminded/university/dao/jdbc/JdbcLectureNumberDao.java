package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.LectureNumberDao;
import ua.com.foxminded.university.dao.jdbc.mappers.LectureNumberRowMapper;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLectureNumberDao extends AbstractCrudDao<LectureNumber, Long> implements LectureNumberDao {
    public static final String LECTURE_NUMBER_ID = "id";
    public static final String LECTURE_NUMBER = "number";
    public static final String LECTURE_TIME_START = "time_start";
    public static final String LECTURE_TIME_END = "time_end";
    //    public static final String CREATE = "INSERT INTO lecture_number(number, time_start, time_end) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id = ?";
    public static final String UPDATE = "UPDATE lecture_number SET number = ?, time_start = ?, time_end = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM lecture_number WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, number, time_start, time_end FROM lecture_number LIMIT 100";
    public static final String COUNT = "SELECT count(*) FROM lecture_number";
    public static final String FIND_ALL_BY_IDS = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id IN (%s)";
    public static final String DELETE_ALL = "DELETE FROM lecture_number";
    public static final String DELETE_ALL_BY_IDS = "DELETE FROM lecture_number WHERE id IN (%s)";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcLectureNumberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("lecture_number").usingGeneratedKeyColumns("id");

    }

    @Override
    public LectureNumber create(LectureNumber entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(LECTURE_NUMBER, entity.getNumber())
                        .addValue(LECTURE_TIME_START, entity.getTimeStart())
                        .addValue(LECTURE_TIME_END, entity.getTimeEnd())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<LectureNumber> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LectureNumberRowMapper(), id).stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public LectureNumber update(LectureNumber entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getTimeStart(), entity.getTimeEnd(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete lecture number entity with id" + id, 1);
    }

    @Override
    public void delete(LectureNumber entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete lecture number entity with id" + entity.getId(), 1);

    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<LectureNumber> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(LectureNumber::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<LectureNumber> findAll() {
        return jdbcTemplate.query(FIND_ALL, new LectureNumberRowMapper());
    }

    @Override
    public List<LectureNumber> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new LectureNumberRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }
}
