package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDateTime;

public class UniversityStaffMember extends User {

    private String position;

    public UniversityStaffMember(Long id, String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role, String position) {
        super(id, firstName, lastName, birthday, schedule, email, role);
        this.position = position;
    }

    public UniversityStaffMember(String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role, String position) {
        this(null, firstName, lastName, birthday, schedule, email, role, position);
    }
}
