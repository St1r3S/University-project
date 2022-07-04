package ua.com.foxminded.university.model.lecture;

import ua.com.foxminded.university.model.LongEntity;

import java.time.LocalDateTime;

public class LectureNumber extends LongEntity {
    public static final String LECTURE_NUMBER_ID = "id";
    public static final String LECTURE_NUMBER = "lecture_number";
    public static final String LECTURE_TIME_START = "time_start";
    public static final String LECTURE_TIME_END = "time_end";
    private Integer lectureNumber;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    public LectureNumber(Long id, Integer lectureNumber, LocalDateTime timeStart, LocalDateTime timeEnd) {
        super(id);
        this.lectureNumber = lectureNumber;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LectureNumber(Integer lectureNumber, LocalDateTime timeStart, LocalDateTime timeEnd) {
        this(null, lectureNumber, timeStart, timeEnd);
    }
}
