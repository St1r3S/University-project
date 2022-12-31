package ua.com.foxminded.university.model.user;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.university.model.LongEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends LongEntity {

    private String userName;
    private String passwordHash;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String email;

    public User(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email) {
        super(id);
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
    }

    public User(String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email) {
        this(null, userName, passwordHash, userRole, firstName, lastName, birthday, email);
    }
}
