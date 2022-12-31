package ua.com.foxminded.university.model.user;

public enum UserRole {
    STAFF("Staff"),
    STUDENT("Student"),
    EDUCATOR("Educator"),
    ADMIN("Admin");
    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole get(String value) {
        for (UserRole d : values()) {
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
