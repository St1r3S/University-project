package ua.com.foxminded.university.dao.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.jdbc.JdbcLectureDao.*;

public class LectureRowMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Lecture(
                rs.getLong(LECTURE_ID),
                rs.getLong(LECTURE_DISCIPLINE_ID),


                rs.getLong(LECTURE_LECTURE_NUMBER_ID),
                rs.getLong(LECTURE_ROOM_ID),
                rs.getLong(LECTURE_DAILY_SCHEDULE_ID)
        );
    }
}
