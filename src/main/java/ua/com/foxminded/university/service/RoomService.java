package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.lesson.Room;

public interface RoomService extends CrudService<Room, Long> {
    Room findByRoomNumber(String roomNumber);
}
