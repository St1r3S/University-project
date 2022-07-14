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
public class UniversityStaffMember extends User {

    private String position;

    public UniversityStaffMember(Long id, String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String position) {
        super(id, firstName, lastName, birthday, email, weeklyScheduleId, userRole);
        this.position = position;
    }

    public UniversityStaffMember(String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String position) {
        this(null, firstName, lastName, birthday, email, weeklyScheduleId, userRole, position);
    }
}
