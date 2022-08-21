package ua.com.foxminded.university.model.view;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String email;
    private Integer scheduleWeek;
    private UserRole userRole;
    private String groupName;
    private String specialism;

    public StudentView(Long id, String firstName, String lastName, LocalDate birthday, String email, Integer scheduleWeek, UserRole userRole, String groupName, String specialism) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.scheduleWeek = scheduleWeek;
        this.userRole = userRole;
        this.groupName = groupName;
        this.specialism = specialism;
    }

    static public StudentView studentToStudentView(Student student, Integer scheduleWeek, String specialism) {
        return new StudentView(student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday(), student.getEmail(), scheduleWeek, student.getUserRole(), student.getGroupName(), specialism);
    }

    public Student studentViewToStudent(Long weeklyScheduleId, Long specialismId) {
        return new Student(id, firstName, lastName, birthday, email, weeklyScheduleId, userRole, groupName, specialismId);
    }
}
