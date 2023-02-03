package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
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
public class EducatorRepositoryTest {

    @Autowired
    EducatorRepository repository;

    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByUserName() {
        Educator expected = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Optional<Educator> actual = repository.findByUserName("grant78");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindByEmail() {
        Educator expected = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Optional<Educator> actual = repository.findByEmail("grant@gmail.com");

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

        List<Educator> actual = repository.findAllByUserRole(UserRole.EDUCATOR);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByBirthday() {
        List<Educator> expected = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR)
        );

        List<Educator> actual = repository.findAllByBirthday(LocalDate.parse("1978-03-28"));

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

        List<Educator> actual = repository.findAllByAcademicRank(AcademicRank.DOCENT);

        assertEquals(expected, actual);
    }
}