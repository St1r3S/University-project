package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.lesson.Room;

import java.util.Optional;

public interface RoomDao extends CrudDao<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);

}
