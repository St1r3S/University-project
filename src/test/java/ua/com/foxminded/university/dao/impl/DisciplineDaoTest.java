package ua.com.foxminded.university.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.DailyScheduleRowMapper;
import ua.com.foxminded.university.dao.mappers.DisciplineRowMapper;
import ua.com.foxminded.university.model.lecture.Discipline;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DisciplineDaoTest extends BaseDaoTest {
    public static final String SELECT_DISCIPLINE_BY_ID = "SELECT id, discipline_name, educator_id FROM discipline WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    DisciplineDao dao;

    @PostConstruct
    void init() {
        dao = new DisciplineDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Discipline expected = new Discipline(3L, "Math", 1L);
        dao.create(expected);
        Discipline actual = jdbcTemplate.queryForObject(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 3);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        Discipline expected = new Discipline(1L, "OOP", 1L);
        Discipline actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Discipline expected = new Discipline(1L, "Math", 1L);
        dao.update(expected);
        Discipline actual = jdbcTemplate.queryForObject(SELECT_DISCIPLINE_BY_ID, new DisciplineRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Discipline expected = new Discipline(1L, "OOP", 1L);
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_DISCIPLINE_BY_ID, new DailyScheduleRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Discipline> expected = List.of(new Discipline(1L, "OOP", 1L), new Discipline(2L, "Physics", 1L));
        List<Discipline> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetDisciplinesBySpecialismId() {
        List<Discipline> expected = List.of(new Discipline(1L, "OOP", 1L), new Discipline(2L, "Physics", 1L));
        List<Discipline> actual = dao.getDisciplinesBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetDisciplineByDisciplineName() {
        Discipline expected = new Discipline(1L, "OOP", 1L);
        Discipline actual = dao.getDisciplineByDisciplineName("OOP").get();
        assertEquals(expected, actual);
    }


}