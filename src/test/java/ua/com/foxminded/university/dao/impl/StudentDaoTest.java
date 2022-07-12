package ua.com.foxminded.university.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.StudentRowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentDaoTest extends BaseDaoTest {
    public static final String SELECT_STUDENT_BY_ID = "SELECT id, first_name, last_name, birthday, email, weekly_schedule_id, " +
            "user_role, group_name, specialism_id FROM student WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    StudentDao dao;

    @PostConstruct
    void init() {
        dao = new StudentDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Student expected = new Student(2L, "Jack", "Black", LocalDate.parse("2003-05-22"), "blackJack@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        dao.create(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        Student expected = new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        Student actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Student expected = new Student(1L, "John", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        dao.update(expected);
        Student actual = jdbcTemplate.queryForObject(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Student expected = new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L);
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_STUDENT_BY_ID, new StudentRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsByLectureId() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.getStudentsByLectureId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsBySpecialismId() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.getStudentsBySpecialismId(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsByGroupName() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.getStudentsByGroupName("NAI-196");
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyGetStudentsByBirthday() {
        List<Student> expected = List.of(new Student(1L, "Alex", "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", 1L, UserRole.STUDENT, "NAI-196", 1L));
        List<Student> actual = dao.getStudentsByBirthday(LocalDate.parse("2002-05-05"));
        assertEquals(expected, actual);
    }

}