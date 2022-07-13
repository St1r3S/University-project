package ua.com.foxminded.university.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class User extends LongEntity {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private Long weeklyScheduleId;
    private UserRole userRole;


    public User(Long id, String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.weeklyScheduleId = weeklyScheduleId;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole) {
        this(null, firstName, lastName, birthday, email, weeklyScheduleId, userRole);
    }
}