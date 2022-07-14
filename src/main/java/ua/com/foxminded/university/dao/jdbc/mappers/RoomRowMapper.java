package ua.com.foxminded.university.dao.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.jdbc.JdbcRoomDao.ROOM_ID;
import static ua.com.foxminded.university.dao.jdbc.JdbcRoomDao.ROOM_NUMBER;

public class RoomRowMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Room(
                rs.getLong(ROOM_ID),
                rs.getString(ROOM_NUMBER)
        );
    }
}
