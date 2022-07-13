package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.com.foxminded.university.dao.implementation.EducatorDaoImpl.*;

public class EducatorRowMapper implements RowMapper<Educator> {
    @Override
    public Educator mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Educator(
                rs.getLong(EDUCATOR_ID),
                rs.getString(EDUCATOR_FIRST_NAME),
                rs.getString(EDUCATOR_LAST_NAME),
                LocalDate.parse(rs.getString(EDUCATOR_BIRTHDAY)),
                // rs.getDate(EDUCATOR_BIRTHDAY),
                rs.getString(EDUCATOR_EMAIL),
                rs.getLong(EDUCATOR_WEEKLY_SCHEDULE_ID),
                UserRole.valueOf(rs.getString(EDUCATOR_ROLE)),
                rs.getString(EDUCATOR_POSITION)
        );
    }
}
