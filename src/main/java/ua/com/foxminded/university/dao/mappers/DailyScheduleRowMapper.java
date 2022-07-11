package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.impl.DailyScheduleDao.*;

public class DailyScheduleRowMapper implements RowMapper<DailySchedule> {
    @Override
    public DailySchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DailySchedule(
                rs.getLong(DAILY_SCHEDULE_ID),
                DayOfWeek.valueOf(rs.getString(DAILY_SCHEDULE_DAY_OF_WEEK)),
                rs.getLong(DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID)
        );
    }
}
