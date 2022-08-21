package ua.com.foxminded.university.model.schedule;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeeklySchedule extends LongEntity implements Schedule {

    private Integer weekNumber;

    public WeeklySchedule(Long id, Integer weekNumber) {
        super(id);
        this.weekNumber = weekNumber;
    }

    public WeeklySchedule(Integer weekNumber) {
        this(null, weekNumber);
    }


    @Override
    public List<Lecture> getSchedule() {
        return null;
    }
}
