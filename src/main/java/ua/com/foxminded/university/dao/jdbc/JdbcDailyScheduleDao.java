package ua.com.foxminded.university.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.jdbc.mappers.DailyScheduleRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDailyScheduleDao extends AbstractCrudDao<DailySchedule, Long> {
    public static final String DAILY_SCHEDULE_ID = "id";
    public static final String DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL = "date_of_schedule_cell";
    public static final String DAILY_SCHEDULE_DAY_OF_WEEK = "day_of_week";
    public static final String DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    //    public static final String CREATE = "INSERT INTO daily_schedule(date_of_schedule_cell, day_of_week, weekly_schedule_id) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE daily_schedule SET date_of_schedule_cell = ?, day_of_week = ?, weekly_schedule_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM daily_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule LIMIT 100";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcDailyScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("daily_schedule").usingGeneratedKeyColumns("id");
    }

    @Override
    public DailySchedule create(DailySchedule entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL, entity.getDateOfScheduleCell())
                        .addValue(DAILY_SCHEDULE_DAY_OF_WEEK, entity.getDayOfWeek().toString())
                        .addValue(DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID, entity.getWeeklyScheduleId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<DailySchedule> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new DailyScheduleRowMapper(), id).stream().findFirst();
    }

    @Override
    public DailySchedule update(DailySchedule entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getDateOfScheduleCell(), entity.getDayOfWeek().toString(), entity.getWeeklyScheduleId(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new NotFoundException("Unable to delete daily schedule entity with id" + id);
    }

    @Override
    public List<DailySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new DailyScheduleRowMapper());
    }
}
