package ua.com.foxminded.university.model.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lesson.Lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "schedule_days")
public class ScheduleDay extends LongEntity {
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @OneToMany(mappedBy = "scheduleDay")
    private List<Lesson> lessons;

    public ScheduleDay(Long id, DayOfWeek dayOfWeek, SemesterType semesterType) {
        super(id);
        this.dayOfWeek = dayOfWeek;
        this.semesterType = semesterType;
    }

    public ScheduleDay(DayOfWeek dayOfWeek, SemesterType semesterType) {
        this(null, dayOfWeek, semesterType);
    }
}
