package ua.com.foxminded.university.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.schedule.DailySchedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.com.foxminded.university.dao.implementation.DailyScheduleDaoImpl.*;

public class DailyScheduleRowMapper implements RowMapper<DailySchedule> {
    @Override
    public DailySchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DailySchedule(
                rs.getLong(DAILY_SCHEDULE_ID),
                LocalDate.parse(rs.getString(DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL)),
                // rs.getDate(DAILY_SCHEDULE_DATE_OF_SCHEDULE_CELL),
                DayOfWeek.valueOf(rs.getString(DAILY_SCHEDULE_DAY_OF_WEEK)),
                rs.getLong(DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID)
        );
    }
}
