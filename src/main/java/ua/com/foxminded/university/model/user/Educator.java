package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDateTime;

public class Educator extends UniversityStaffMember {
    public static final String EDUCATOR_ID = "id";
    public static final String EDUCATOR_FIRST_NAME = "first_name";
    public static final String EDUCATOR_LAST_NAME = "last_name";
    public static final String EDUCATOR_BIRTHDAY = "birthday";
    public static final String EDUCATOR_EMAIL = "email";
    public static final String EDUCATOR_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String EDUCATOR_ROLE = "role";
    public static final String EDUCATOR_POSITION = "position";
    public static final String EDUCATOR_SPECIALISM = "specialism";
    private String specialism;

    public Educator(Long id, String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role, String position, String specialism) {
        super(id, firstName, lastName, birthday, schedule, email, role, position);
        this.specialism = specialism;
    }

    public Educator(String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role, String position, String specialism) {
        this(null, firstName, lastName, birthday, schedule, email, role, position, specialism);
    }
}
