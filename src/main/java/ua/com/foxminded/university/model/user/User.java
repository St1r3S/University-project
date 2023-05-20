package ua.com.foxminded.university.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "user_type",
        discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name", unique = true, nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 5, message = "{Size.User.Username}")
    private String userName;
    @Column(name = "password_hash", nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 5, message = "{Size.User.Password}")
    private String passwordHash;
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 3, message = "{Size.User.FirstName}")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Size(min = 3, message = "{Size.User.LastName}")
    private String lastName;
    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "{Past.User.Birthday}")
    private LocalDate birthday;
    @Column(name = "email", unique = true, nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    @Email(message = "{Pattern.User.Email}")
    private String email;

    public User(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email) {
        this.id = id;
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
