package ua.com.foxminded.university.model.lecture;

import ua.com.foxminded.university.model.LongEntity;

import java.time.LocalTime;

public class LectureNumber extends LongEntity {
    public static final String LECTURE_NUMBER_ID = "id";
    public static final String LECTURE_NUMBER = "lecture_number";
    public static final String LECTURE_TIME_START = "time_start";
    public static final String LECTURE_TIME_END = "time_end";
    private final Integer lectureNumber;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;

    public LectureNumber(Long id, Integer lectureNumber, LocalTime timeStart, LocalTime timeEnd) {
        super(id);
        this.lectureNumber = lectureNumber;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LectureNumber(Integer lectureNumber, LocalTime timeStart, LocalTime timeEnd) {
        this(null, lectureNumber, timeStart, timeEnd);
    }
}
