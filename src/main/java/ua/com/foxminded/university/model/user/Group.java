package ua.com.foxminded.university.model.user;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Group extends LongEntity {

    private String groupName;
    private Long specialismId;
    private Long academicYearId;

    public Group(Long id, String groupName, Long specialismId, Long academicYearId) {
        super(id);
        this.groupName = groupName;
        this.specialismId = specialismId;
        this.academicYearId = academicYearId;
    }

    public Group(String groupName, Long specialismId, Long academicYearId) {
        this(null, groupName, specialismId, academicYearId);
    }
}
