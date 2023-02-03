package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.user.Group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "specialisms")
public class Specialism extends LongEntity {
    @Column(name = "specialism_name", unique = true, nullable = false)
    private String specialismName;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "specialism")
    private List<Group> groups;


    public Specialism(Long id, String specialismName) {
        super(id);
        this.specialismName = specialismName;
    }

    public Specialism(String specialismName) {
        this(null, specialismName);
    }
}

