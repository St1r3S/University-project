package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.UserDao;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateUserDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateUserDaoTest {
    @Autowired
    UserDao dao;


    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        User expected = new User("test", "test", UserRole.ADMIN, "test",
                "test", LocalDate.parse("1980-05-28"), "test@gmail.com");

        dao.save(expected);
        Optional<User> actual = dao.findById(9L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        User expected = new User(1L, "maccas82", "pass822", UserRole.ADMIN, "Oleg",
                "Maccas", LocalDate.parse("1982-07-22"), "maccas82@gmail.com");

        dao.save(expected);
        Optional<User> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        Optional<User> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    void shouldVerifyFindAll() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com"),
                new User(2L, "sobs85", "pass85", UserRole.STAFF, "Jack",
                        "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com"),
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        groupId2, specialismId1, academicYearId1),
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)
        );
        List<User> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
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
    void shouldVerifyCount() {
        long actual = dao.count();

        assertEquals(8L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        User entity = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllByEntities() {
        List<User> entities = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com"),
                new User(2L, "sobs85", "pass85", UserRole.STAFF, "Jack",
                        "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com")
        );

        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
        assertFalse(dao.findById(3L).isPresent());
        assertFalse(dao.findById(4L).isPresent());
        assertFalse(dao.findById(5L).isPresent());
        assertFalse(dao.findById(6L).isPresent());
        assertFalse(dao.findById(7L).isPresent());
        assertFalse(dao.findById(8L).isPresent());
    }

    @Test
    void shouldVerifyFindByUserName() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        Optional<User> actual = dao.findByUserName("maccas82");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindByEmail() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        Optional<User> actual = dao.findByEmail("maccas82@gmail.com");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllByUserRole() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );

        List<User> actual = dao.findAllByUserRole(UserRole.ADMIN);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByBirthday() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );

        List<User> actual = dao.findAllByBirthday(LocalDate.parse("1982-06-22"));

        assertEquals(expected, actual);
    }

}