package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AcademicYearRepositoryTest {

    @Autowired
    AcademicYearRepository repository;

    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByYearNumber() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER)
        );
        List<AcademicYear> actual = repository.findByYearNumber(1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindBySemesterType() {
        List<AcademicYear> expected = List.of(
                new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER),
                new AcademicYear(2L, 2, SemesterType.FALL_SEMESTER),
                new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER)
        );
        List<AcademicYear> actual = repository.findBySemesterType(SemesterType.FALL_SEMESTER);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByYearNumberAndSemesterType() {
        AcademicYear expected = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Optional<AcademicYear> actual = repository.findByYearNumberAndSemesterType(1, SemesterType.FALL_SEMESTER);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}

