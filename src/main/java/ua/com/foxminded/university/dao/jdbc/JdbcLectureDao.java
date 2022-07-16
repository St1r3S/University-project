package ua.com.foxminded.university.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.dao.jdbc.mappers.LectureRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLectureDao extends AbstractCrudDao<Lecture, Long> implements LectureDao {
    public static final String LECTURE_ID = "id";
    public static final String LECTURE_DISCIPLINE_ID = "discipline_id";
    public static final String LECTURE_LECTURE_NUMBER_ID = "lecture_number_id";
    public static final String LECTURE_ROOM_ID = "room_id";
    public static final String LECTURE_DAILY_SCHEDULE_ID = "daily_schedule_id";
    //    public static final String CREATE = "INSERT INTO lecture(discipline_id, lecture_number_id, room_id, " +
//            "daily_schedule_id) VALUES (?,?,?,?)";
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
    private static final String LECTURES_BY_DATE = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN daily_schedule AS ds ON l.daily_schedule_id = ds.id " +
            "where ds.date_of_schedule_cell = ?";
    private static final String LECTURES_BY_ROOM_NUMBER = "SELECT l.id, l.discipline_id, l.lecture_number_id, " +
            "l.room_id, l.daily_schedule_id FROM lecture AS l " +
            "INNER JOIN room AS r ON l.room_id = r.id " +
            "where r.room_number = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcLectureDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("lecture").usingGeneratedKeyColumns("id");
    }

    @Override
    public Lecture create(Lecture entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(LECTURE_DISCIPLINE_ID, entity.getDisciplineId())
                        .addValue(LECTURE_LECTURE_NUMBER_ID, entity.getLectureNumberId())
                        .addValue(LECTURE_ROOM_ID, entity.getRoomId())
                        .addValue(LECTURE_DAILY_SCHEDULE_ID, entity.getDailyScheduleId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LectureRowMapper(), id).stream().findFirst();
    }

    @Override
    public Lecture update(Lecture entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getDisciplineId(), entity.getLectureNumberId(),
                entity.getRoomId(), entity.getDailyScheduleId(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new NotFoundException("Unable to delete lecture entity with id" + id);
    }

    @Override
    public List<Lecture> findAll() {
        return jdbcTemplate.query(FIND_ALL, new LectureRowMapper());
    }

    @Override
    public List<Lecture> findAllByStudentId(Long studentId) {
        return jdbcTemplate.query(LECTURES_BY_STUDENT_ID, new LectureRowMapper(), studentId);
    }

    @Override
    public List<Lecture> findAllByWeekNumber(Integer weekNumber) {
        return jdbcTemplate.query(LECTURES_BY_WEEK_NUMBER, new LectureRowMapper(), weekNumber);
    }

    @Override
    public List<Lecture> findAllByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber) {
        return jdbcTemplate.query(LECTURES_BY_DAY_OF_WEEK_AND_WEEK_NUMBER, new LectureRowMapper(), dayOfWeek.toString(), weekNumber);
    }

    @Override
    public List<Lecture> findAllByDate(LocalDate date) {
        return jdbcTemplate.query(LECTURES_BY_DATE, new LectureRowMapper(), date);
    }

    @Override
    public List<Lecture> findAllByRoomNumber(String roomNumber) {
        return jdbcTemplate.query(LECTURES_BY_ROOM_NUMBER, new LectureRowMapper(), roomNumber);
    }
}
