package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.DailyScheduleRowMapper;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcDailyScheduleDaoTest extends BaseDaoTest {
    public static final String SELECT_DAILY_SCHEDULE_BY_ID = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcDailyScheduleDao dao;


    @PostConstruct
    void init() {
        dao = new JdbcDailyScheduleDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        DailySchedule expected = new DailySchedule(LocalDate.of(2022, 9, 2), DayOfWeek.FRIDAY, 1L);
        dao.save(expected);
        DailySchedule actual = jdbcTemplate.queryForObject(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 3);
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
        DailySchedule expected = new DailySchedule(1L, LocalDate.of(2022, 9, 1), DayOfWeek.THURSDAY, 1L);
        DailySchedule actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        DailySchedule expected = new DailySchedule(1L, LocalDate.of(2022, 9, 2), DayOfWeek.FRIDAY, 1L);
        dao.save(expected);
        DailySchedule actual = jdbcTemplate.queryForObject(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }


    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        DailySchedule entity = new DailySchedule(1L, LocalDate.of(2022, 9, 1), DayOfWeek.THURSDAY, 1L);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<DailySchedule> entities = List.of(
                new DailySchedule(1L, LocalDate.of(2022, 9, 1), DayOfWeek.THURSDAY, 1L),
                new DailySchedule(2L, LocalDate.of(2022, 9, 8), DayOfWeek.THURSDAY, 2L)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new DailyScheduleRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<DailySchedule> expected = List.of(
                new DailySchedule(1L, LocalDate.of(2022, 9, 1), DayOfWeek.THURSDAY, 1L),
                new DailySchedule(2L, LocalDate.of(2022, 9, 8), DayOfWeek.THURSDAY, 2L)
        );
        List<DailySchedule> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<DailySchedule> expected = List.of(
                new DailySchedule(1L, LocalDate.of(2022, 9, 1), DayOfWeek.THURSDAY, 1L),
                new DailySchedule(2L, LocalDate.of(2022, 9, 8), DayOfWeek.THURSDAY, 2L)
        );
        List<DailySchedule> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(2L, actual);
    }
}