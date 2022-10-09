package ua.com.foxminded.university.model.schedule;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleDay extends LongEntity {

    private DayOfWeek dayOfWeek;
    private SemesterType semesterType;

    public ScheduleDay(Long id, DayOfWeek dayOfWeek, SemesterType semesterType) {
        super(id);
        this.dayOfWeek = dayOfWeek;
        this.semesterType = semesterType;
    }

    public ScheduleDay(DayOfWeek dayOfWeek, SemesterType semesterType) {
        this(null, dayOfWeek, semesterType);
    }
}
