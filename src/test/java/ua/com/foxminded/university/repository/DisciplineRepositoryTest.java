package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DisciplineRepositoryTest {
    @Autowired
    DisciplineRepository repository;

    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByDisciplineName() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline expected = new Discipline(1L, "Math", specialismId1, academicYear, educator);

        Optional<Discipline> actual = repository.findByDisciplineName("Math");

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

        List<Discipline> actual = repository.findAllBySpecialismId(1L);

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
        List<Discipline> actual = repository.findAllByAcademicYearId(1L);

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
        List<Discipline> actual = repository.findAllBySpecialismIdAndAcademicYearId(1L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByEducatorId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educator = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline expected = new Discipline(1L, "Math", specialismId1, academicYear, educator);

        Optional<Discipline> actual = repository.findByEducatorId(5L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}