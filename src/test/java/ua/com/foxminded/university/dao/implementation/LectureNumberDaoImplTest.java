package ua.com.foxminded.university.dao.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.LectureNumberRowMapper;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LectureNumberDaoImplTest extends BaseDaoTest {
    public static final String SELECT_LECTURE_NUMBER_BY_ID = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    LectureNumberDaoImpl dao;

    @PostConstruct
    void init() {
        dao = new LectureNumberDaoImpl(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        LectureNumber expected = new LectureNumber(2, LocalTime.parse("09:50:00"), LocalTime.parse("11:25:00"));
        dao.save(expected);
        LectureNumber actual = jdbcTemplate.queryForObject(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        LectureNumber expected = new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00"));
        LectureNumber actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        LectureNumber expected = new LectureNumber(1L, 1, LocalTime.parse("08:10:00"), LocalTime.parse("09:35:00"));
        dao.save(expected);
        LectureNumber actual = jdbcTemplate.queryForObject(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        LectureNumber expected = new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00"));
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<LectureNumber> expected = List.of(new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00")));
        List<LectureNumber> actual = dao.findAll();
        assertEquals(expected, actual);
    }
}