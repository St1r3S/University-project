package ua.com.foxminded.university.model.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.lesson.Lesson;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "schedule_days")
public class ScheduleDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "scheduleDay")
    private List<Lesson> lessons;

    public ScheduleDay(Long id, DayOfWeek dayOfWeek, SemesterType semesterType) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.semesterType = semesterType;
    }

    public ScheduleDay(DayOfWeek dayOfWeek, SemesterType semesterType) {
        this(null, dayOfWeek, semesterType);
    }
}
