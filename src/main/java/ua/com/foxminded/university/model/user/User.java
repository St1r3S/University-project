package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDate;

public class User extends LongEntity {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final WeeklySchedule schedule;
    private final String email;
    private final UserRole userRole;

    public User(Long id, String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.schedule = schedule;
        this.email = email;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, LocalDate birthday, WeeklySchedule schedule, String email, UserRole userRole) {
        this(null, firstName, lastName, birthday, schedule, email, userRole);
    }
}
