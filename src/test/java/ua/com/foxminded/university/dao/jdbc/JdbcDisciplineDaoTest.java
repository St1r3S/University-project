package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.DisciplineRowMapper;
import ua.com.foxminded.university.model.lecture.Discipline;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcDisciplineDaoTest extends BaseDaoTest {
    public static final String SELECT_DISCIPLINE_BY_ID = "SELECT id, discipline_name, educator_id FROM discipline WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcDisciplineDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcDisciplineDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Discipline expected = new Discipline("Math", 1L);
        dao.save(expected);
        Discipline actual = jdbcTemplate.queryForObject(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 3);
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
        Discipline expected = new Discipline(1L, "OOP", 1L);
        Discipline actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Discipline expected = new Discipline(1L, "Math", 1L);
        dao.save(expected);
        Discipline actual = jdbcTemplate.queryForObject(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Discipline entity = new Discipline(1L, "Math", 1L);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Discipline> entities = List.of(
                new Discipline(1L, "OOP", 1L),
                new Discipline(2L, "Physics", 1L)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Discipline> expected = List.of(
                new Discipline(1L, "OOP", 1L),
                new Discipline(2L, "Physics", 1L)
        );
        List<Discipline> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Discipline> expected = List.of(
                new Discipline(1L, "OOP", 1L),
                new Discipline(2L, "Physics", 1L)
        );
        List<Discipline> actual = dao.findAllById(List.of(1L, 2L));
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
    void shouldVerifyGetDisciplinesBySpecialismId() {
        List<Discipline> expected = List.of(new Discipline(1L, "OOP", 1L), new Discipline(2L, "Physics", 1L));
        List<Discipline> actual = dao.findAllBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetDisciplineByDisciplineName() {
        Discipline expected = new Discipline(1L, "OOP", 1L);
        Discipline actual = dao.findByDisciplineName("OOP").get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyExpelAndEnroll() {
        assertDoesNotThrow(() -> dao.expelDisciplineSpecialism(1L, 1L));
        assertDoesNotThrow(() -> dao.expelDisciplineSpecialism(2L, 1L));
        assertFalse(dao.findAllBySpecialismId(1L).stream().findFirst().isPresent());
        assertDoesNotThrow(() -> dao.enrollDisciplineSpecialism(1L, 1L));
        assertTrue(dao.findAllBySpecialismId(1L).stream().findFirst().isPresent());
    }

}