//package ua.com.foxminded.university.dao.hibernate;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.jdbc.Sql;
//import ua.com.foxminded.university.dao.hibernate.mappers.GroupRowMapper;
//import ua.com.foxminded.university.model.user.Group;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@JdbcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class HibernateGroupDaoTest {
//    public static final String SELECT_GROUP_BY_ID = "SELECT * FROM groupss WHERE id = ?";
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    HibernateGroupDao dao;
//
//    @PostConstruct
//    void init() {
//        dao = new HibernateGroupDao(jdbcTemplate);
//    }
//
//    @Test
//    void shouldVerifyDaoInjected() {
//        assertNotNull(dao);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyCreate() {
//        Group expected = new Group("AI-196", 1L, 1L);
//        dao.save(expected);
//        Group actual = jdbcTemplate.queryForObject(SELECT_GROUP_BY_ID, new GroupRowMapper(), 4);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyUpdate() {
//        Group expected = new Group(1L, "AI-196", 1L, 1L);
//        dao.save(expected);
//        Group actual = jdbcTemplate.queryForObject(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindById() {
//        Group expected = new Group(1L, "AI-193", 1L, 1L);
//        Group actual = dao.findById(1L).get();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyIsExistById() {
//        assertTrue(dao.existsById(1L));
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAll() {
//        List<Group> expected = List.of(
//                new Group(1L, "AI-193", 1L, 1L),
//                new Group(2L, "AI-194", 1L, 1L),
//                new Group(3L, "AI-195", 1L, 1L)
//        );
//        List<Group> actual = dao.findAll(100);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAllById() {
//        List<Group> expected = List.of(
//                new Group(1L, "AI-193", 1L, 1L),
//                new Group(2L, "AI-194", 1L, 1L)
//        );
//        List<Group> actual = dao.findAllById(List.of(1L, 2L));
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyCount() {
//        long actual = dao.count();
//        assertEquals(3L, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteById() {
//        assertDoesNotThrow(() -> dao.deleteById(1L));
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteByEntity() {
//        Group entity = new Group(1L, "AI-193", 1L, 1L);
//        assertDoesNotThrow(() -> dao.delete(entity));
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllById() {
//        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllByEntities() {
//        List<Group> entities = List.of(
//                new Group(1L, "AI-193", 1L, 1L),
//                new Group(2L, "AI-194", 1L, 1L)
//        );
//        assertDoesNotThrow(() -> dao.deleteAll(entities));
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAll() {
//        assertDoesNotThrow(() -> dao.deleteAll());
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_GROUP_BY_ID, new GroupRowMapper(), 3)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindByGroupName() {
//        Group expected = new Group(1L, "AI-193", 1L, 1L);
//        Group actual = dao.findByGroupName("AI-193").get();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAllBySpecialismId() {
//        List<Group> expected = List.of(
//                new Group(1L, "AI-193", 1L, 1L),
//                new Group(2L, "AI-194", 1L, 1L),
//                new Group(3L, "AI-195", 1L, 1L)
//        );
//        List<Group> actual = dao.findAllBySpecialismId(1L);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAllByAcademicYearId() {
//        List<Group> expected = List.of(
//                new Group(1L, "AI-193", 1L, 1L),
//                new Group(2L, "AI-194", 1L, 1L),
//                new Group(3L, "AI-195", 1L, 1L)
//        );
//        List<Group> actual = dao.findAllBySpecialismId(1L);
//        assertEquals(expected, actual);
//    }
//
//}
