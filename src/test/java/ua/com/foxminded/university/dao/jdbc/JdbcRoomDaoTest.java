package ua.com.foxminded.university.dao.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.jdbc.mappers.RoomRowMapper;
import ua.com.foxminded.university.model.lesson.Room;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcRoomDaoTest extends BaseDaoTest {

    public static final String SELECT_ROOM_BY_ID = "SELECT * FROM rooms WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    JdbcRoomDao dao;

    @PostConstruct
    void init() {
        dao = new JdbcRoomDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Room expected = new Room("707");
        dao.save(expected);
        Room actual = jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomRowMapper(), 4);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Room expected = new Room(1L, "707");
        dao.save(expected);
        Room actual = jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindById() {
        Room expected = new Room(1L, "404");
        Room actual = dao.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Room> expected = List.of(
                new Room(1L, "404"),
                new Room(2L, "505"),
                new Room(3L, "606")
        );
        List<Room> actual = dao.findAll(100);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAllById() {
        List<Room> expected = List.of(
                new Room(1L, "404"),
                new Room(2L, "505")
        );
        List<Room> actual = dao.findAllById(List.of(1L, 2L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(3L, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Room entity = new Room(1L, "404");
        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAllByEntities() {
        List<Room> entities = List.of(
                new Room(1L, "404"),
                new Room(2L, "505")
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 2)
                .stream()
                .findFirst()
                .isPresent());
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 3)
                .stream()
                .findFirst()
                .isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindByRoomNumber() {
        Room expected = new Room(1L, "404");
        Room actual = dao.findByRoomNumber("404").get();
        assertEquals(expected, actual);
    }

}