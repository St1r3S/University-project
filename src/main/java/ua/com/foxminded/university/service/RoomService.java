package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.ScheduleDay;

import java.util.List;

public interface RoomService extends CrudService<Room, Long> {
    Room findByRoomNumber(String roomNumber);

    List<Room> findAllFreeRooms(LessonNumber lessonNumber, ScheduleDay scheduleDay);
}
