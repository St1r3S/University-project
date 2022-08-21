package ua.com.foxminded.university.model.user;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student extends User {


    private String groupName;
    private Long specialismId;

    public Student(Long id, String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String groupName, Long specialismId) {
        super(id, firstName, lastName, birthday, email, weeklyScheduleId, userRole);
        this.groupName = groupName;
        this.specialismId = specialismId;
    }

    public Student(String firstName, String lastName, LocalDate birthday, String email, Long weeklyScheduleId, UserRole userRole, String groupName, Long specialismId) {
        this(null, firstName, lastName, birthday, email, weeklyScheduleId, userRole, groupName, specialismId);
    }
}
