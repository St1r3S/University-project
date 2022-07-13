package ua.com.foxminded.university.dao.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.SpecialismRowMapper;
import ua.com.foxminded.university.model.misc.Specialism;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpecialismDaoImplTest extends BaseDaoTest {
    public static final String SELECT_SPECIALISM_BY_ID = "SELECT id, specialism_name FROM specialism WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    SpecialismDaoImpl dao;

    @PostConstruct
    void init() {
        dao = new SpecialismDaoImpl(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Specialism expected = new Specialism("Data analytics");
        dao.save(expected);
        Specialism actual = jdbcTemplate.queryForObject(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        Specialism expected = new Specialism(1L, "Computer science");
        Specialism actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Specialism expected = new Specialism(1L, "Cybersecurity");
        dao.save(expected);
        Specialism actual = jdbcTemplate.queryForObject(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Specialism expected = new Specialism(1L, "Computer science");
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Specialism> expected = List.of(new Specialism(1L, "Computer science"));
        List<Specialism> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetSpecialismsByDisciplineId() {
        List<Specialism> expected = List.of(new Specialism(1L, "Computer science"));
        List<Specialism> actual = dao.getSpecialismsByDisciplineId(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetSpecialismsByEducatorId() {
        List<Specialism> expected = List.of(new Specialism(1L, "Computer science"));
        List<Specialism> actual = dao.getSpecialismsByEducatorId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetSpecialismBySpecialismName() {
        Specialism expected = new Specialism(1L, "Computer science");
        Specialism actual = dao.getSpecialismBySpecialismName("Computer science").get();
        assertEquals(expected, actual);
    }


}