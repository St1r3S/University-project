package ua.com.foxminded.university.model.view;

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
    private String userName;
    private String passwordHash;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String email;
    private String groupName;
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

    static public StudentView studentToStudentView(Student student, Group group, Specialism specialism, AcademicYear academicYear) {
        return new StudentView(student.getId(), student.getUserName(), student.getPasswordHash(), student.getUserRole(),
                student.getFirstName(), student.getLastName(), student.getBirthday(), student.getEmail(),
                group.getGroupName(), specialism.getSpecialismName(), academicYear.getYearNumber(),
                academicYear.getSemesterType());
    }

    // Don't forget to hash password while convert studentView to student
    public Student studentViewToStudent(Group group, Specialism specialism, AcademicYear academicYear) {
        return new Student(id, userName, passwordHash, userRole, firstName, lastName, birthday, email, group.getId(), specialism.getId(), academicYear.getId());
    }
}
