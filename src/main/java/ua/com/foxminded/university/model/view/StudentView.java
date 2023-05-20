package ua.com.foxminded.university.model.view;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentView {

    private Long id;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 5, message = "{Size.User.Username}")
    private String userName;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 5, message = "{Size.User.Password}")
    private String passwordHash;
    private UserRole userRole;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 3, message = "{Size.User.FirstName}")
    private String firstName;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 3, message = "{Size.User.LastName}")
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "{Past.User.Birthday}")
    private LocalDate birthday;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Email(message = "{Pattern.User.Email}")
    private String email;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    private String groupName;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    private String specialismName;
    private Integer academicYearNumber;
    private SemesterType semesterType;


    public StudentView(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, String groupName, String specialismName, Integer academicYearNumber, SemesterType semesterType) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.groupName = groupName;
        this.specialismName = specialismName;
        this.academicYearNumber = academicYearNumber;
        this.semesterType = semesterType;
    }

    public static StudentView studentToStudentView(Student student, Group group, Specialism specialism, AcademicYear academicYear) {
        return new StudentView(student.getId(), student.getUserName(), student.getPasswordHash(), student.getUserRole(),
                student.getFirstName(), student.getLastName(), student.getBirthday(), student.getEmail(),
                group.getGroupName(), specialism.getSpecialismName(), academicYear.getYearNumber(),
                academicYear.getSemesterType());
    }

    // Don't forget to hash password while convert studentView to student
    public Student studentViewToStudent(Group group, Specialism specialism, AcademicYear academicYear) {
        return new Student(id, userName, passwordHash, userRole, firstName, lastName, birthday, email, group, specialism, academicYear);
    }
}
