package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.UserRowMapper;
import ua.com.foxminded.university.model.user.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcUserDaoTest extends BaseDaoTest {
    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcUserDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        User expected = new User("test", "test", UserRole.ADMIN, "test",
                "test", LocalDate.parse("1980-05-28"), "test@gmail.com");
        dao.save(expected);
        User actual = jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserRowMapper(), 9);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        User expected = new User(1L, "maccas82", "pass822", UserRole.ADMIN, "Oleg",
                "Maccas", LocalDate.parse("1982-07-22"), "maccas82@gmail.com");
        dao.save(expected);
        User actual = jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindById() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User actual = dao.findById(1L).get();
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
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com"),
                new User(2L, "sobs85", "pass85", UserRole.STAFF, "Jack",
                        "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com"),
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L),
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)

        );
        List<User> actual = dao.findAll(100);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com"),
                new User(2L, "sobs85", "pass85", UserRole.STAFF, "Jack",
                        "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com")
        );
        List<User> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(8L, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        User entity = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<User> entities = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com"),
                new User(2L, "sobs85", "pass85", UserRole.STAFF, "Jack",
                        "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com")
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_USER_BY_ID, new UserRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByLogin() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User actual = dao.findByLogin("maccas82").get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByEmail() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User actual = dao.findByEmail("maccas82@gmail.com").get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByUserRole() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );
        List<User> actual = dao.findAllByUserRole(UserRole.ADMIN);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByBirthday() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );
        List<User> actual = dao.findAllByBirthday(LocalDate.parse("1982-06-22"));
        assertEquals(expected, actual);
    }

}