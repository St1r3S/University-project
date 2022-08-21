package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.WeeklyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcWeeklyScheduleDaoTest extends BaseDaoTest {
    public static final String SELECT_WEEKLY_SCHEDULE_BY_ID = "SELECT id, week_number FROM weekly_schedule WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcWeeklyScheduleDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcWeeklyScheduleDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        WeeklySchedule expected = new WeeklySchedule(3);
        dao.save(expected);
        WeeklySchedule actual = jdbcTemplate.queryForObject(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 3);
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
        WeeklySchedule expected = new WeeklySchedule(1L, 1);
        WeeklySchedule actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        WeeklySchedule expected = new WeeklySchedule(1L, 4);
        dao.save(expected);
        WeeklySchedule actual = jdbcTemplate.queryForObject(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        WeeklySchedule entity = new WeeklySchedule(1L, 4);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<WeeklySchedule> entities = List.of(
                new WeeklySchedule(1L, 1),
                new WeeklySchedule(2L, 2)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<WeeklySchedule> expected = List.of(
                new WeeklySchedule(1L, 1),
                new WeeklySchedule(2L, 2)
        );
        List<WeeklySchedule> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<WeeklySchedule> expected = List.of(
                new WeeklySchedule(1L, 1),
                new WeeklySchedule(2L, 2)
        );
        List<WeeklySchedule> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(2L, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByWeekNumber() {
        WeeklySchedule expected = new WeeklySchedule(1L, 1);
        WeeklySchedule actual = dao.findByWeekNumber(1).get();
        assertEquals(expected, actual);
    }
}