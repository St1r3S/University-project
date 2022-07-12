package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.DailyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import java.util.List;
import java.util.Optional;

@Repository
public class DailyScheduleDao implements DAO<Long, DailySchedule> {
    public static final String DAILY_SCHEDULE_ID = "id";
    public static final String DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL = "date_of_schedule_cell";
    public static final String DAILY_SCHEDULE_DAY_OF_WEEK = "day_of_week";
    public static final String DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String CREATE = "INSERT INTO daily_schedule(date_of_schedule_cell, day_of_week, weekly_schedule_id) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE daily_schedule SET date_of_schedule_cell = ?, day_of_week = ?, weekly_schedule_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM daily_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule LIMIT 100";

    private final JdbcTemplate jdbcTemplate;

    public DailyScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(DailySchedule entity) {
        jdbcTemplate.update(CREATE, entity.getDateOfScheduleCell(), entity.getDayOfWeek().toString(), entity.getWeeklyScheduleId());
    }

    @Override
    public Optional<DailySchedule> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new DailyScheduleRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(DailySchedule entity) {
        jdbcTemplate.update(UPDATE, entity.getDateOfScheduleCell(), entity.getDayOfWeek().toString(), entity.getWeeklyScheduleId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(DailySchedule entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<DailySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new DailyScheduleRowMapper());
    }
}
