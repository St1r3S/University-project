package ua.com.foxminded.university.model.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
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
