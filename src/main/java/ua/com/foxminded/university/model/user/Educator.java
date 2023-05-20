package ua.com.foxminded.university.model.user;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.lesson.Discipline;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("3")
public class Educator extends User {
    @Column(name = "academic_rank")
    private AcademicRank academicRank;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "educator")
    private Discipline discipline;

    public Educator(Long id, String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, AcademicRank academicRank) {
        super(id, userName, passwordHash, userRole, firstName, lastName, birthday, email);
        this.academicRank = academicRank;
    }

    public Educator(String userName, String passwordHash, UserRole userRole, String firstName, String lastName, LocalDate birthday, String email, AcademicRank academicRank) {
        this(null, userName, passwordHash, userRole, firstName, lastName, birthday, email, academicRank);
    }
}