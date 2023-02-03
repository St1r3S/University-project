package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {

    List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<ScheduleDay> findBySemesterType(SemesterType semesterType);

    Optional<ScheduleDay> findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType);

}
