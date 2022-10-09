package ua.com.foxminded.university.model.user;

import java.util.AbstractMap;
import java.util.Map;

public enum AcademicRank {

    PROFESSOR("Professor", "prof."),
    DOCENT("Docent", "d."),
    SENIOR_EDUCATOR("Senior educator", "s_e."),
    EDUCATOR("Educator", "e.");

    private final String key;
    private final String value;

    AcademicRank(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static AcademicRank get(String key) {
        for (AcademicRank a : values()) {
            if (a.getKey().equals(key)) {
                return a;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Map.Entry<String, String> getBoth() {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}