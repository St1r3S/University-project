package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.user.Educator;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "disciplines")
public class Discipline extends LongEntity {

    @Column(name = "discipline_name", nullable = false)
    private String disciplineName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialism_id", nullable = false)
    private Specialism specialism;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialism_id", nullable = false)
    private AcademicYear academicYear;
    @OneToOne
    @JoinColumn(name = "educator_id", nullable = false, referencedColumnName = "id")
    private Educator educator;
    
    @OneToMany(mappedBy = "discipline")
    private List<Lesson> lessons;

    public Discipline(Long id, String disciplineName, Specialism specialism, AcademicYear academicYear, Educator educator) {
        super(id);
        this.disciplineName = disciplineName;
        this.specialism = specialism;
        this.academicYear = academicYear;
        this.educator = educator;
    }

    public Discipline(String disciplineName, Specialism specialism, AcademicYear academicYear, Educator educator) {
        this(null, disciplineName, specialism, academicYear, educator);
    }
}



