package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.LectureNumber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static ua.com.foxminded.university.dao.implementation.LectureNumberDaoImpl.*;

public class LectureNumberRowMapper implements RowMapper<LectureNumber> {
    @Override
    public LectureNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new LectureNumber(
                rs.getLong(LECTURE_NUMBER_ID),
                rs.getInt(LECTURE_NUMBER),
//                rs.getTime(LECTURE_TIME_START)
                LocalTime.parse(rs.getString(LECTURE_TIME_START)),
//                rs.getTime(LECTURE_TIME_END)
                LocalTime.parse(rs.getString(LECTURE_TIME_END))
        );
    }
}
