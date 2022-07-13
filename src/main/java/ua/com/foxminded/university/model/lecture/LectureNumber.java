package ua.com.foxminded.university.model.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;

import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class LectureNumber extends LongEntity {

    private Integer number;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public LectureNumber(Long id, Integer number, LocalTime timeStart, LocalTime timeEnd) {
        super(id);
        this.number = number;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LectureNumber(Integer number, LocalTime timeStart, LocalTime timeEnd) {
        this(null, number, timeStart, timeEnd);
    }
}
