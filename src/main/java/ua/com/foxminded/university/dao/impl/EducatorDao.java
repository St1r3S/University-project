package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.EducatorRowMapper;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;
import java.util.Optional;

public class EducatorDao implements DAO<Long, Educator> {
    public static final String EDUCATOR_ID = "id";
    public static final String EDUCATOR_FIRST_NAME = "first_name";
    public static final String EDUCATOR_LAST_NAME = "last_name";
    public static final String EDUCATOR_BIRTHDAY = "birthday";
    public static final String EDUCATOR_EMAIL = "email";
    public static final String EDUCATOR_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String EDUCATOR_ROLE = "user_role";
    public static final String EDUCATOR_POSITION = "educator_position";
    public static final String CREATE = "INSERT INTO educator(first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, educator_position) VALUES (?,?,?,?,?,?,?)";
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

    private final JdbcTemplate jdbcTemplate;

    public EducatorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Educator entity) {
        jdbcTemplate.update(CREATE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole(), entity.getPosition());
    }

    @Override
    public Optional<Educator> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new EducatorRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public void update(Educator entity) {
        jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole(), entity.getPosition(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(Educator entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<Educator> findAll() {
        return jdbcTemplate.query(FIND_ALL, new EducatorRowMapper());
    }

    public List<Educator> getEducatorsBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(EDUCATORS_BY_SPECIALISM_ID, new EducatorRowMapper(), specialismId);
    }
}
