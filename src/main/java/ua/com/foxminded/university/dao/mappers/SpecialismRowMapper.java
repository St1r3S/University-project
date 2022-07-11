package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.misc.Specialism;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.impl.SpecialismDao.SPECIALISM_ID;
import static ua.com.foxminded.university.dao.impl.SpecialismDao.SPECIALISM_SPECIALISM;

public class SpecialismRowMapper implements RowMapper<Specialism> {
    @Override
    public Specialism mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Specialism(
                rs.getLong(SPECIALISM_ID),
                rs.getString(SPECIALISM_SPECIALISM)
        );
    }
}
