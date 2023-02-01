package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateGroupDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateGroupDaoTest {
    @Autowired
    GroupDao dao;


    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group expected = new Group("AI-196", specialismId1, academicYearId1);

        dao.save(expected);
        Optional<Group> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group expected = new Group(1L, "AI-196", specialismId1, academicYearId1);

        dao.save(expected);
        Optional<Group> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group expected = new Group(1L, "AI-193", specialismId1, academicYearId1);

        Optional<Group> actual = dao.findById(1L);

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
        List<Group> expected = List.of(
                new Group(1L, "AI-193", specialismId1, academicYearId1),
                new Group(2L, "AI-194", specialismId1, academicYearId1),
                new Group(3L, "AI-195", specialismId1, academicYearId1)
        );

        List<Group> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        List<Group> expected = List.of(
                new Group(1L, "AI-193", specialismId1, academicYearId1),
                new Group(2L, "AI-194", specialismId1, academicYearId1)
        );

        List<Group> actual = dao.findAllById(List.of(1L, 2L));

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
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group entity = new Group(1L, "AI-193", specialismId1, academicYearId1);

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
        List<Group> entities = List.of(
                new Group(1L, "AI-193", specialismId1, academicYearId1),
                new Group(2L, "AI-194", specialismId1, academicYearId1)
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
    void shouldVerifyFindByGroupName() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group expected = new Group(1L, "AI-193", specialismId1, academicYearId1);

        Optional<Group> actual = dao.findByGroupName("AI-193");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllBySpecialismId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        List<Group> expected = List.of(
                new Group(1L, "AI-193", specialismId1, academicYearId1),
                new Group(2L, "AI-194", specialismId1, academicYearId1),
                new Group(3L, "AI-195", specialismId1, academicYearId1)
        );

        List<Group> actual = dao.findAllBySpecialismId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByAcademicYearId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        List<Group> expected = List.of(
                new Group(1L, "AI-193", specialismId1, academicYearId1),
                new Group(2L, "AI-194", specialismId1, academicYearId1),
                new Group(3L, "AI-195", specialismId1, academicYearId1)
        );

        List<Group> actual = dao.findAllBySpecialismId(1L);
        
        assertEquals(expected, actual);
    }

}
