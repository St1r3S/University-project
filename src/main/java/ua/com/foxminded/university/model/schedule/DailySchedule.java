package ua.com.foxminded.university.model.schedule;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DailySchedule extends LongEntity implements Schedule {

    private LocalDate dateOfScheduleCell;
    private DayOfWeek dayOfWeek;
    private Long weeklyScheduleId;

    public DailySchedule(Long id, LocalDate dateOfScheduleCell, DayOfWeek dayOfWeek, Long weeklyScheduleId) {
        super(id);
        this.dateOfScheduleCell = dateOfScheduleCell;
        this.dayOfWeek = dayOfWeek;
        this.weeklyScheduleId = weeklyScheduleId;
    }

    public DailySchedule(LocalDate dateOfScheduleCell, DayOfWeek dayOfWeek, Long weeklyScheduleId) {
        this(null, dateOfScheduleCell, dayOfWeek, weeklyScheduleId);
    }

    @Override
    public List<Lecture> getSchedule() {
        return null;
    }
}
