package ua.com.foxminded.university.dao.jdbc.mappers;


import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.jdbc.JdbcStudentDao.*;


public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getLong(STUDENT_ID),
                rs.getString(STUDENT_FIRST_NAME),
                rs.getString(STUDENT_LAST_NAME),
                //LocalDate.parse(rs.getString(STUDENT_BIRTHDAY)),
                rs.getDate(STUDENT_BIRTHDAY).toLocalDate(),
                rs.getString(STUDENT_EMAIL),
                rs.getLong(STUDENT_WEEKLY_SCHEDULE_ID),
                UserRole.valueOf(rs.getString(STUDENT_ROLE)),
                rs.getString(STUDENT_GROUP_NAME),
                rs.getLong(STUDENT_SPECIALISM)
        );
    }
}
