package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.EducatorRowMapper;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcEducatorDaoTest extends BaseDaoTest {
    public static final String SELECT_EDUCATOR_BY_ID = "SELECT id, first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, educator_position " +
            "FROM educator WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcEducatorDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcEducatorDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Educator expected = new Educator("Jack", "Black", LocalDate.parse("1965-08-22"), "blackJack@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer");
        dao.save(expected);
        Educator actual = jdbcTemplate.queryForObject(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 3);
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
        Educator expected = new Educator(1L, "John", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer");
        Educator actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Educator expected = new Educator(1L, "Jack", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer");
        dao.save(expected);
        Educator actual = jdbcTemplate.queryForObject(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Educator entity = new Educator(1L, "Jack", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer");
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Educator> entities = List.of(
                new Educator(1L, "John", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer"),
                new Educator(2L, "Alex", "Test", LocalDate.parse("1980-03-28"), "alex@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer")
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_EDUCATOR_BY_ID, new EducatorRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Educator> expected = List.of(
                new Educator(1L, "John", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer"),
                new Educator(2L, "Alex", "Test", LocalDate.parse("1980-03-28"), "alex@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer")
        );
        List<Educator> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Educator> expected = List.of(
                new Educator(1L, "John", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer"),
                new Educator(2L, "Alex", "Test", LocalDate.parse("1980-03-28"), "alex@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer")
        );
        List<Educator> actual = dao.findAllById(List.of(1L, 2L));
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
    void shouldVerifyGetEducatorsBySpecialismId() {
        List<Educator> expected = List.of(new Educator(1L, "John", "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", 1L, UserRole.EDUCATOR, "Lecturer"));
        List<Educator> actual = dao.findAllBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyExpelAndEnroll() {
        assertDoesNotThrow(() -> dao.expelEducatorSpecialism(1L, 1L));
        assertFalse(dao.findAllBySpecialismId(1L).stream().findFirst().isPresent());
        assertDoesNotThrow(() -> dao.enrollEducatorSpecialism(1L, 1L));
        assertTrue(dao.findAllBySpecialismId(1L).stream().findFirst().isPresent());
    }
}