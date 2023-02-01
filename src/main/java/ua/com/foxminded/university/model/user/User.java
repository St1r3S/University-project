package ua.com.foxminded.university.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.university.model.LongEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class User extends LongEntity {
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @Column(name = "email", unique = true, nullable = false)
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
