package ua.com.foxminded.university.dao.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.jdbc.JdbcScheduleDayDao.*;

public class ScheduleDayRowMapper implements RowMapper<ScheduleDay> {
    @Override
    public ScheduleDay mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleDay(
                rs.getLong(SCHEDULE_DAY_ID),
                DayOfWeek.get(rs.getString(SCHEDULE_DAY_DAY_OF_WEEK)),
                SemesterType.get(rs.getString(SCHEDULE_DAY_SEMESTER_TYPE))
        );
    }
}
