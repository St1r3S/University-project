package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.hibernate.mappers.StudentRowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HibernateStudentDaoTest extends BaseDaoTest {
    public static final Integer STUDENT_TYPE_CODE = UserType.STUDENT.getTypeCode();
    public static final String SELECT_STUDENT_BY_ID = "SELECT * FROM users WHERE id = ? AND user_type = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    HibernateStudentDao dao;

    @PostConstruct
    void init() {
        dao = new HibernateStudentDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Student expected = new Student("jackB25", "pass22", UserRole.STUDENT, "Jack",
                "Black", LocalDate.parse("2003-05-22"), "blackJack@gmail.com", 1L, 1L, 1L);
        dao.save(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 9, STUDENT_TYPE_CODE);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Student expected = new Student(3L, "john05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny05@gmail.com", 1L, 1L, 1L);
        dao.save(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindById() {
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, 1L, 1L);
        Student actual = dao.findById(3L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(3L));
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        List<Student> actual = dao.findAll(100);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        List<Student> actual = dao.findAllById(List.of(3L, 4L));
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
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(3L));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Student entity = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, 1L, 1L);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(3L, 4L)));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 4, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Student> entities = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 4, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 4, STUDENT_TYPE_CODE)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByLogin() {
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, 1L, 1L);
        Student actual = dao.findByLogin("johny05").get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByEmail() {
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, 1L, 1L);
        Student actual = dao.findByEmail("a.johny@gmail.com").get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByUserRole() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        List<Student> actual = dao.findAllByUserRole(UserRole.STUDENT);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByBirthday() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L)
        );
        List<Student> actual = dao.findAllByBirthday(LocalDate.parse("2002-05-05"));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByGroupId() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L)
        );
        List<Student> actual = dao.findAllByGroupId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllBySpecialismId() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        List<Student> actual = dao.findAllBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllByAcademicYearId() {
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        1L, 1L, 1L),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        2L, 1L, 1L)
        );
        List<Student> actual = dao.findAllByAcademicYearId(1L);
        assertEquals(expected, actual);
    }

}