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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_FIRST_NAME = "first_name";
    public static final String STUDENT_LAST_NAME = "last_name";
    public static final String STUDENT_BIRTHDAY = "birthday";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String STUDENT_ROLE = "user_role";
    public static final String STUDENT_GROUP_NAME = "group_name";
    public static final String STUDENT_SPECIALISM = "specialism_id";
    //    public static final String CRETE = "INSERT INTO student(first_name, last_name, birthday, email, weekly_schedule_id, " +
//            "user_role, group_name, specialism_id) VALUES (?,?,?,?,?,?,?,?)";
    public static final String RETRIEVE = "SELECT id, first_name, last_name, birthday, email, weekly_schedule_id, " +
            "user_role, group_name, specialism_id FROM student WHERE id = ?";
    public static final String UPDATE = "UPDATE student SET first_name = ?, last_name = ?, birthday = ?, email = ?, " +
            "weekly_schedule_id = ?, user_role = ?, group_name = ?, specialism_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM student WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, first_name, last_name, birthday, email, weekly_schedule_id, " +
            "user_role, group_name, specialism_id FROM student LIMIT 100";
    private static final String STUDENTS_BY_LECTURE_ID = "SELECT s.id, s.first_name, s.last_name, s.birthday, s.email, " +
            "s.weekly_schedule_id, s.user_role, s.group_name, s.specialism_id FROM student AS s " +
            "INNER JOIN lecture_student AS ls ON s.id = ls.student_id " +
            "INNER JOIN lecture AS l ON ls.lecture_id = l.id where l.id = ?";
    private static final String STUDENTS_BY_SPECIALISM_ID = "SELECT id, first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, group_name, specialism_id FROM student WHERE specialism_id = ?";
    private static final String STUDENTS_BY_GROUP_NAME = "SELECT id, first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, group_name, specialism_id FROM student WHERE group_name = ?";
    private static final String STUDENTS_BY_BIRTHDAY = "SELECT id, first_name, last_name, birthday, email, " +
            "weekly_schedule_id, user_role, group_name, specialism_id FROM student WHERE birthday = ?";
    private static final String INSERT_LECTURE_STUDENT = "INSERT INTO lecture_student(lecture_id, student_id) VALUES (?, ?)";

    private static final String DELETE_LECTURE_STUDENT = "DELETE FROM lecture_student WHERE lecture_id = ? AND student_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("student").usingGeneratedKeyColumns("id");
    }

    @Override
    public Student create(Student entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(STUDENT_FIRST_NAME, entity.getFirstName())
                        .addValue(STUDENT_LAST_NAME, entity.getLastName())
                        .addValue(STUDENT_BIRTHDAY, entity.getBirthday())
                        .addValue(STUDENT_EMAIL, entity.getEmail())
                        .addValue(STUDENT_WEEKLY_SCHEDULE_ID, entity.getWeeklyScheduleId())
                        .addValue(STUDENT_ROLE, entity.getUserRole().toString())
                        .addValue(STUDENT_GROUP_NAME, entity.getGroupName())
                        .addValue(STUDENT_SPECIALISM, entity.getSpecialismId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new StudentRowMapper(), id).stream().findFirst();
    }

    @Override
    public Student update(Student entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole().toString(), entity.getGroupName(),
                entity.getSpecialismId(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete student entity with id" + id, 1);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper());
    }

    @Override
    public List<Student> findAllByLectureId(Long lectureId) {
        return jdbcTemplate.query(STUDENTS_BY_LECTURE_ID, new StudentRowMapper(), lectureId);
    }

    @Override
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(STUDENTS_BY_SPECIALISM_ID, new StudentRowMapper(), specialismId);
    }

    @Override
    public List<Student> findAllByGroupName(String groupName) {
        return jdbcTemplate.query(STUDENTS_BY_GROUP_NAME, new StudentRowMapper(), groupName);
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return jdbcTemplate.query(STUDENTS_BY_BIRTHDAY, new StudentRowMapper(), birthday);
    }

    @Override
    public void enrollLectureStudent(Long lectureId, Long studentId) {
        if (1 != jdbcTemplate.update(INSERT_LECTURE_STUDENT, lectureId, studentId))
            throw new EmptyResultDataAccessException("Unable to enroll lecture entity with id " + lectureId +
                    "with student entity with id " + studentId, 1);
    }

    @Override
    public void expelLectureStudent(Long lectureId, Long studentId) {
        if (1 != jdbcTemplate.update(DELETE_LECTURE_STUDENT, lectureId, studentId))
            throw new EmptyResultDataAccessException("Unable to expel lecture entity with id " + lectureId +
                    "with student entity with id " + studentId, 1);
    }
}
