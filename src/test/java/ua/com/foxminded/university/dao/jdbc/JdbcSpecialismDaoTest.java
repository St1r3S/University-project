package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.SpecialismRowMapper;
import ua.com.foxminded.university.model.misc.Specialism;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcSpecialismDaoTest extends BaseDaoTest {
    public static final String SELECT_SPECIALISM_BY_ID = "SELECT id, specialism_name FROM specialism WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcSpecialismDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcSpecialismDao(jdbcTemplate);
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
        Specialism actual = dao.findById(1L).get();
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
        dao.deleteById(1L);
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
        List<Specialism> actual = dao.findAllByDisciplineId(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetSpecialismsByEducatorId() {
        List<Specialism> expected = List.of(new Specialism(1L, "Computer science"));
        List<Specialism> actual = dao.findAllByEducatorId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetSpecialismBySpecialismName() {
        Specialism expected = new Specialism(1L, "Computer science");
        Specialism actual = dao.findBySpecialismName("Computer science").get();
        assertEquals(expected, actual);
    }


}