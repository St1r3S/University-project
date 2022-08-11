package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.LectureNumberRowMapper;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcLectureNumberDaoTest extends BaseDaoTest {
    public static final String SELECT_LECTURE_NUMBER_BY_ID = "SELECT id, number, time_start, time_end FROM lecture_number WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcLectureNumberDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcLectureNumberDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        LectureNumber expected = new LectureNumber(3, LocalTime.parse("11:40:00"), LocalTime.parse("13:15:00"));
        dao.save(expected);
        LectureNumber actual = jdbcTemplate.queryForObject(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 3);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        LectureNumber expected = new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00"));
        LectureNumber actual = dao.findById(1L).get();
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
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        LectureNumber entity = new LectureNumber(1L, 1, LocalTime.parse("08:10:00"), LocalTime.parse("09:35:00"));
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<LectureNumber> entities = List.of(
                new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00")),
                new LectureNumber(2L, 2, LocalTime.parse("09:50:00"), LocalTime.parse("11:25:00"))
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_NUMBER_BY_ID, new LectureNumberRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<LectureNumber> expected = List.of(
                new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00")),
                new LectureNumber(2L, 2, LocalTime.parse("09:50:00"), LocalTime.parse("11:25:00"))
        );
        List<LectureNumber> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<LectureNumber> expected = List.of(
                new LectureNumber(1L, 1, LocalTime.parse("08:00:00"), LocalTime.parse("09:35:00")),
                new LectureNumber(2L, 2, LocalTime.parse("09:50:00"), LocalTime.parse("11:25:00"))
        );
        List<LectureNumber> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(2L, actual);
    }
}