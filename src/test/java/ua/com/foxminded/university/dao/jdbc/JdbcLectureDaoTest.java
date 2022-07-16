package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.LectureRowMapper;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcLectureDaoTest extends BaseDaoTest {
    public static final String SELECT_LECTURE_BY_ID = "SELECT id, discipline_id, lecture_number_id, room_id, daily_schedule_id " +
            "FROM lecture WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcLectureDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcLectureDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Lecture expected = new Lecture(2L, 1L, 1L, 1L);
        dao.save(expected);
        Lecture actual = jdbcTemplate.queryForObject(SELECT_LECTURE_BY_ID, new LectureRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        Lecture expected = new Lecture(1L, 1L, 1L, 1L, 1L);
        Lecture actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Lecture expected = new Lecture(1L, 2L, 1L, 1L, 1L);
        dao.save(expected);
        Lecture actual = jdbcTemplate.queryForObject(SELECT_LECTURE_BY_ID, new LectureRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.deleteById(1L);
        assertFalse(jdbcTemplate.query(SELECT_LECTURE_BY_ID, new LectureRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetLecturesByStudentId() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAllByStudentId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetLecturesByWeekNumber() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAllByWeekNumber(1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetLecturesByDayOfWeekAndWeekNumber() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAllByDayOfWeekAndWeekNumber(DayOfWeek.THURSDAY, 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetLecturesByDate() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAllByDate(LocalDate.parse("2022-09-01"));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetLecturesByRoomNumber() {
        List<Lecture> expected = List.of(new Lecture(1L, 1L, 1L, 1L, 1L));
        List<Lecture> actual = dao.findAllByRoomNumber("404");
        assertEquals(expected, actual);
    }

}