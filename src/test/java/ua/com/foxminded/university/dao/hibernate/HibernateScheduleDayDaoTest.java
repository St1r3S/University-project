//package ua.com.foxminded.university.dao.hibernate;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.jdbc.Sql;
//import ua.com.foxminded.university.BaseDaoTest;
//import ua.com.foxminded.university.dao.hibernate.mappers.ScheduleDayRowMapper;
//import ua.com.foxminded.university.model.schedule.DayOfWeek;
//import ua.com.foxminded.university.model.schedule.ScheduleDay;
//import ua.com.foxminded.university.model.schedule.SemesterType;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@JdbcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class HibernateScheduleDayDaoTest extends BaseDaoTest {
//    public static final String SELECT_DAILY_SCHEDULE_BY_ID = "SELECT * FROM schedule_days WHERE id = ?";
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    HibernateScheduleDayDao dao;
//
//
//    @PostConstruct
//    void init() {
//        dao = new HibernateScheduleDayDao(jdbcTemplate);
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
//        ScheduleDay expected = new ScheduleDay(DayOfWeek.THURSDAY, SemesterType.FALL_SEMESTER);
//        dao.save(expected);
//        ScheduleDay actual = jdbcTemplate.queryForObject(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 4);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyUpdate() {
//        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.SPRING_SEMESTER);
//        dao.save(expected);
//        ScheduleDay actual = jdbcTemplate.queryForObject(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindById() {
//        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);
//        ScheduleDay actual = dao.findById(1L).get();
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
//        List<ScheduleDay> expected = List.of(
//                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(3L, DayOfWeek.WEDNESDAY, SemesterType.FALL_SEMESTER)
//        );
//        List<ScheduleDay> actual = dao.findAll(100);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindAllById() {
//        List<ScheduleDay> expected = List.of(
//                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER)
//        );
//        List<ScheduleDay> actual = dao.findAllById(List.of(1L, 2L));
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
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteByEntity() {
//        ScheduleDay entity = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);
//        assertDoesNotThrow(() -> dao.delete(entity));
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllById() {
//        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAllByEntities() {
//        List<ScheduleDay> entities = List.of(
//                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER)
//        );
//        assertDoesNotThrow(() -> dao.deleteAll(entities));
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyDeleteAll() {
//        assertDoesNotThrow(() -> dao.deleteAll());
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 1)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 2)
//                .stream()
//                .findFirst()
//                .isPresent());
//        assertFalse(jdbcTemplate.query(SELECT_DAILY_SCHEDULE_BY_ID, new ScheduleDayRowMapper(), 3)
//                .stream()
//                .findFirst()
//                .isPresent());
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindByDayOfWeek() {
//        List<ScheduleDay> expected = List.of(
//                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER)
//        );
//        List<ScheduleDay> actual = dao.findByDayOfWeek(DayOfWeek.MONDAY);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindBySemesterType() {
//        List<ScheduleDay> expected = List.of(
//                new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER),
//                new ScheduleDay(3L, DayOfWeek.WEDNESDAY, SemesterType.FALL_SEMESTER)
//        );
//        List<ScheduleDay> actual = dao.findBySemesterType(SemesterType.FALL_SEMESTER);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"})
//    void shouldVerifyFindByDayOfWeekAndSemesterType() {
//        ScheduleDay expected = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);
//        ScheduleDay actual = dao.findByDayOfWeekAndSemesterType(DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER).get();
//        assertEquals(expected, actual);
//    }
//
//}