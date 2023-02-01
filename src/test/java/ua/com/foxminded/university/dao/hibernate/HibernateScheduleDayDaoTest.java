package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.ScheduleDayDao;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateScheduleDayDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateScheduleDayDaoTest {
    @Autowired
    ScheduleDayDao dao;


    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        ScheduleDay expected = new ScheduleDay(DayOfWeek.THURSDAY, SemesterType.FALL_SEMESTER);

        dao.save(expected);
        Optional<ScheduleDay> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.SPRING_SEMESTER);

        dao.save(expected);
        Optional<ScheduleDay> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Optional<ScheduleDay> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    void shouldVerifyFindAll() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(3L, DayOfWeek.WEDNESDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = dao.findAllById(List.of(1L, 2L));

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
        ScheduleDay entity = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

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
        List<ScheduleDay> entities = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER)
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
    void shouldVerifyFindByDayOfWeek() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = dao.findByDayOfWeek(DayOfWeek.MONDAY);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindBySemesterType() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(3L, DayOfWeek.WEDNESDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = dao.findBySemesterType(SemesterType.FALL_SEMESTER);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByDayOfWeekAndSemesterType() {
        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Optional<ScheduleDay> actual = dao.findByDayOfWeekAndSemesterType(DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

}