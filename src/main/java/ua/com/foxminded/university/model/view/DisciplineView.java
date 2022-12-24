package ua.com.foxminded.university.model.view;

import lombok.*;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Educator;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DisciplineView {
    private Long id;
    private String disciplineName;
    private String specialismName;
    private Integer academicYearNumber;
    private SemesterType semesterType;
    private Educator educator;

    public DisciplineView(Long id, String disciplineName, String specialismName, Integer academicYearNumber, SemesterType semesterType, Educator educator) {
        this.id = id;
        this.disciplineName = disciplineName;
        this.specialismName = specialismName;
        this.academicYearNumber = academicYearNumber;
        this.semesterType = semesterType;
        this.educator = educator;
    }

    static public DisciplineView disciplineToDisciplineView(Discipline discipline, Specialism specialism, AcademicYear academicYear, Educator educator) {
        return new DisciplineView(discipline.getId(), discipline.getDisciplineName(), specialism.getSpecialismName(), academicYear.getYearNumber(), academicYear.getSemesterType(), educator);
    }

    public Discipline disciplineViewToDiscipline(Specialism specialism, AcademicYear academicYear, Educator educator) {
        return new Discipline(id, disciplineName, specialism.getId(), academicYear.getId(), educator.getId());
    }
}
