package ua.com.foxminded.university.dao.hibernate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lesson.Specialism;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.hibernate.HibernateSpecialismDao.SPECIALISM_ID;
import static ua.com.foxminded.university.dao.hibernate.HibernateSpecialismDao.SPECIALISM_NAME;

public class SpecialismRowMapper implements RowMapper<Specialism> {
    @Override
    public Specialism mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Specialism(
                rs.getLong(SPECIALISM_ID),
                rs.getString(SPECIALISM_NAME)
        );
    }
}
