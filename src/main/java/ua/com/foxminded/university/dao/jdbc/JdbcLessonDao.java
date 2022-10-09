package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.LessonDao;
import ua.com.foxminded.university.dao.jdbc.mappers.LessonRowMapper;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class JdbcLessonDao extends AbstractCrudDao<Lesson, Long> implements LessonDao {
    public static final String LESSON_ID = "id";
    public static final String LESSON_DISCIPLINE_ID = "discipline_id";
    public static final String LESSON_GROUP_ID = "group_id";
    public static final String LESSON_NUMBER = "lesson_number";
    public static final String LESSON_ROOM_ID = "room_id";
    public static final String LESSON_SCHEDULE_DAY_ID = "schedule_day_id";
    private static final String UPDATE = "UPDATE lessons SET discipline_id = ?, group_id = ?, lesson_number = ?, room_id = ?," +
            "schedule_day_id = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM lessons WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM lessons LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM lessons WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM lessons";
    private static final String DELETE = "DELETE FROM lessons WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM lessons WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM lessons";
    private static final String LESSON_BY_DISCIPLINE_ID = "SELECT * FROM lessons WHERE discipline_id = ?";
    private static final String LESSON_BY_GROUP_ID = "SELECT * FROM lessons WHERE group_id = ?";
    private static final String LESSON_BY_LESSON_NUMBER = "SELECT * FROM lessons WHERE lesson_number = ?";
    private static final String LESSON_BY_ROOM_ID = "SELECT * FROM lessons WHERE room_id = ?";
    private static final String LESSON_BY_SCHEDULE_DAY_ID = "SELECT * FROM lessons WHERE schedule_day_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcLessonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("lessons").usingGeneratedKeyColumns("id");
    }


    @Override
    protected Lesson create(Lesson entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(LESSON_DISCIPLINE_ID, entity.getDisciplineId())
                        .addValue(LESSON_GROUP_ID, entity.getGroupId())
                        .addValue(LESSON_NUMBER, entity.getLessonNumber().getLessonNumber())
                        .addValue(LESSON_ROOM_ID, entity.getRoomId())
                        .addValue(LESSON_SCHEDULE_DAY_ID, entity.getScheduleDayId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected Lesson update(Lesson entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getDisciplineId(), entity.getGroupId(),
                entity.getLessonNumber().getLessonNumber(), entity.getRoomId(), entity.getScheduleDayId(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new LessonRowMapper(), id).stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Lesson> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new LessonRowMapper(), limit);
    }

    @Override
    public List<Lesson> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new LessonRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete lesson entity with id" + id, 1);
    }

    @Override
    public void delete(Lesson entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete lesson entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<Lesson> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(Lesson::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<Lesson> findAllByDisciplineId(Long disciplineId) {
        return jdbcTemplate.query(LESSON_BY_DISCIPLINE_ID, new LessonRowMapper(), disciplineId);
    }

    @Override
    public List<Lesson> findAllByGroupId(Long groupId) {
        return jdbcTemplate.query(LESSON_BY_GROUP_ID, new LessonRowMapper(), groupId);
    }

    @Override
    public List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber) {
        return jdbcTemplate.query(LESSON_BY_LESSON_NUMBER, new LessonRowMapper(), lessonNumber.getLessonNumber());
    }

    @Override
    public List<Lesson> findAllByRoomId(Long roomId) {
        return jdbcTemplate.query(LESSON_BY_ROOM_ID, new LessonRowMapper(), roomId);
    }

    @Override
    public List<Lesson> findAllByScheduleDayId(Long scheduleDayId) {
        return jdbcTemplate.query(LESSON_BY_SCHEDULE_DAY_ID, new LessonRowMapper(), scheduleDayId);
    }
}
