package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDate;

public class Student extends User {

    public static final String STUDENT_ID = "id";
    public static final String STUDENT_FIRST_NAME = "first_name";
    public static final String STUDENT_LAST_NAME = "last_name";
    public static final String STUDENT_BIRTHDAY = "birthday";
    public static final String STUDENT_EMAIL = "email";
    public static final String STUDENT_WEEKLY_SCHEDULE_ID = "weekly_schedule_id";
    public static final String STUDENT_ROLE = "role";
    public static final String STUDENT_GROUP_NAME = "group_name";
    public static final String STUDENT_SPECIALISM = "specialism";

    private final String groupName;
    private final String specialism;

    public Student(Long id, String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole, String groupName, String specialism) {
        super(id, firstName, lastName, birthday, schedule, email, userRole);
        this.groupName = groupName;
        this.specialism = specialism;
    }

    public Student(String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole, String groupName, String specialism) {
        this(null, firstName, lastName, birthday, schedule, email, userRole, groupName, specialism);
    }
}
