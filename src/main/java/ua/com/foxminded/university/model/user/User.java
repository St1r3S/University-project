package ua.com.foxminded.university.model.user;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;

import java.time.LocalDateTime;

public class User extends LongEntity {

    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    private WeeklySchedule schedule;
    private String email;
    private Role role;

    public User(Long id, String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.schedule = schedule;
        this.email = email;
        this.role = role;
    }

    public User(String firstName, String lastName, LocalDateTime birthday, WeeklySchedule schedule, String email, Role role) {
        this(null, firstName, lastName, birthday, schedule, email, role);
    }
}
