package ua.com.foxminded.university.model.user;

public enum UserType {
    USER(1),
    STUDENT(2),
    EDUCATOR(3);

    private final Integer typeCode;

    UserType(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public static UserType get(Integer typeCode) {
        for (UserType u : values()) {
            if (u.getTypeCode().equals(typeCode)) {
                return u;
            }
        }
        return null;
    }

    public Integer getTypeCode() {
        return typeCode;
    }
}
