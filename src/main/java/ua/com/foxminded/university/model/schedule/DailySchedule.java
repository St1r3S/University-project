package ua.com.foxminded.university.model.schedule;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class DailySchedule extends LongEntity implements Schedule {

    private DayOfWeek dayOfWeek;
    private Long weeklyScheduleId;

    public DailySchedule(Long id, DayOfWeek dayOfWeek, Long weeklyScheduleId) {
        super(id);
        this.dayOfWeek = dayOfWeek;
        this.weeklyScheduleId = weeklyScheduleId;
    }

    public DailySchedule(DayOfWeek dayOfWeek, Long weeklyScheduleId) {
        this(null, dayOfWeek, weeklyScheduleId);
    }

    @Override
    public List<Lecture> getSchedule() {
        return null;
    }
}
