package ua.com.foxminded.university.dao.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.com.foxminded.university.dao.jdbc.JdbcStudentDao.*;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getInt(USER_TYPE) == UserType.STUDENT.getTypeCode()) {
            return new Student(
                    rs.getLong(STUDENT_ID),
                    rs.getString(STUDENT_LOGIN),
                    rs.getString(STUDENT_PASSWORD),
                    UserRole.get(rs.getString(STUDENT_ROLE)),
                    rs.getString(STUDENT_FIRST_NAME),
                    rs.getString(STUDENT_LAST_NAME),
                    LocalDate.parse(rs.getString(STUDENT_BIRTHDAY)),
                    // rs.getDate(STUDENT_BIRTHDAY),
                    rs.getString(STUDENT_EMAIL),
                    rs.getLong(STUDENT_GROUP_ID),
                    rs.getLong(STUDENT_SPECIALISM_ID),
                    rs.getLong(STUDENT_ACADEMIC_YEAR_ID)
            );
        }
        throw new IllegalArgumentException("Unknown type " + rs.getInt(USER_TYPE));
    }
}
