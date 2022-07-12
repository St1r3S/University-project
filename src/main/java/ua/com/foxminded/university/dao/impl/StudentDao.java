package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.StudentRowMapper;
import ua.com.foxminded.university.model.user.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao implements DAO<Long, Student> {
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_FIRST_NAME = "first_name";
    public static final String STUDENT_LAST_NAME = "last_name";
    public static final String STUDENT_BIRTHDAY = "birthday";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String STUDENT_ROLE = "user_role";
    public static final String STUDENT_GROUP_NAME = "group_name";
    public static final String STUDENT_SPECIALISM = "specialism_id";
    public static final String CRETE = "INSERT INTO student(first_name, last_name, birthday, email, weekly_schedule_id, " +
            "user_role, group_name, specialism_id) VALUES (?,?,?,?,?,?,?,?)";
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

    private final JdbcTemplate jdbcTemplate;

    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Student entity) {
        jdbcTemplate.update(CRETE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole().toString(), entity.getGroupName(),
                entity.getSpecialismId());
    }

    @Override
    public Optional<Student> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new StudentRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(Student entity) {
        jdbcTemplate.update(UPDATE, entity.getFirstName(), entity.getLastName(), entity.getBirthday(),
                entity.getEmail(), entity.getWeeklyScheduleId(), entity.getUserRole().toString(), entity.getGroupName(),
                entity.getSpecialismId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(Student entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper());
    }

    public List<Student> getStudentsByLectureId(Long lectureId) {
        return jdbcTemplate.query(STUDENTS_BY_LECTURE_ID, new StudentRowMapper(), lectureId);
    }

    public List<Student> getStudentsBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(STUDENTS_BY_SPECIALISM_ID, new StudentRowMapper(), specialismId);
    }

    public List<Student> getStudentsByGroupName(String groupName) {
        return jdbcTemplate.query(STUDENTS_BY_GROUP_NAME, new StudentRowMapper(), groupName);
    }

    public List<Student> getStudentsByBirthday(LocalDate birthday) {
        return jdbcTemplate.query(STUDENTS_BY_BIRTHDAY, new StudentRowMapper(), birthday);
    }
}
