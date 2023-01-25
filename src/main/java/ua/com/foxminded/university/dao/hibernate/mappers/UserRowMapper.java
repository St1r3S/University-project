package ua.com.foxminded.university.dao.hibernate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.com.foxminded.university.dao.hibernate.HibernateUserDao.*;

public class UserRowMapper implements RowMapper<User> {

    private final StudentRowMapper studentRowMapper = new StudentRowMapper();
    private final EducatorRowMapper educatorRowMapper = new EducatorRowMapper();

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        switch (rs.getInt(USER_TYPE)) {
            case 1:
                return new User(
                        rs.getLong(USER_ID),
                        rs.getString(USER_LOGIN),
                        rs.getString(USER_PASSWORD),
                        UserRole.get(rs.getString(USER_ROLE)),
                        rs.getString(USER_FIRST_NAME),
                        rs.getString(USER_LAST_NAME),
                        LocalDate.parse(rs.getString(USER_BIRTHDAY)),
                        // rs.getDate(USER_BIRTHDAY),
                        rs.getString(USER_EMAIL)
                );
            case 2:
                return studentRowMapper.mapRow(rs, rowNum);
            case 3:
                return educatorRowMapper.mapRow(rs, rowNum);
            default:
                throw new IllegalArgumentException("Unknown type " + rs.getInt(USER_TYPE));
        }
    }
}
