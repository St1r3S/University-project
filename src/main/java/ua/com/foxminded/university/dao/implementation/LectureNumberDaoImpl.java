package ua.com.foxminded.university.dao.implementation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.mappers.LectureNumberRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import java.util.List;
import java.util.Optional;

@Repository
public class LectureNumberDaoImpl extends AbstractCrudDao<LectureNumber, Long> {
    public static final String LECTURE_NUMBER_ID = "id";
    public static final String LECTURE_NUMBER = "number";
    public static final String LECTURE_TIME_START = "time_start";
    public static final String LECTURE_TIME_END = "time_end";
    //    public static final String CREATE = "INSERT INTO lecture_number(number, time_start, time_end) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id = ?";
    public static final String UPDATE = "UPDATE lecture_number SET number = ?, time_start = ?, time_end = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM lecture_number WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, number, time_start, time_end FROM lecture_number LIMIT 100";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public LectureNumberDaoImpl(JdbcTemplate jdbcTemplate) {
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
    public Optional<LectureNumber> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LectureNumberRowMapper(), id).stream().findFirst();
    }

    @Override
    public LectureNumber update(LectureNumber entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getTimeStart(), entity.getTimeEnd(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(LectureNumber entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<LectureNumber> findAll() {
        return jdbcTemplate.query(FIND_ALL, new LectureNumberRowMapper());
    }
}
