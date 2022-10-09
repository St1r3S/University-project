package ua.com.foxminded.university.model.lesson;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Discipline extends LongEntity {

    private String disciplineName;
    private Long specialismId;
    private Long academicYearId;
    private Long educatorId;

    public Discipline(Long id, String disciplineName, Long specialismId, Long academicYearId, Long educatorId) {
        super(id);
        this.disciplineName = disciplineName;
        this.specialismId = specialismId;
        this.academicYearId = academicYearId;
        this.educatorId = educatorId;
    }

    public Discipline(String disciplineName, Long specialismId, Long academicYearId, Long educatorId) {
        this(null, disciplineName, specialismId, academicYearId, educatorId);
    }
}
