package ua.com.foxminded.university.model.schedule;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AcademicYear extends LongEntity {

    private Integer yearNumber;
    private SemesterType semesterType;

    public AcademicYear(Long id, Integer yearNumber, SemesterType semesterType) {
        super(id);
        this.yearNumber = yearNumber;
        this.semesterType = semesterType;
    }

    public AcademicYear(Integer yearNumber, SemesterType semesterType) {
        this(null, yearNumber, semesterType);
    }
}
