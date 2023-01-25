package ua.com.foxminded.university.model.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.user.Group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "academic_years")
public class AcademicYear extends LongEntity {

    @Column(name = "year_number", nullable = false)
    private Integer yearNumber;
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @OneToMany(mappedBy = "academicYear")
    private List<Group> groups;

    public AcademicYear(Long id, Integer yearNumber, SemesterType semesterType) {
        super(id);
        this.yearNumber = yearNumber;
        this.semesterType = semesterType;
    }

    public AcademicYear(Integer yearNumber, SemesterType semesterType) {
        this(null, yearNumber, semesterType);
    }

}
