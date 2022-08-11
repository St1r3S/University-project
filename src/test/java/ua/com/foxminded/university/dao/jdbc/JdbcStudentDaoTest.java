package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.StudentRowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcStudentDaoTest extends BaseDaoTest {
    public static final String SELECT_STUDENT_BY_ID = "SELECT id, first_name, last_name, birthday, email, weekly_schedule_id, " +
            "user_role, group_name, specialism_id FROM student WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcStudentDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcStudentDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Student expected = new Student("Jack", "Black", LocalDate.parse("2003-05-22"), "blackJack@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        dao.save(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 3);
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
        Student expected = new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        Student actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Student expected = new Student(1L, "John", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        dao.save(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Student entity = new Student(1L, "John", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Student> entities = List.of(
                new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L),
                new Student(2L, "Finn", "Test", LocalDate.parse("2002-04-25"), "finn@gmail.com", 1L, UserRole.STUDENT, "AI-195", 2L)
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 2).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Student> expected = List.of(
                new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L),
                new Student(2L, "Finn", "Test", LocalDate.parse("2002-04-25"), "finn@gmail.com", 1L, UserRole.STUDENT, "AI-195", 2L)
        );
        List<Student> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Student> expected = List.of(
                new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L),
                new Student(2L, "Finn", "Test", LocalDate.parse("2002-04-25"), "finn@gmail.com", 1L, UserRole.STUDENT, "AI-195", 2L)
        );
        List<Student> actual = dao.findAllById(List.of(1L, 2L));
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
    void shouldVerifyGetStudentsByLectureId() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.findAllByLectureId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsBySpecialismId() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.findAllBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsByGroupName() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.findAllByGroupName("NAI-196");
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsByBirthday() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.findAllByBirthday(LocalDate.parse("2002-05-05"));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyExpelAndEnroll() {
        assertDoesNotThrow(() -> dao.expelLectureStudent(1L, 1L));
        assertFalse(dao.findAllByLectureId(1L).stream().findFirst().isPresent());
        assertDoesNotThrow(() -> dao.enrollLectureStudent(1L, 1L));
        assertTrue(dao.findAllByLectureId(1L).stream().findFirst().isPresent());
    }

}