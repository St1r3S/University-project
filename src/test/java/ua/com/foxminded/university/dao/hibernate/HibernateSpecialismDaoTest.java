//package ua.com.foxminded.university.dao.hibernate;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.jdbc.Sql;
//import ua.com.foxminded.university.BaseDaoTest;
//import ua.com.foxminded.university.dao.hibernate.mappers.SpecialismRowMapper;
//import ua.com.foxminded.university.model.lesson.Specialism;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@JdbcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class HibernateSpecialismDaoTest extends BaseDaoTest {
//    public static final String SELECT_SPECIALISM_BY_ID = "SELECT * FROM specialisms WHERE id = ?";
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    HibernateSpecialismDao dao;
//
//    @PostConstruct
//    void init() {
//        dao = new HibernateSpecialismDao(jdbcTemplate);
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
//        Specialism expected = new Specialism("112");
//        dao.save(expected);
//        Specialism actual = jdbcTemplate.queryForObject(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 4);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyUpdate() {
//        Specialism expected = new Specialism(1L, "112");
//        dao.save(expected);
//        Specialism actual = jdbcTemplate.queryForObject(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindById() {
//        Specialism expected = new Specialism(1L, "122");
//        Specialism actual = dao.findById(1L).get();
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
//        List<Specialism> expected = List.of(
//                new Specialism(1L, "122"),
//                new Specialism(2L, "125"),
//                new Specialism(3L, "127")
//        );
//        List<Specialism> actual = dao.findAll(100);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAllById() {
//        List<Specialism> expected = List.of(
//                new Specialism(1L, "122"),
//                new Specialism(2L, "125")
//        );
//        List<Specialism> actual = dao.findAllById(List.of(1L, 2L));
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
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteByEntity() {
//        Specialism entity = new Specialism(1L, "122");
//        assertDoesNotThrow(() -> dao.delete(entity));
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllById() {
//        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllByEntities() {
//        List<Specialism> entities = List.of(
//                new Specialism(1L, "122"),
//                new Specialism(2L, "125")
//        );
//        assertDoesNotThrow(() -> dao.deleteAll(entities));
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAll() {
//        assertDoesNotThrow(() -> dao.deleteAll());
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_SPECIALISM_BY_ID, new SpecialismRowMapper(), 3)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindBySpecialismName() {
//        Specialism expected = new Specialism(1L, "122");
//        Specialism actual = dao.findBySpecialismName("122").get();
//        assertEquals(expected, actual);
//    }
//
//}