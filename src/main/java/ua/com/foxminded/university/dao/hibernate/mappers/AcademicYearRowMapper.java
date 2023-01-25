package ua.com.foxminded.university.dao.hibernate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.hibernate.HibernateAcademicYearDao.*;

public class AcademicYearRowMapper implements RowMapper<AcademicYear> {
    @Override
    public AcademicYear mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AcademicYear(
                rs.getLong(ACADEMIC_YEAR_ID),
                rs.getInt(ACADEMIC_YEAR_YEAR_NUMBER),
                SemesterType.get(rs.getString(ACADEMIC_YEAR_SEMESTER_TYPE))
        );
    }
}
