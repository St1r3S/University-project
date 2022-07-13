package ua.com.foxminded.university.dao.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.WeeklyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WeeklyScheduleDaoImplTest extends BaseDaoTest {
    public static final String SELECT_WEEKLY_SCHEDULE_BY_ID = "SELECT id, week_number FROM weekly_schedule WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    WeeklyScheduleDaoImpl dao;

    @PostConstruct
    void init() {
        dao = new WeeklyScheduleDaoImpl(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        WeeklySchedule expected = new WeeklySchedule(2);
        dao.save(expected);
        WeeklySchedule actual = jdbcTemplate.queryForObject(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        WeeklySchedule expected = new WeeklySchedule(1L, 1);
        WeeklySchedule actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        WeeklySchedule expected = new WeeklySchedule(1L, 2);
        dao.save(expected);
        WeeklySchedule actual = jdbcTemplate.queryForObject(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        WeeklySchedule expected = new WeeklySchedule(1L, 1);
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_WEEKLY_SCHEDULE_BY_ID, new WeeklyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<WeeklySchedule> expected = List.of(new WeeklySchedule(1L, 1));
        List<WeeklySchedule> actual = dao.findAll();
        assertEquals(expected, actual);
    }
}