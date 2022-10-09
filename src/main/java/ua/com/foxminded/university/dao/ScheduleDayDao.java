package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

public interface ScheduleDayDao extends CrudDao<ScheduleDay, Long> {

    List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<ScheduleDay> findBySemesterType(SemesterType semesterType);

    Optional<ScheduleDay> findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType);

}
