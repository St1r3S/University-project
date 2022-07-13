package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.com.foxminded.university.dao.implementation.WeeklyScheduleDaoImpl.WEEKLY_SCHEDULE_ID;
import static ua.com.foxminded.university.dao.implementation.WeeklyScheduleDaoImpl.WEEKLY_SCHEDULE_WEEK_NUMBER;

public class WeeklyScheduleRowMapper implements RowMapper<WeeklySchedule> {
    @Override
    public WeeklySchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WeeklySchedule(
                rs.getLong(WEEKLY_SCHEDULE_ID),
                rs.getInt(WEEKLY_SCHEDULE_WEEK_NUMBER)
        );
    }
}
