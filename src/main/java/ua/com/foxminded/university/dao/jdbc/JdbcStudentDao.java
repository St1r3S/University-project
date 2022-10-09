package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.dao.jdbc.mappers.StudentRowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {

    public static final Integer STUDENT_TYPE_CODE = UserType.STUDENT.getTypeCode();
    public static final String STUDENT_ID = "id";
    public static final String USER_TYPE = "user_type";
    public static final String STUDENT_LOGIN = "user_name";
    public static final String STUDENT_PASSWORD = "password_hash";
    public static final String STUDENT_ROLE = "user_role";
    public static final String STUDENT_FIRST_NAME = "first_name";
    public static final String STUDENT_LAST_NAME = "last_name";
    public static final String STUDENT_BIRTHDAY = "birthday";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_GROUP_ID = "group_id";
    public static final String STUDENT_SPECIALISM_ID = "specialism_id";
    public static final String STUDENT_ACADEMIC_YEAR_ID = "academic_year_id";
    private static final String UPDATE = "UPDATE users SET user_name = ?, password_hash = ?, user_role = ?, first_name = ?, " +
            "last_name = ?, birthday = ?, email = ?, group_id = ?, specialism_id = ?, academic_year_id = ? WHERE id = ? AND user_type = ?";
    private static final String RETRIEVE = "SELECT * FROM users WHERE id = ? AND user_type = ?";
    private static final String FIND_ALL = "SELECT * FROM users WHERE user_type = ? LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM users WHERE id IN (%s) AND user_type = ?";
    private static final String COUNT = "SELECT count(*) FROM users WHERE user_type = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ? AND user_type = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM users WHERE id IN (%s) AND user_type = ?";
    private static final String DELETE_ALL = "DELETE FROM users WHERE user_type = ?";
    private static final String STUDENT_BY_LOGIN = "SELECT * FROM users WHERE user_name = ? AND user_type = ?";
    private static final String STUDENT_BY_EMAIL = "SELECT * FROM users WHERE email = ? AND user_type = ?";
    private static final String STUDENT_BY_USER_ROLE = "SELECT * FROM users WHERE user_role = ? AND user_type = ?";
    private static final String STUDENT_BY_BIRTHDAY = "SELECT * FROM users WHERE birthday = ? AND user_type = ?";
    private static final String STUDENT_BY_GROUP_ID = "SELECT * FROM users WHERE group_id = ? AND user_type = ?";
    private static final String STUDENT_BY_SPECIALISM_ID = "SELECT * FROM users WHERE specialism_id = ? AND user_type = ?";
    private static final String STUDENT_BY_ACADEMIC_YEAR_ID = "SELECT * FROM users WHERE academic_year_id = ? AND user_type = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }


    @Override
    protected Student create(Student entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(USER_TYPE, STUDENT_TYPE_CODE)
                        .addValue(STUDENT_LOGIN, entity.getUserName())
                        .addValue(STUDENT_PASSWORD, entity.getPasswordHash())
                        .addValue(STUDENT_ROLE, entity.getUserRole().getValue())
                        .addValue(STUDENT_FIRST_NAME, entity.getFirstName())
                        .addValue(STUDENT_LAST_NAME, entity.getLastName())
                        .addValue(STUDENT_BIRTHDAY, entity.getBirthday())
                        .addValue(STUDENT_EMAIL, entity.getEmail())
                        .addValue(STUDENT_GROUP_ID, entity.getGroupId())
                        .addValue(STUDENT_SPECIALISM_ID, entity.getSpecialismId())
                        .addValue(STUDENT_ACADEMIC_YEAR_ID, entity.getAcademicYearId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected Student update(Student entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getUserName(), entity.getPasswordHash(), entity.getUserRole().getValue(),
                entity.getFirstName(), entity.getLastName(), entity.getBirthday(), entity.getEmail(),
                entity.getGroupId(), entity.getSpecialismId(), entity.getAcademicYearId(), entity.getId(), STUDENT_TYPE_CODE)) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new StudentRowMapper(), id, STUDENT_TYPE_CODE)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Student> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper(), STUDENT_TYPE_CODE, limit);
    }

    @Override
    public List<Student> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Object[] args = new Object[ids.size() + 1];
        for (int i = 0; i < ids.size(); i++) {
            args[i] = ids.get(i);
        }
        args[ids.size()] = STUDENT_TYPE_CODE;
        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new StudentRowMapper(),
                args);
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class, STUDENT_TYPE_CODE);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id, STUDENT_TYPE_CODE))
            throw new EmptyResultDataAccessException("Unable to delete student entity with id" + id, 1);
    }

    @Override
    public void delete(Student entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId(), STUDENT_TYPE_CODE))
            throw new EmptyResultDataAccessException("Unable to delete student entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        Object[] args = new Object[ids.size() + 1];
        for (int i = 0; i < ids.size(); i++) {
            args[i] = ids.get(i);
        }
        args[ids.size()] = STUDENT_TYPE_CODE;
        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                args);
    }

    @Override
    public void deleteAll(List<Student> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));
        Object[] args = new Object[entities.size() + 1];
        for (int i = 0; i < entities.size(); i++) {
            args[i] = entities.get(i).getId();
        }
        args[entities.size()] = STUDENT_TYPE_CODE;
        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                args);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, STUDENT_TYPE_CODE);
    }

    @Override
    public Optional<Student> findByLogin(String userName) {
        return jdbcTemplate.query(STUDENT_BY_LOGIN, new StudentRowMapper(), userName, STUDENT_TYPE_CODE)
                .stream().findFirst();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return jdbcTemplate.query(STUDENT_BY_EMAIL, new StudentRowMapper(), email, STUDENT_TYPE_CODE)
                .stream().findFirst();
    }

    @Override
    public List<Student> findAllByUserRole(UserRole userRole) {
        return jdbcTemplate.query(STUDENT_BY_USER_ROLE, new StudentRowMapper(), userRole.getValue(), STUDENT_TYPE_CODE);
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return jdbcTemplate.query(STUDENT_BY_BIRTHDAY, new StudentRowMapper(), birthday, STUDENT_TYPE_CODE);
    }

    @Override
    public List<Student> findAllByGroupId(Long groupId) {
        return jdbcTemplate.query(STUDENT_BY_GROUP_ID, new StudentRowMapper(), groupId, STUDENT_TYPE_CODE);
    }

    @Override
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(STUDENT_BY_SPECIALISM_ID, new StudentRowMapper(), specialismId, STUDENT_TYPE_CODE);
    }

    @Override
    public List<Student> findAllByAcademicYearId(Long academicYearId) {
        return jdbcTemplate.query(STUDENT_BY_ACADEMIC_YEAR_ID, new StudentRowMapper(), academicYearId, STUDENT_TYPE_CODE);
    }
}
