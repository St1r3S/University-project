package ua.com.foxminded.university.model.schedule;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;

public class DailySchedule extends LongEntity implements Schedule {
    public static final String DAILY_SCHEDULE_ID = "id";
    public static final String DAILY_SCHEDULE_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";

    private List<Lecture> lectures;

    public DailySchedule(Long id, List<Lecture> lectures) {
        super(id);
        this.lectures = lectures;
    }

    public DailySchedule(List<Lecture> lectures) {
        this(null, lectures);
    }

    @Override
    public List<Lecture> getSchedule() {
        return null;
    }
}
