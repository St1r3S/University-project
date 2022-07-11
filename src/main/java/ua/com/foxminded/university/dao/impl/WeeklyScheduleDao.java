package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.WeeklyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.util.List;
import java.util.Optional;

public class WeeklyScheduleDao implements DAO<Long, WeeklySchedule> {
    public static final String WEEKLY_SCHEDULE_ID = "id";
    public static final String WEEKLY_SCHEDULE_WEEK_NUMBER = "week_number";
    public static final String CREATE = "INSERT INTO weekly_schedule(week_number) VALUES (?)";
    public static final String RETRIEVE = "SELECT id, week_number FROM weekly_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE weekly_schedule SET week_number = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM weekly_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, week_number FROM weekly_schedule LIMIT 100";

    private final JdbcTemplate jdbcTemplate;

    public WeeklyScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(WeeklySchedule entity) {
        jdbcTemplate.update(CREATE, entity.getWeekNumber());
    }

    @Override
    public Optional<WeeklySchedule> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new WeeklyScheduleRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(WeeklySchedule entity) {
        jdbcTemplate.update(UPDATE, entity.getWeekNumber(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(WeeklySchedule entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<WeeklySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new WeeklyScheduleRowMapper());
    }
}
