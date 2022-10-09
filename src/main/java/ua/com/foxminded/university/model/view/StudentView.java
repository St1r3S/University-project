package ua.com.foxminded.university.model.view;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
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
    private String login;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String email;
    private String groupName;
    private String specialism;
    private Integer academicYear;
    private String semester;


    public StudentView(Long id, String login, String password, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, String groupName, String specialism, Integer academicYear, String semester) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.groupName = groupName;
        this.specialism = specialism;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    static public StudentView studentToStudentView(Student student, Group group, Specialism specialism, AcademicYear academicYear) {
        return new StudentView(student.getId(), student.getUserName(), student.getPasswordHash(), student.getUserRole(),
                student.getFirstName(), student.getLastName(), student.getBirthday(), student.getEmail(),
                group.getGroupName(), specialism.getSpecialismName(), academicYear.getYearNumber(),
                academicYear.getSemesterType().getValue());
    }

    public Student studentViewToStudent(Group group, Specialism specialism, AcademicYear academicYear) {
        return new Student(id, login, password, userRole, firstName, lastName, birthday, email, group.getId(), specialism.getId(), academicYear.getId());
    }
}
