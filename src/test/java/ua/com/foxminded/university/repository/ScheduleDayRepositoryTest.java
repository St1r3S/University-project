package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ScheduleDayRepositoryTest {
    @Autowired
    ScheduleDayRepository repository;


    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByDayOfWeek() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = repository.findByDayOfWeek(DayOfWeek.MONDAY);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindBySemesterType() {
        List<ScheduleDay> expected = List.of(
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER),
                new ScheduleDay(3L, DayOfWeek.WEDNESDAY, SemesterType.FALL_SEMESTER)
        );

        List<ScheduleDay> actual = repository.findBySemesterType(SemesterType.FALL_SEMESTER);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindByDayOfWeekAndSemesterType() {
        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Optional<ScheduleDay> actual = repository.findByDayOfWeekAndSemesterType(DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

}