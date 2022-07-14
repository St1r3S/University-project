package ua.com.foxminded.university.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.jdbc.mappers.RoomRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Room;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRoomDao extends AbstractCrudDao<Room, Long> {
    public static final String ROOM_ID = "id";
    public static final String ROOM_NUMBER = "room_number";
    //    public static final String CREATE = "INSERT INTO room(id,room_number) VALUES (DEFAULT,?)";
    public static final String RETRIEVE = "SELECT id, room_number FROM room WHERE id = ?";
    public static final String UPDATE = "UPDATE room SET room_number = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM room WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, room_number FROM room LIMIT 100";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcRoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("room").usingGeneratedKeyColumns("id");

    }

    @Override
    public Room create(Room entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(ROOM_NUMBER, entity.getRoomNumber())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<Room> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new RoomRowMapper(), id).stream().findFirst();
    }

    @Override
    public Room update(Room entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getRoomNumber(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
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
