package ua.com.foxminded.university.model.schedule;

public enum SemesterType {
    FALL_SEMESTER("fall"),
    SPRING_SEMESTER("spring");

    private final String value;

    SemesterType(String value) {
        this.value = value;
    }

    public static SemesterType get(String value) {
        for (SemesterType s : values()) {
            if (s.getValue().equals(value)) {
                return s;
            }
        }
        return null;
    }


    public String getValue() {
        return value;
    }
}
