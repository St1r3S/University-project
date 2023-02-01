package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.user.Group;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson extends LongEntity {
    @ManyToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    private LessonNumber lessonNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_day_id", nullable = false)
    private ScheduleDay scheduleDay;

    public Lesson(Long id, Discipline discipline, Group group, LessonNumber lessonNumber, Room room, ScheduleDay scheduleDay) {
        super(id);
        this.discipline = discipline;
        this.group = group;
        this.lessonNumber = lessonNumber;
        this.room = room;
        this.scheduleDay = scheduleDay;
    }

    public Lesson(Discipline discipline, Group group, LessonNumber lessonNumber, Room room, ScheduleDay scheduleDay) {
        this(null, discipline, group, lessonNumber, room, scheduleDay);
    }
}