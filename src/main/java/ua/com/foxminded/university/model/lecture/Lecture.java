package ua.com.foxminded.university.model.lecture;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.user.Student;

import java.util.List;

public class Lecture extends LongEntity {
    public static final String LECTURE_ID = "id";
    public static final String LECTURE_DISCIPLINE_ID = "discipline_id";
    public static final String LECTURE_WEEK_NUMBER = "week_number";
    public static final String LECTURE_DAY_OF_WEEK = "day_of_week";
    public static final String LECTURE_LECTURE_NUMBER = "lecture_number";
    public static final String LECTURE_ROOM = "room";
    public static final String LECTURE_DAILY_SCHEDULE = "daily_schedule_id";
    private Discipline discipline;
    private Integer weekNumber;
    private DayOfWeek dayOfWeek;
    private LectureNumber lectureNumber;
    private Room room;
    private List<Student> attendance;

    public Lecture(Long id, Discipline discipline, Integer weekNumber, DayOfWeek dayOfWeek, LectureNumber lectureNumber, Room room, List<Student> attendance) {
        super(id);
        this.discipline = discipline;
        this.weekNumber = weekNumber;
        this.dayOfWeek = dayOfWeek;
        this.lectureNumber = lectureNumber;
        this.room = room;
        this.attendance = attendance;
    }

    public Lecture(Discipline discipline, Integer weekNumber, DayOfWeek dayOfWeek, LectureNumber lectureNumber, Room room, List<Student> attendance) {
        this(null, discipline, weekNumber, dayOfWeek, lectureNumber, room, attendance);
    }
}
