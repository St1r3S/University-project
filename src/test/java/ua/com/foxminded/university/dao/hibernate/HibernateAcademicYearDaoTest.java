package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.AcademicYearDao;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateAcademicYearDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateAcademicYearDaoTest {

    @Autowired
    AcademicYearDao dao;

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        AcademicYear expected = new AcademicYear(1, SemesterType.SPRING_SEMESTER);

        dao.save(expected);
        Optional<AcademicYear> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        AcademicYear expected = new AcademicYear(1L, 1, SemesterType.SPRING_SEMESTER);

        dao.save(expected);
        Optional<AcademicYear> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        AcademicYear expected = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);

        Optional<AcademicYear> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    void shouldVerifyFindAll() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER),
                new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER),
                new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER)
        );

        List<AcademicYear> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER),
                new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER)
        );

        List<AcademicYear> actual = dao.findAllById(List.of(1L, 2L));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyCount() {
        long actual = dao.count();

        assertEquals(3L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        AcademicYear entity = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);

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
        List<AcademicYear> entities = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER),
                new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER)
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
    }

    @Test
    void shouldVerifyFindByYearNumber() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER)
        );
        List<AcademicYear> actual = dao.findByYearNumber(1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindBySemesterType() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER),
                new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER),
                new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER)
        );
        List<AcademicYear> actual = dao.findBySemesterType(SemesterType.FALL_SEMESTER);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByYearNumberAndSemesterType() {
        AcademicYear expected = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        AcademicYear actual = dao.findByYearNumberAndSemesterType(1, SemesterType.FALL_SEMESTER).get();

        assertEquals(expected, actual);
    }
}

