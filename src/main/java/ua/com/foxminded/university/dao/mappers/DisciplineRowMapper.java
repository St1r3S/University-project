package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.Discipline;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.implementation.DisciplineDaoImpl.*;

public class DisciplineRowMapper implements RowMapper<Discipline> {
    @Override
    public Discipline mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Discipline(
                rs.getLong(DISCIPLINE_ID),
                rs.getString(DISCIPLINE_NAME),
                rs.getLong(DISCIPLINE_EDUCATOR_ID)
        );
    }
}
