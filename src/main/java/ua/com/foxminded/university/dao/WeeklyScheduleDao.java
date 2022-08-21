package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.util.Optional;

public interface WeeklyScheduleDao extends CrudDao<WeeklySchedule, Long> {
    Optional<WeeklySchedule> findByWeekNumber(Integer weekNumber);
}
