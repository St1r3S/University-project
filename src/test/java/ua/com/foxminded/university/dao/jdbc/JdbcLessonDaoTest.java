package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.jdbc.mappers.LessonRowMapper;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcLessonDaoTest {
    public static final String SELECT_LESSON_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcLessonDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcLessonDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Lesson expected = new Lesson(2L, 2L, LessonNumber.SECOND_LESSON, 1L, 2L);
        dao.save(expected);
        Lesson actual = jdbcTemplate.queryForObject(SELECT_LESSON_BY_ID, new LessonRowMapper(), 4);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Lesson expected = new Lesson(1L, 1L, 1L, LessonNumber.FIFTH_LESSON, 1L, 2L);
        dao.save(expected);
        Lesson actual = jdbcTemplate.queryForObject(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindById() {
        Lesson expected = new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L);
        Lesson actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Lesson> expected = List.of(
                new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L),
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L),
                new Lesson(3L, 1L, 2L, LessonNumber.FIRST_LESSON, 3L, 1L)
        );
        List<Lesson> actual = dao.findAll(100);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Lesson> expected = List.of(
                new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L),
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L)
        );
        List<Lesson> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(3L, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Lesson entity = new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1)

                .stream()

                .findFirst()

                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Lesson> entities = List.of(
                new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L),
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_LESSON_BY_ID, new LessonRowMapper(), 3)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByDisciplineId() {
        List<Lesson> expected = List.of(
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L)
        );
        List<Lesson> actual = dao.findAllByDisciplineId(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByGroupId() {
        List<Lesson> expected = List.of(
                new Lesson(3L, 1L, 2L, LessonNumber.FIRST_LESSON, 3L, 1L)
        );
        List<Lesson> actual = dao.findAllByGroupId(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByLessonNumber() {
        List<Lesson> expected = List.of(
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L)
        );
        List<Lesson> actual = dao.findAllByLessonNumber(LessonNumber.SECOND_LESSON);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByRoomId() {
        List<Lesson> expected = List.of(
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L)
        );
        List<Lesson> actual = dao.findAllByRoomId(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByScheduleDayId() {
        List<Lesson> expected = List.of(
                new Lesson(1L, 1L, 1L, LessonNumber.FIRST_LESSON, 1L, 1L),
                new Lesson(2L, 2L, 1L, LessonNumber.SECOND_LESSON, 2L, 1L),
                new Lesson(3L, 1L, 2L, LessonNumber.FIRST_LESSON, 3L, 1L)
        );
        List<Lesson> actual = dao.findAllByScheduleDayId(1L);
        assertEquals(expected, actual);
    }
}