package ua.com.foxminded.university.dao.hibernate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.user.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.com.foxminded.university.dao.hibernate.HibernateEducatorDao.*;

public class EducatorRowMapper implements RowMapper<Educator> {

    @Override
    public Educator mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getInt(USER_TYPE) == UserType.EDUCATOR.getTypeCode()) {
            return new Educator(
                    rs.getLong(EDUCATOR_ID),
                    rs.getString(EDUCATOR_LOGIN),
                    rs.getString(EDUCATOR_PASSWORD),
                    UserRole.get(rs.getString(EDUCATOR_ROLE)),
                    rs.getString(EDUCATOR_FIRST_NAME),
                    rs.getString(EDUCATOR_LAST_NAME),
                    LocalDate.parse(rs.getString(EDUCATOR_BIRTHDAY)),
                    // rs.getDate(EDUCATOR_BIRTHDAY),
                    rs.getString(EDUCATOR_EMAIL),
                    AcademicRank.get(rs.getString(EDUCATOR_ACADEMIC_RANK))
            );
        }
        throw new IllegalArgumentException("Unknown type " + rs.getInt(USER_TYPE));
    }
}
