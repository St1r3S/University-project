package ua.com.foxminded.university.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "groupss")
public class Group extends LongEntity {

    @Column(name = "group_name", unique = true, nullable = false)
    private String groupName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialism_id", nullable = false)
    private Specialism specialism;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group")
    private List<Student> students;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;

    public Group(Long id, String groupName, Specialism specialism, AcademicYear academicYear) {
        super(id);
        this.groupName = groupName;
        this.specialism = specialism;
        this.academicYear = academicYear;
    }

    public Group(String groupName, Specialism specialism, AcademicYear academicYear) {
        this.groupName = groupName;
        this.specialism = specialism;
        this.academicYear = academicYear;
    }
}
