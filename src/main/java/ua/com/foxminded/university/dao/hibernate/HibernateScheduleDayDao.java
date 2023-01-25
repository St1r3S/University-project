package ua.com.foxminded.university.dao.hibernate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.ScheduleDayDao;
import ua.com.foxminded.university.dao.hibernate.mappers.ScheduleDayRowMapper;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateScheduleDayDao extends AbstractCrudDao<ScheduleDay, Long> implements ScheduleDayDao {

    public static final String SCHEDULE_DAY_ID = "id";
    public static final String SCHEDULE_DAY_DAY_OF_WEEK = "day_of_week";
    public static final String SCHEDULE_DAY_SEMESTER_TYPE = "semester_type";
    private static final String UPDATE = "UPDATE schedule_days SET day_of_week = ?, semester_type = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM schedule_days WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM schedule_days LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM schedule_days WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM schedule_days";
    private static final String DELETE = "DELETE FROM schedule_days WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM schedule_days WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM schedule_days";
    private static final String SCHEDULE_DAY_BY_DAY_OF_WEEK = "SELECT * FROM schedule_days WHERE day_of_week = ?";
    private static final String SCHEDULE_DAY_BY_SEMESTER_TYPE = "SELECT * FROM schedule_days WHERE semester_type = ?";
    private static final String SCHEDULE_DAY_BY_DAY_OF_WEEK_AND_SEMESTER_TYPE = "SELECT * FROM schedule_days WHERE day_of_week = ? AND semester_type = ?";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected ScheduleDay create(ScheduleDay entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected ScheduleDay update(ScheduleDay entity) {
        try {
            return entityManager.merge(entity);
        } catch (IllegalArgumentException ex) {
            throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
        }
    }

    @Override
    public Optional<ScheduleDay> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new ScheduleDayRowMapper(), id).stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<ScheduleDay> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new ScheduleDayRowMapper(), limit);
    }

    @Override
    public List<ScheduleDay> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new ScheduleDayRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete schedule day entity with id" + id, 1);
    }

    @Override
    public void delete(ScheduleDay entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete schedule day entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<ScheduleDay> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(ScheduleDay::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek) {
        return jdbcTemplate.query(SCHEDULE_DAY_BY_DAY_OF_WEEK, new ScheduleDayRowMapper(), dayOfWeek.getValue());
    }

    @Override
    public List<ScheduleDay> findBySemesterType(SemesterType semesterType) {
        return jdbcTemplate.query(SCHEDULE_DAY_BY_SEMESTER_TYPE, new ScheduleDayRowMapper(), semesterType.getValue());
    }

    @Override
    public Optional<ScheduleDay> findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType) {
        return jdbcTemplate.query(SCHEDULE_DAY_BY_DAY_OF_WEEK_AND_SEMESTER_TYPE, new ScheduleDayRowMapper(), dayOfWeek.getValue(), semesterType.getValue())
                .stream()
                .findFirst();
    }
}
