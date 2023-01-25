package ua.com.foxminded.university.dao.hibernate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.hibernate.HibernateLessonDao.*;

public class LessonRowMapper implements RowMapper<Lesson> {
    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Lesson(
                rs.getLong(LESSON_ID),
                rs.getLong(LESSON_DISCIPLINE_ID),
                rs.getLong(LESSON_GROUP_ID),
                LessonNumber.get(rs.getInt(LESSON_NUMBER)),
                rs.getLong(LESSON_ROOM_ID),
                rs.getLong(LESSON_SCHEDULE_DAY_ID)
        );
    }
}
