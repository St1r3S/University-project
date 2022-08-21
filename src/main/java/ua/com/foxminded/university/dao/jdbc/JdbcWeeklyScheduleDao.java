package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.WeeklyScheduleDao;
import ua.com.foxminded.university.dao.jdbc.mappers.WeeklyScheduleRowMapper;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcWeeklyScheduleDao extends AbstractCrudDao<WeeklySchedule, Long> implements WeeklyScheduleDao {
    public static final String WEEKLY_SCHEDULE_ID = "id";
    public static final String WEEKLY_SCHEDULE_WEEK_NUMBER = "week_number";
    //    public static final String CREATE = "INSERT INTO weekly_schedule(week_number) VALUES (?)";
    public static final String RETRIEVE = "SELECT id, week_number FROM weekly_schedule WHERE id = ?";
    public static final String UPDATE = "UPDATE weekly_schedule SET week_number = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM weekly_schedule WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, week_number FROM weekly_schedule LIMIT 100";
    public static final String COUNT = "SELECT count(*) FROM weekly_schedule";
    public static final String FIND_ALL_BY_IDS = "SELECT id, week_number FROM weekly_schedule WHERE id IN (%s)";
    public static final String DELETE_ALL = "DELETE FROM weekly_schedule";
    public static final String DELETE_ALL_BY_IDS = "DELETE FROM weekly_schedule WHERE id IN (%s)";
    public static final String FIND_BY_WEEK_NUMBER = "SELECT id, week_number FROM weekly_schedule WHERE week_number = ?";

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
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public WeeklySchedule update(WeeklySchedule entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getWeekNumber(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete weekly schedule entity with id" + id, 1);
    }

    @Override
    public void delete(WeeklySchedule entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete weekly schedule entity with id" + entity.getId(), 1);

    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<WeeklySchedule> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(WeeklySchedule::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<WeeklySchedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, new WeeklyScheduleRowMapper());
    }

    @Override
    public List<WeeklySchedule> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new WeeklyScheduleRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public Optional<WeeklySchedule> findByWeekNumber(Integer weekNumber) {
        return jdbcTemplate.query(FIND_BY_WEEK_NUMBER, new WeeklyScheduleRowMapper(), weekNumber).stream().findFirst();
    }
}
