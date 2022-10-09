package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;

public interface ScheduleDayService extends CrudService<ScheduleDay, Long> {

    List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<ScheduleDay> findBySemesterType(SemesterType semesterType);

    ScheduleDay findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType);

}
