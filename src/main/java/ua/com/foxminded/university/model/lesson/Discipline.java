package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.user.Educator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "discipline_name", nullable = false)
    private String disciplineName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialism_id", nullable = false)
    private Specialism specialism;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;
    @OneToOne
    @JoinColumn(name = "educator_id", nullable = false, referencedColumnName = "id")
    private Educator educator;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "discipline")
    private List<Lesson> lessons;

    public Discipline(Long id, String disciplineName, Specialism specialism, AcademicYear academicYear, Educator educator) {
        this.id = id;
        this.disciplineName = disciplineName;
        this.specialism = specialism;
        this.academicYear = academicYear;
        this.educator = educator;
    }

    public Discipline(String disciplineName, Specialism specialism, AcademicYear academicYear, Educator educator) {
        this(null, disciplineName, specialism, academicYear, educator);
    }
}



