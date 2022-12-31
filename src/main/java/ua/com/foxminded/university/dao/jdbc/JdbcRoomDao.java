package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.RoomDao;
import ua.com.foxminded.university.dao.jdbc.mappers.RoomRowMapper;
import ua.com.foxminded.university.model.lesson.Room;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRoomDao extends AbstractCrudDao<Room, Long> implements RoomDao {
    public static final String ROOM_ID = "id";
    public static final String ROOM_NUMBER = "room_number";
    private static final String UPDATE = "UPDATE rooms SET room_number = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM rooms WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM rooms LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM rooms WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM rooms";
    private static final String DELETE = "DELETE FROM rooms WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM rooms WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM rooms";
    private static final String ROOM_BY_ROOM_NUMBER = "SELECT * FROM rooms WHERE room_number = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcRoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("rooms").usingGeneratedKeyColumns("id");

    }

    @Override
    protected Room create(Room entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(ROOM_NUMBER, entity.getRoomNumber())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected Room update(Room entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getRoomNumber(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<Room> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new RoomRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Room> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new RoomRowMapper(), limit);
    }

    @Override
    public List<Room> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new RoomRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete room entity with id" + id, 1);
    }

    @Override
    public void delete(Room entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete room entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<Room> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(Room::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber) {
        return jdbcTemplate.query(ROOM_BY_ROOM_NUMBER, new RoomRowMapper(), roomNumber)
                .stream()
                .findFirst();
    }
}
