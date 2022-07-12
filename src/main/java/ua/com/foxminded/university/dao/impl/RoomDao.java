package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.RoomRowMapper;
import ua.com.foxminded.university.model.lecture.Room;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomDao implements DAO<Long, Room> {
    public static final String ROOM_ID = "id";
    public static final String ROOM_NUMBER = "room_number";
    public static final String CREATE = "INSERT INTO room(id,room_number) VALUES (DEFAULT,?)";
    public static final String RETRIEVE = "SELECT id, room_number FROM room WHERE id = ?";
    public static final String UPDATE = "UPDATE room SET room_number = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM room WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, room_number FROM room LIMIT 100";

    private final JdbcTemplate jdbcTemplate;

    public RoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Room entity) {
        jdbcTemplate.update(CREATE, entity.getRoomNumber());
    }

    @Override
    public Optional<Room> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new RoomRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(Room entity) {
        jdbcTemplate.update(UPDATE, entity.getRoomNumber(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(Room entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query(FIND_ALL, new RoomRowMapper());
    }
}
