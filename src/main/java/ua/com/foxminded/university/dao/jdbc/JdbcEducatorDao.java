package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.dao.jdbc.mappers.EducatorRowMapper;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEducatorDao extends AbstractCrudDao<Educator, Long> implements EducatorDao {
    public static final Integer EDUCATOR_TYPE_CODE = UserType.EDUCATOR.getTypeCode();
    public static final String EDUCATOR_ID = "id";
    public static final String USER_TYPE = "user_type";
    public static final String EDUCATOR_LOGIN = "user_name";
    public static final String EDUCATOR_PASSWORD = "password_hash";
    public static final String EDUCATOR_ROLE = "user_role";
    public static final String EDUCATOR_FIRST_NAME = "first_name";
    public static final String EDUCATOR_LAST_NAME = "last_name";
    public static final String EDUCATOR_BIRTHDAY = "birthday";
    public static final String EDUCATOR_EMAIL = "email";
    public static final String EDUCATOR_ACADEMIC_RANK = "academic_rank";
    private static final String UPDATE = "UPDATE users SET user_name = ?, password_hash = ?, user_role = ?, first_name = ?," +
            "last_name = ?, birthday = ?, email = ?, academic_rank = ? WHERE id = ? AND user_type = ?";
    private static final String RETRIEVE = "SELECT * FROM users WHERE id = ? AND user_type = ?";
    private static final String FIND_ALL = "SELECT * FROM users WHERE user_type = ? LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM users WHERE id IN (%s) AND user_type = ?";
    private static final String COUNT = "SELECT count(*) FROM users WHERE user_type = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ? AND user_type = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM users WHERE id IN (%s) AND user_type = ?";
    private static final String DELETE_ALL = "DELETE FROM users WHERE user_type = ?";
    private static final String EDUCATOR_BY_LOGIN = "SELECT * FROM users WHERE user_name = ? AND user_type = ?";
    private static final String EDUCATOR_BY_EMAIL = "SELECT * FROM users WHERE email = ? AND user_type = ?";
    private static final String EDUCATOR_BY_USER_ROLE = "SELECT * FROM users WHERE user_role = ? AND user_type = ?";
    private static final String EDUCATOR_BY_BIRTHDAY = "SELECT * FROM users WHERE birthday = ? AND user_type = ?";
    private static final String EDUCATOR_BY_ACADEMIC_RANK = "SELECT * FROM users WHERE academic_rank = ? AND user_type = ?";

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public JdbcEducatorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }


    @Override
    protected Educator create(Educator entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(USER_TYPE, EDUCATOR_TYPE_CODE)
                        .addValue(EDUCATOR_LOGIN, entity.getUserName())
                        .addValue(EDUCATOR_PASSWORD, entity.getPasswordHash())
                        .addValue(EDUCATOR_ROLE, entity.getUserRole().getValue())
                        .addValue(EDUCATOR_FIRST_NAME, entity.getFirstName())
                        .addValue(EDUCATOR_LAST_NAME, entity.getLastName())
                        .addValue(EDUCATOR_BIRTHDAY, entity.getBirthday())
                        .addValue(EDUCATOR_EMAIL, entity.getEmail())
                        .addValue(EDUCATOR_ACADEMIC_RANK, entity.getAcademicRank().getKey())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected Educator update(Educator entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getUserName(), entity.getPasswordHash(), entity.getUserRole().getValue(),
                entity.getFirstName(), entity.getLastName(), entity.getBirthday(), entity.getEmail(),
                entity.getAcademicRank().getKey(), entity.getId(), EDUCATOR_TYPE_CODE)) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<Educator> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new EducatorRowMapper(), id, EDUCATOR_TYPE_CODE)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Educator> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new EducatorRowMapper(), EDUCATOR_TYPE_CODE, limit);
    }

    @Override
    public List<Educator> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Object[] args = new Object[ids.size() + 1];
        for (int i = 0; i < ids.size(); i++) {
            args[i] = ids.get(i);
        }
        args[ids.size()] = EDUCATOR_TYPE_CODE;
        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new EducatorRowMapper(),
                args);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class, EDUCATOR_TYPE_CODE);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id, EDUCATOR_TYPE_CODE))
            throw new EmptyResultDataAccessException("Unable to delete educator entity with id" + id, 1);
    }

    @Override
    public void delete(Educator entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId(), EDUCATOR_TYPE_CODE))
            throw new EmptyResultDataAccessException("Unable to delete educator entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Object[] args = new Object[ids.size() + 1];
        for (int i = 0; i < ids.size(); i++) {
            args[i] = ids.get(i);
        }
        args[ids.size()] = EDUCATOR_TYPE_CODE;
        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                args);
    }

    @Override
    public void deleteAll(List<Educator> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));
        Object[] args = new Object[entities.size() + 1];
        for (int i = 0; i < entities.size(); i++) {
            args[i] = entities.get(i).getId();
        }
        args[entities.size()] = EDUCATOR_TYPE_CODE;
        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                args);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, EDUCATOR_TYPE_CODE);
    }

    @Override
    public Optional<Educator> findByLogin(String userName) {
        return jdbcTemplate.query(EDUCATOR_BY_LOGIN, new EducatorRowMapper(), userName, EDUCATOR_TYPE_CODE)
                .stream().findFirst();
    }

    @Override
    public Optional<Educator> findByEmail(String email) {
        return jdbcTemplate.query(EDUCATOR_BY_EMAIL, new EducatorRowMapper(), email, EDUCATOR_TYPE_CODE)
                .stream().findFirst();
    }

    @Override
    public List<Educator> findAllByUserRole(UserRole userRole) {
        return jdbcTemplate.query(EDUCATOR_BY_USER_ROLE, new EducatorRowMapper(), userRole.getValue(), EDUCATOR_TYPE_CODE);
    }

    @Override
    public List<Educator> findAllByBirthday(LocalDate birthday) {
        return jdbcTemplate.query(EDUCATOR_BY_BIRTHDAY, new EducatorRowMapper(), birthday, EDUCATOR_TYPE_CODE);
    }

    @Override
    public List<Educator> findAllByAcademicRank(AcademicRank academicRank) {
        return jdbcTemplate.query(EDUCATOR_BY_ACADEMIC_RANK, new EducatorRowMapper(), academicRank.getKey(), EDUCATOR_TYPE_CODE);
    }
}
