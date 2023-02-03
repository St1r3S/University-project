package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.DisciplineDao;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateDisciplineDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class HibernateDisciplineDaoTest {
    @Autowired
    DisciplineDao dao;

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT);
        Discipline expected = new Discipline("Database Theory", specialismId1, academicYear, educator);

        dao.save(expected);
        Optional<Discipline> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT);
        Discipline expected = new Discipline(1L, "Database Theory", specialismId1, academicYear, educator);

        dao.save(expected);
        Optional<Discipline> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline expected = new Discipline(1L, "Math", specialismId1, academicYear, educator);

        Optional<Discipline> actual = dao.findById(1L);


        assertTrue(actual.isPresent());
        assertEquals(expected.getSpecialism(), actual.get().getSpecialism());
        assertEquals(expected.getAcademicYear(), actual.get().getAcademicYear());
        assertEquals(expected.getEducator(), actual.get().getEducator());
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
        AcademicYear academicYearId3 = new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Educator educatorId7 = new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT);

        List<Discipline> expected = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6),
                new Discipline(3L, "OOP", specialismId1, academicYearId3, educatorId7)
        );
        List<Discipline> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        List<Discipline> expected = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6)
        );
        List<Discipline> actual = dao.findAllById(List.of(1L, 2L));

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
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline entity = new Discipline(1L, "Math", specialismId1, academicYear, educator);

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
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        List<Discipline> entities = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6)
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
    void shouldVerifyFindByDisciplineName() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline expected = new Discipline(1L, "Math", specialismId1, academicYear, educator);

        Optional<Discipline> actual = dao.findByDisciplineName("Math");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllBySpecialismId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        AcademicYear academicYearId3 = new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Educator educatorId7 = new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT);

        List<Discipline> expected = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6),
                new Discipline(3L, "OOP", specialismId1, academicYearId3, educatorId7)
        );

        List<Discipline> actual = dao.findAllBySpecialismId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByAcademicYearId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        List<Discipline> expected = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6)
        );
        List<Discipline> actual = dao.findAllByAcademicYearId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllBySpecialismIdAndAcademicYearId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        List<Discipline> expected = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6)
        );
        List<Discipline> actual = dao.findAllBySpecialismIdAndAcademicYearId(1L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByEducatorId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline expected = new Discipline(1L, "Math", specialismId1, academicYear, educator);

        Optional<Discipline> actual = dao.findByEducatorId(5L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}