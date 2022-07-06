package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDate;

public class UniversityStaffMember extends User {

    private final String position;

    public UniversityStaffMember(Long id, String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole, String position) {
        super(id, firstName, lastName, birthday, schedule, email, userRole);
        this.position = position;
    }

    public UniversityStaffMember(String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole, String position) {
        this(null, firstName, lastName, birthday, schedule, email, userRole, position);
    }
}
