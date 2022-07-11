package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.LectureRowMapper;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;
import java.util.Optional;

public class LectureDao implements DAO<Long, Lecture> {
    public static final String LECTURE_ID = "id";
    public static final String LECTURE_DISCIPLINE_ID = "discipline_id";
    public static final String LECTURE_LECTURE_NUMBER_ID = "lecture_number_id";
    public static final String LECTURE_ROOM_ID = "room_id";
    public static final String LECTURE_DAILY_SCHEDULE_ID = "daily_schedule_id";
    public static final String CREATE = "INSERT INTO lecture(discipline_id, lecture_number_id, room_id, " +
            "daily_schedule_id) VALUES (?,?,?,?)";
    public static final String RETRIEVE = "SELECT id, discipline_id, lecture_number_id, room_id, daily_schedule_id " +
            "FROM lecture WHERE id = ?";
    public static final String UPDATE = "UPDATE lecture SET discipline_id = ?, lecture_number_id = ?, room_id = ?, " +
            "daily_schedule_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM lecture WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, discipline_id, lecture_number_id, room_id, daily_schedule_id " +
            "FROM lecture LIMIT 100";
    private static final String LECTURES_BY_STUDENT_ID = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN lecture_student AS ls ON l.id = ls.lecture_id " +
            "INNER JOIN student AS s ON ls.student_id = s.id where s.id = ?";
    private static final String LECTURES_BY_WEEK_NUMBER = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN daily_schedule AS ds ON l.daily_schedule_id = ds.id " +
            "INNER JOIN weekly_schedule AS ws ON ds.weekly_schedule_id = ws.id where ws.week_number = ?";
    private static final String LECTURES_BY_DAY_OF_WEEK_AND_WEEK_NUMBER = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN daily_schedule AS ds ON l.daily_schedule_id = ds.id " +
            "INNER JOIN weekly_schedule AS ws ON ds.weekly_schedule_id = ws.id where ds.day_of_week = ? AND ws.week_number = ?";
    private static final String LECTURES_BY_ROOM_NUMBER = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN room AS r ON l.room_id = r.id " +
            "where r.room_number = ?";

    private final JdbcTemplate jdbcTemplate;

    public LectureDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Lecture entity) {
        jdbcTemplate.update(CREATE, entity.getDisciplineId(), entity.getLectureNumberId(),
                entity.getRoomId(), entity.getDailyScheduleId());
    }

    @Override
    public Optional<Lecture> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LectureRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(Lecture entity) {
        jdbcTemplate.update(UPDATE, entity.getDisciplineId(), entity.getLectureNumberId(),
                entity.getRoomId(), entity.getDailyScheduleId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(Lecture entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<Lecture> findAll() {
        return jdbcTemplate.query(FIND_ALL, new LectureRowMapper());
    }

    public List<Lecture> getLecturesByStudentId(Long studentId) {
        return jdbcTemplate.query(LECTURES_BY_STUDENT_ID, new LectureRowMapper(), studentId);
    }

    public List<Lecture> getLecturesByWeekNumber(Integer weekNumber) {
        return jdbcTemplate.query(LECTURES_BY_WEEK_NUMBER, new LectureRowMapper(), weekNumber);
    }

    public List<Lecture> getLecturesByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber) {
        return jdbcTemplate.query(LECTURES_BY_DAY_OF_WEEK_AND_WEEK_NUMBER, new LectureRowMapper(), dayOfWeek, weekNumber);
    }

    public List<Lecture> getLecturesByRoomNumber(String roomNumber) {
        return jdbcTemplate.query(LECTURES_BY_ROOM_NUMBER, new LectureRowMapper(), roomNumber);
    }
}
