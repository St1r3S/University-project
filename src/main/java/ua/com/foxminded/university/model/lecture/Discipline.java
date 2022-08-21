package ua.com.foxminded.university.model.lecture;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Discipline extends LongEntity {

    private String disciplineName;
    private Long educatorId;

    public Discipline(Long id, String disciplineName, Long educatorId) {
        super(id);
        this.disciplineName = disciplineName;
        this.educatorId = educatorId;
    }

    public Discipline(String disciplineName, Long educatorId) {
        this(null, disciplineName, educatorId);
    }
}
