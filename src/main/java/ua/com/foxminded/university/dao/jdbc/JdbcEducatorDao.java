package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.dao.jdbc.mappers.EducatorRowMapper;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEducatorDao extends AbstractCrudDao<Educator, Long> implements EducatorDao {
    public static final String EDUCATOR_ID = "id";
    public static final String EDUCATOR_FIRST_NAME = "first_name";
    public static final String EDUCATOR_LAST_NAME = "last_name";
    public static final String EDUCATOR_BIRTHDAY = "birthday";
    public static final String EDUCATOR_EMAIL = "email";
    public static final String EDUCATOR_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String EDUCATOR_ROLE = "user_role";
    public static final String EDUCATOR_POSITION = "educator_position";
    //    public static final String CREATE = "INSERT INTO educator(first_name, last_name, birthday, email, " +
//            "weekly_schedule_id, user_role, educator_position) VALUES (?,?,?,?,?,?,?)";
    public static final String RETRIEVE = "SELECT id, first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, educator_position " +
            "FROM educator WHERE id = ?";
    public static final String UPDATE = "UPDATE educator SET first_name = ?, last_name = ?, birthday = ?, email = ?, " +
            "weekly_schedule_id = ?, user_role = ?, educator_position = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM educator WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, first_name, last_name, birthday, email, weekly_schedule_id, user_role, " +
            "educator_position FROM educator LIMIT 100";
    private static final String EDUCATORS_BY_SPECIALISM_ID = "SELECT e.id, e.first_name, e.last_name, e.birthday, e.email, " +
            "e.weekly_schedule_id, e.user_role, e.educator_position FROM educator AS e " +
            "INNER JOIN educator_specialism AS es ON e.id = es.educator_id " +
            "INNER JOIN specialism AS s ON es.specialism_id = s.id where s.id = ?";
    private static final String INSERT_EDUCATOR_SPECIALISM = "INSERT INTO educator_specialism(educator_id, specialism_id) VALUES (?, ?)";
    private static final String DELETE_EDUCATOR_SPECIALISM = "DELETE FROM educator_specialism WHERE educator_id = ? AND specialism_id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public JdbcEducatorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("educator").usingGeneratedKeyColumns("id");
    }

    @Override
    public Educator create(Educator entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(EDUCATOR_FIRST_NAME, entity.getFirstName())
                        .addValue(EDUCATOR_LAST_NAME, entity.getLastName())
                        .addValue(EDUCATOR_BIRTHDAY, entity.getBirthday())
                        .addValue(EDUCATOR_EMAIL, entity.getEmail())
                        .addValue(EDUCATOR_WEEKLY_SCHEDULE_ID, entity.getWeeklyScheduleId())
                        .addValue(EDUCATOR_ROLE, entity.getUserRole().toString())
                        .addValue(EDUCATOR_POSITION, entity.getPosition())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<Educator> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new EducatorRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Educator update(Educator entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole().toString(), entity.getPosition(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);

    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete educator entity with id" + id, 1);
    }

    @Override
    public List<Educator> findAll() {
        return jdbcTemplate.query(FIND_ALL, new EducatorRowMapper());
    }

    @Override
    public List<Educator> findAllBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(EDUCATORS_BY_SPECIALISM_ID, new EducatorRowMapper(), specialismId);
    }

    @Override
    public void enrollEducatorSpecialism(Long educatorId, Long specialismId) {
        if (1 != jdbcTemplate.update(INSERT_EDUCATOR_SPECIALISM, educatorId, specialismId))
            throw new EmptyResultDataAccessException("Unable to enroll educator entity with id " + educatorId +
                    "with specialism entity with id " + specialismId, 1);
    }

    @Override
    public void expelEducatorSpecialism(Long educatorId, Long specialismId) {
        if (1 != jdbcTemplate.update(DELETE_EDUCATOR_SPECIALISM, educatorId, specialismId))
            throw new EmptyResultDataAccessException("Unable to expel educator entity with id " + educatorId +
                    "with specialism entity with id " + specialismId, 1);
    }
}
