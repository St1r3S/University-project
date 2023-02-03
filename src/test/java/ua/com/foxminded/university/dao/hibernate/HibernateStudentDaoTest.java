package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.StudentDao;
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

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateStudentDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateStudentDaoTest {
    @Autowired
    StudentDao dao;


    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student("jackB25", "pass22", UserRole.STUDENT, "Jack",
                "Black", LocalDate.parse("2003-05-22"), "blackJack@gmail.com", groupId1, specialismId1, academicYearId1);

        dao.save(expected);
        Optional<Student> actual = dao.findById(9L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student(3L, "john05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny05@gmail.com", groupId1, specialismId1, academicYearId1);

        dao.save(expected);
        Optional<Student> actual = dao.findById(3L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", groupId1, specialismId1, academicYearId1);

        Optional<Student> actual = dao.findById(3L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(3L));
    }

    @Test
    void shouldVerifyFindAll() {
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

        List<Student> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
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

        List<Student> actual = dao.findAllById(List.of(3L, 4L));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyCount() {
        long actual = dao.count();

        assertEquals(2L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(3L));
        assertFalse(dao.findById(3L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student entity = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", groupId1, specialismId1, academicYearId1);

        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(dao.findById(3L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(3L, 4L)));
        assertFalse(dao.findById(3L).isPresent());
        assertFalse(dao.findById(4L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllByEntities() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Student> entities = List.of(
                new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                        "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                        groupId1, specialismId1, academicYearId1),
                new Student(4L, "finn25", "pass25", UserRole.STUDENT, "Finn",
                        "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                        groupId2, specialismId1, academicYearId1)
        );

        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(dao.findById(3L).isPresent());
        assertFalse(dao.findById(4L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(dao.findById(3L).isPresent());
        assertFalse(dao.findById(4L).isPresent());
    }

    @Test
    void shouldVerifyFindByUserName() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student expected = new Student(3L, "johny05", "pass05", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com", groupId1, specialismId1, academicYearId1);

        Optional<Student> actual = dao.findByUserName("johny05");

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

        Optional<Student> actual = dao.findByEmail("a.johny@gmail.com");

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

        List<Student> actual = dao.findAllByUserRole(UserRole.STUDENT);

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

        List<Student> actual = dao.findAllByBirthday(LocalDate.parse("2002-05-05"));

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

        List<Student> actual = dao.findAllByGroupId(1L);

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

        List<Student> actual = dao.findAllBySpecialismId(1L);

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

        List<Student> actual = dao.findAllByAcademicYearId(1L);

        assertEquals(expected, actual);
    }

}