package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.LectureNumberRowMapper;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import java.util.List;
import java.util.Optional;

public class LectureNumberDao implements DAO<Long, LectureNumber> {
    public static final String LECTURE_NUMBER_ID = "id";
    public static final String LECTURE_NUMBER = "number";
    public static final String LECTURE_TIME_START = "time_start";
    public static final String LECTURE_TIME_END = "time_end";
    public static final String CREATE = "INSERT INTO lecture_number(number, time_start, time_end) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id = ?";
    public static final String UPDATE = "UPDATE lecture_number SET number = ?, time_start = ?, time_end = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM lecture_number WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, number, time_start, time_end FROM lecture_number LIMIT 100";

    private final JdbcTemplate jdbcTemplate;

    public LectureNumberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(LectureNumber entity) {
        jdbcTemplate.update(CREATE, entity.getNumber(), entity.getTimeStart(), entity.getTimeEnd());
    }

    @Override
    public Optional<LectureNumber> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LectureNumberRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(LectureNumber entity) {
        jdbcTemplate.update(UPDATE, entity.getNumber(), entity.getTimeStart(), entity.getTimeEnd(), entity.getId());
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
