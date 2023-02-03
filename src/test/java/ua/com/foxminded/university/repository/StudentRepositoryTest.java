package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class StudentRepositoryTest {
    @Autowired
    StudentRepository repository;


    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByUserName() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", groupId1, specialismId1, academicYearId1);

        Optional<Student> actual = repository.findByUserName("johny05");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindByEmail() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", groupId1, specialismId1, academicYearId1);

        Optional<Student> actual = repository.findByEmail("a.johny@gmail.com");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllByUserRole() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        groupId2, specialismId1, academicYearId1)
        );

        List<Student> actual = repository.findAllByUserRole(UserRole.STUDENT);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByBirthday() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1)
        );

        List<Student> actual = repository.findAllByBirthday(LocalDate.parse("2002-05-05"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByGroupId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1)
        );

        List<Student> actual = repository.findAllByGroupId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllBySpecialismId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        groupId2, specialismId1, academicYearId1)
        );

        List<Student> actual = repository.findAllBySpecialismId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByAcademicYearId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Student> expected = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        groupId2, specialismId1, academicYearId1)
        );

        List<Student> actual = repository.findAllByAcademicYearId(1L);

        assertEquals(expected, actual);
    }

}