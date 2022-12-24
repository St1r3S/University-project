package ua.com.foxminded.university.model.schedule;

public enum ScheduleListType {
    GROUPS_LIST("groups"),
    EDUCATORS_LIST("educators"),
    ROOMS_LIST("rooms");
    private final String value;

    ScheduleListType(String value) {
        this.value = value;
    }

    public static ScheduleListType get(String value) {
        for (ScheduleListType s : values()) {
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
