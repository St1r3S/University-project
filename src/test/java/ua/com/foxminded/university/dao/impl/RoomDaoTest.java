package ua.com.foxminded.university.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.BaseDaoTest;
import ua.com.foxminded.university.dao.mappers.RoomRowMapper;
import ua.com.foxminded.university.model.lecture.Room;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomDaoTest extends BaseDaoTest {

    public static final String SELECT_ROOM_BY_ID = "SELECT id, room_number FROM room WHERE id = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    RoomDao dao;

    @PostConstruct
    void init() {
        dao = new RoomDao(jdbcTemplate);
    }

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyCreate() {
        Room expected = new Room(2L, "55");
        dao.create(expected);
        Room actual = jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomRowMapper(), 2);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyRetrieve() {
        Room expected = new Room(1L, "404");
        Room actual = dao.retrieve(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyUpdate() {
        Room expected = new Room(1L, "505");
        dao.update(expected);
        Room actual = jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteById() {
        dao.delete(1L);
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyDeleteByEntity() {
        Room expected = new Room(1L, "404");
        dao.delete(expected);
        assertFalse(jdbcTemplate.query(SELECT_ROOM_BY_ID, new RoomRowMapper(), 1).stream().findFirst().isPresent());
    }

    @Test
    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
    void shouldVerifyFindAll() {
        List<Room> expected = List.of(new Room(1L, "404"));
        List<Room> actual = dao.findAll();
        assertEquals(expected, actual);
    }
}