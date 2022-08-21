package ua.com.foxminded.university.model.lecture;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Lecture extends LongEntity {

    private Long disciplineId;
    private Long lectureNumberId;
    private Long roomId;
    private Long dailyScheduleId;


    public Lecture(Long id, Long disciplineId, Long lectureNumberId, Long roomId, Long dailyScheduleId) {
        super(id);
        this.disciplineId = disciplineId;
        this.lectureNumberId = lectureNumberId;
        this.roomId = roomId;
        this.dailyScheduleId = dailyScheduleId;
    }

    public Lecture(Long disciplineId, Long lectureNumberId, Long roomId, Long dailyScheduleId) {
        this(null, disciplineId, lectureNumberId, roomId, dailyScheduleId);
    }
}
