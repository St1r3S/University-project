package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateEducatorDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateEducatorDaoTest {

    @Autowired
    EducatorDao dao;

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Educator expected = new Educator("test", "test", UserRole.EDUCATOR, "test",
                "test", LocalDate.parse("1980-05-28"), "test@gmail.com", AcademicRank.PROFESSOR);

        dao.save(expected);
        Optional<Educator> actual = dao.findById(9L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Educator expected = new Educator(5L, "grant788", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant78@gmail.com", AcademicRank.SENIOR_EDUCATOR);

        dao.save(expected);
        Optional<Educator> actual = dao.findById(5L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Educator expected = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Optional<Educator> actual = dao.findById(5L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(5L));
    }

    @Test
    void shouldVerifyFindAll() {
        List<Educator> expected = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)
        );

        List<Educator> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        List<Educator> expected = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT)
        );

        List<Educator> actual = dao.findAllById(List.of(5L, 6L));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyCount() {
        long actual = dao.count();

        assertEquals(4L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(5L));
        assertFalse(dao.findById(5L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        Educator entity = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(dao.findById(5L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(5L, 6L)));
        assertFalse(dao.findById(5L).isPresent());
        assertFalse(dao.findById(6L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllByEntities() {
        List<Educator> entities = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT)
        );

        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(dao.findById(5L).isPresent());
        assertFalse(dao.findById(6L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(dao.findById(5L).isPresent());
        assertFalse(dao.findById(6L).isPresent());
        assertFalse(dao.findById(7L).isPresent());
        assertFalse(dao.findById(8L).isPresent());
    }

    @Test
    void shouldVerifyFindByUserName() {
        Educator expected = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Optional<Educator> actual = dao.findByUserName("grant78");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindByEmail() {
        Educator expected = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Optional<Educator> actual = dao.findByEmail("grant@gmail.com");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllByUserRole() {
        List<Educator> expected = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)
        );

        List<Educator> actual = dao.findAllByUserRole(UserRole.EDUCATOR);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByBirthday() {
        List<Educator> expected = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR)
        );

        List<Educator> actual = dao.findAllByBirthday(LocalDate.parse("1978-03-28"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByAcademicRank() {
        List<Educator> expected = List.of(
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)
        );

        List<Educator> actual = dao.findAllByAcademicRank(AcademicRank.DOCENT);

        assertEquals(expected, actual);
    }
}