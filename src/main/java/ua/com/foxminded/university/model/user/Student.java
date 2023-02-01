package ua.com.foxminded.university.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Student extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialism_id", nullable = false)
    private Specialism specialism;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;

    public Student(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, Group group, Specialism specialism, AcademicYear academicYear) {
        super(id, userName, passwordHash, userRole, firstName, lastName, birthday, email);
        this.group = group;
        this.specialism = specialism;
        this.academicYear = academicYear;
    }

    public Student(String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, Group group, Specialism specialism, AcademicYear academicYear) {
        this(null, userName, passwordHash, userRole, firstName, lastName, birthday, email, group, specialism, academicYear);
    }
}
