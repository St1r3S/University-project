package ua.com.foxminded.university.model.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.user.Group;

import jakarta.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "academic_years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "year_number", nullable = false)
    private Integer yearNumber;
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "academicYear")
    private List<Group> groups;

    public AcademicYear(Long id, Integer yearNumber, SemesterType semesterType) {
        this.id = id;
        this.yearNumber = yearNumber;
        this.semesterType = semesterType;
    }

    public AcademicYear(Integer yearNumber, SemesterType semesterType) {
        this(null, yearNumber, semesterType);
    }

}
