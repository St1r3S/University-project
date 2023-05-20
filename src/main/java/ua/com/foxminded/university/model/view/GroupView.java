package ua.com.foxminded.university.model.view;

import lombok.*;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;

import jakarta.validation.constraints.NotEmpty;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GroupView {
    private Long id;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    private String groupName;
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    private String specialismName;
    private Integer academicYearNumber;
    private SemesterType semesterType;

    public GroupView(Long id, String groupName, String specialismName, Integer academicYearNumber, SemesterType semesterType) {
        this.id = id;
        this.groupName = groupName;
        this.specialismName = specialismName;
        this.academicYearNumber = academicYearNumber;
        this.semesterType = semesterType;
    }

    static public GroupView groupToGroupView(Group group, Specialism specialism, AcademicYear academicYear) {
        return new GroupView(group.getId(), group.getGroupName(), specialism.getSpecialismName(), academicYear.getYearNumber(), academicYear.getSemesterType());
    }

    public Group groupViewToGroup(Specialism specialism, AcademicYear academicYear) {
        return new Group(id, groupName, specialism, academicYear);
    }
}
