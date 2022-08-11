package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.DailyScheduleDao;
import ua.com.foxminded.university.dao.jdbc.mappers.DailyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SqlWithoutWhere")
@Repository
public class JdbcDailyScheduleDao extends AbstractCrudDao<DailySchedule, Long> implements DailyScheduleDao {
    public static final String DAILY_SCHEDULE_ID = "id";
    public static final String DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL = "date_of_schedule_cell";
    public static final String DAILY_SCHEDULE_DAY_OF_WEEK = "day_of_week";
    public static final String DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    // public static final String CREATE = "INSERT INTO daily_schedule(date_of_schedule_cell, day_of_week, weekly_schedule_id) VALUES (?,?,?)";
    public static final String RETRIEVE = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE daily_schedule SET date_of_schedule_cell = ?, day_of_week = ?, weekly_schedule_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM daily_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule LIMIT 100";
    public static final String COUNT = "SELECT count(*) FROM daily_schedule";
    public static final String FIND_ALL_BY_IDS = "SELECT id, date_of_schedule_cell, day_of_week, weekly_schedule_id FROM daily_schedule WHERE id IN (%s)";
    public static final String DELETE_ALL = "DELETE FROM daily_schedule";
    public static final String DELETE_ALL_BY_IDS = "DELETE FROM daily_schedule WHERE id IN (%s)";

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
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public DailySchedule update(DailySchedule entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getDateOfScheduleCell(), entity.getDayOfWeek().toString(), entity.getWeeklyScheduleId(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete daily schedule entity with id" + id, 1);
    }

    @Override
    public void delete(DailySchedule entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete daily schedule entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<DailySchedule> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(DailySchedule::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<DailySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new DailyScheduleRowMapper());
    }

    @Override
    public List<DailySchedule> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new DailyScheduleRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }
}
