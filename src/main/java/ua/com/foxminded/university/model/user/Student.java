package ua.com.foxminded.university.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class Student extends User {


    private final String groupName;
    private final Long specialismId;

    public Student(Long id, String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String groupName, Long specialismId) {
        super(id, firstName, lastName, birthday, email, weeklyScheduleId, userRole);
        this.groupName = groupName;
        this.specialismId = specialismId;
    }

    public Student(String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String groupName, Long specialismId) {
        this(null, firstName, lastName, birthday, email, weeklyScheduleId, userRole, groupName, specialismId);
    }
}
