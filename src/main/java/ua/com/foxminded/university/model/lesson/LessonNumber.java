package ua.com.foxminded.university.model.lesson;

import java.util.AbstractMap;
import java.util.Map;

public enum LessonNumber {

    FIRST_LESSON(1, Map.of("08:00", "09:35")),
    SECOND_LESSON(2, Map.of("09:50", "11:25")),
    THIRD_LESSON(3, Map.of("11:40", "13:15")),
    FOURTH_LESSON(4, Map.of("13:30", "15:05")),
    FIFTH_LESSON(5, Map.of("15:20", "16:55")),
    SIXTH_LESSON(6, Map.of("17:10", "18:45"));

    private final Integer lessonNumber;
    private final Map<String, String> lessonTime;

    LessonNumber(Integer lessonNumber, Map<String, String> lessonTime) {
        this.lessonNumber = lessonNumber;
        this.lessonTime = lessonTime;
    }

    public static LessonNumber get(Integer lessonNumber) {
        for (LessonNumber l : values()) {
            if (l.getLessonNumber().equals(lessonNumber)) {
                return l;
            }
        }
        return null;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public Map<String, String> getLessonTime() {
        return lessonTime;
    }

    public Map.Entry<Integer, Map<String, String>> getLessonNumberAndTime() {
        return new AbstractMap.SimpleEntry<>(lessonNumber, lessonTime);
    }
}
