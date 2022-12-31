package ua.com.foxminded.university.dao.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.jdbc.JdbcGroupDao.*;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(
                rs.getLong(GROUP_ID),
                rs.getString(GROUP_NAME),
                rs.getLong(GROUP_SPECIALISM_ID),
                rs.getLong(GROUP_ACADEMIC_YEAR_ID)
        );
    }
}