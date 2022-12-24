package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoomServiceImplTest {
    @Autowired
    RoomServiceImpl roomService;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> roomService.findById(1L));
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllFreeRooms() {
        List<Room> expected = List.of(
                new Room(1L, "404"),
                new Room(2L, "505"),
                new Room(3L, "606"),
                new Room(4L, "707")
        );
        List<Room> actual = roomService.findAllFreeRooms(LessonNumber.THIRD_LESSON,
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER));
        assertEquals(expected, actual);

        expected = List.of(
                new Room(2L, "505"),
                new Room(4L, "707")
        );
        actual = roomService.findAllFreeRooms(LessonNumber.FIRST_LESSON,
                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER));
        assertEquals(expected, actual);
    }
}