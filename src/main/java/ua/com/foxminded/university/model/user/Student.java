package ua.com.foxminded.university.model.user;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student extends User {

    private Long groupId;
    private Long specialismId;
    private Long academicYearId;

    public Student(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, Long groupId, Long specialismId, Long academicYearId) {
        super(id, userName, passwordHash, userRole, firstName, lastName, birthday, email);
        this.groupId = groupId;
        this.specialismId = specialismId;
        this.academicYearId = academicYearId;

    }

    public Student(String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, Long groupId, Long specialismId, Long academicYearId) {
        this(null, userName, passwordHash, userRole, firstName, lastName, birthday, email, groupId, specialismId, academicYearId);
    }
}
