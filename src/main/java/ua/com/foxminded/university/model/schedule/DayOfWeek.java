package ua.com.foxminded.university.model.schedule;

public enum DayOfWeek {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    private final String value;

    DayOfWeek(String value) {
        this.value = value;
    }

    public static DayOfWeek get(String value) {
        for (DayOfWeek d : values()) {
            if (d.getValue().equals(value)) {
                return d;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

}
