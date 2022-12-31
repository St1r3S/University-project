package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.UserDao;
import ua.com.foxminded.university.dao.jdbc.mappers.UserRowMapper;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao extends AbstractCrudDao<User, Long> implements UserDao {

    public static final Integer USER_TYPE_CODE = UserType.USER.getTypeCode();
    public static final String USER_ID = "id";
    public static final String USER_TYPE = "user_type";
    public static final String USER_LOGIN = "user_name";
    public static final String USER_PASSWORD = "password_hash";
    public static final String USER_ROLE = "user_role";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_BIRTHDAY = "birthday";
    public static final String USER_EMAIL = "email";
    private static final String UPDATE = "UPDATE users SET user_name = ?, password_hash = ?, user_role = ?, first_name = ?," +
            "last_name = ?, birthday = ?, email = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM users  LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM users WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM users";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM users WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM users";
    private static final String USER_BY_LOGIN = "SELECT * FROM users WHERE user_name = ?";
    private static final String USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String USER_BY_USER_ROLE = "SELECT * FROM users WHERE user_role = ?";
    private static final String USER_BY_BIRTHDAY = "SELECT * FROM users WHERE birthday = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    protected User create(User entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(USER_TYPE, USER_TYPE_CODE)
                        .addValue(USER_LOGIN, entity.getUserName())
                        .addValue(USER_PASSWORD, entity.getPasswordHash())
                        .addValue(USER_ROLE, entity.getUserRole().getValue())
                        .addValue(USER_FIRST_NAME, entity.getFirstName())
                        .addValue(USER_LAST_NAME, entity.getLastName())
                        .addValue(USER_BIRTHDAY, entity.getBirthday())
                        .addValue(USER_EMAIL, entity.getEmail())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected User update(User entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getUserName(), entity.getPasswordHash(), entity.getUserRole().getValue(),
                entity.getFirstName(), entity.getLastName(), entity.getBirthday(), entity.getEmail(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new UserRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<User> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new UserRowMapper(), limit);
    }

    @Override
    public List<User> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new UserRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete user entity with id" + id, 1);
    }

    @Override
    public void delete(User entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete user entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<User> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(User::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<User> findByLogin(String userName) {
        return jdbcTemplate.query(USER_BY_LOGIN, new UserRowMapper(), userName)
                .stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(USER_BY_EMAIL, new UserRowMapper(), email)
                .stream().findFirst();
    }

    @Override
    public List<User> findAllByUserRole(UserRole userRole) {
        return jdbcTemplate.query(USER_BY_USER_ROLE, new UserRowMapper(), userRole.getValue());
    }

    @Override
    public List<User> findAllByBirthday(LocalDate birthday) {
        return jdbcTemplate.query(USER_BY_BIRTHDAY, new UserRowMapper(), birthday);
    }
}
