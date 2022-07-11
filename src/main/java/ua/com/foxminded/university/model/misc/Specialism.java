package ua.com.foxminded.university.model.misc;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
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
