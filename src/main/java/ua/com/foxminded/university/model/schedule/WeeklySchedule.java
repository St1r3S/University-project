package ua.com.foxminded.university.model.schedule;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;

public class WeeklySchedule extends LongEntity implements Schedule {
    public static final String WEEKLY_SCHEDULE_ID = "id";

    private List<DailySchedule> weeklySchedule;

    public WeeklySchedule(Long id, List<DailySchedule> weeklySchedule) {
        super(id);
        this.weeklySchedule = weeklySchedule;
    }

    public WeeklySchedule(List<DailySchedule> weeklySchedule) {
        this(null, weeklySchedule);
    }

    @Override
    public List<Lecture> getSchedule() {
        return null;
    }
}
