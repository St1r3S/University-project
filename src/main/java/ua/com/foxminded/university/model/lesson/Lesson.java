package ua.com.foxminded.university.model.lesson;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Lesson extends LongEntity {

    private Long disciplineId;
    private Long groupId;
    private LessonNumber lessonNumber;
    private Long roomId;
    private Long scheduleDayId;

    public Lesson(Long id, Long disciplineId, Long groupId, LessonNumber lessonNumber, Long roomId, Long scheduleDayId) {
        super(id);
        this.disciplineId = disciplineId;
        this.groupId = groupId;
        this.lessonNumber = lessonNumber;
        this.roomId = roomId;
        this.scheduleDayId = scheduleDayId;
    }

    public Lesson(Long disciplineId, Long groupId, LessonNumber lessonNumber, Long roomId, Long scheduleDayId) {
        this(null, disciplineId, groupId, lessonNumber, roomId, scheduleDayId);
    }
}