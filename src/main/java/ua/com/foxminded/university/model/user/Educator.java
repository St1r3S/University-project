package ua.com.foxminded.university.model.user;


import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Educator extends User {

    private AcademicRank academicRank;

    public Educator(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, AcademicRank academicRank) {
        super(id, userName, passwordHash, userRole, firstName, lastName, birthday, email);
        this.academicRank = academicRank;
    }

    public Educator(String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, AcademicRank academicRank) {
        this(null, userName, passwordHash, userRole, firstName, lastName, birthday, email, academicRank);
    }
}