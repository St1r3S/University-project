package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

public interface WeeklyScheduleService extends CrudService<WeeklySchedule, Long> {
    WeeklySchedule findByWeekNumber(Integer weekNumber);
}
