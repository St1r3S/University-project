package ua.com.foxminded.university.model.view;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.Group;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class LessonView {
    private Long id;
    private String disciplineName;
    private Educator educator;
    private String groupName;
    private LessonNumber lessonNumber;
    private String roomNumber;
    private ScheduleDay scheduleDay;
    private Boolean isBlank;

    public LessonView(Long id, String disciplineName, Educator educator, String groupName, LessonNumber lessonNumber, String roomNumber, ScheduleDay scheduleDay) {
        this.id = id;
        this.disciplineName = disciplineName;
        this.educator = educator;
        this.groupName = groupName;
        this.lessonNumber = lessonNumber;
        this.roomNumber = roomNumber;
        this.scheduleDay = scheduleDay;
        this.isBlank = false;
    }

    public LessonView() {
        this.isBlank = true;
    }

    public static LessonView lessonToLessonView(Lesson lesson, Discipline discipline, Educator educator, Group group, Room room, ScheduleDay scheduleDay) {
        return new LessonView(lesson.getId(), discipline.getDisciplineName(), educator, group.getGroupName(), lesson.getLessonNumber(), room.getRoomNumber(), scheduleDay);
    }

    public Lesson lessonViewToLesson(Discipline discipline, Group group, Room room) {
        return new Lesson(this.id, discipline, group, this.lessonNumber, room, scheduleDay);
    }
}
