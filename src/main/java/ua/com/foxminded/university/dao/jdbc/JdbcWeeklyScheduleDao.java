package ua.com.foxminded.university.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.jdbc.mappers.WeeklyScheduleRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcWeeklyScheduleDao extends AbstractCrudDao<WeeklySchedule, Long> {
    public static final String WEEKLY_SCHEDULE_ID = "id";
    public static final String WEEKLY_SCHEDULE_WEEK_NUMBER = "week_number";
    //    public static final String CREATE = "INSERT INTO weekly_schedule(week_number) VALUES (?)";
    public static final String RETRIEVE = "SELECT id, week_number FROM weekly_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE weekly_schedule SET week_number = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM weekly_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, week_number FROM weekly_schedule LIMIT 100";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcWeeklyScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("weekly_schedule").usingGeneratedKeyColumns("id");
    }

    @Override
    public WeeklySchedule create(WeeklySchedule entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(WEEKLY_SCHEDULE_WEEK_NUMBER, entity.getWeekNumber())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<WeeklySchedule> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new WeeklyScheduleRowMapper(), id).stream().findFirst();
    }

    @Override
    public WeeklySchedule update(WeeklySchedule entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getWeekNumber(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new NotFoundException("Unable to delete weekly schedule entity with id" + id);
    }

    @Override
    public List<WeeklySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new WeeklyScheduleRowMapper());
    }
}
