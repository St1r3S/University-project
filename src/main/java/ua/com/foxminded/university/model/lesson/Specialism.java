package ua.com.foxminded.university.model.lesson;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Specialism extends LongEntity {

    private String specialismName;

    public Specialism(Long id, String specialismName) {
        super(id);
        this.specialismName = specialismName;
    }

    public Specialism(String specialismName) {
        this(null, specialismName);
    }
}
