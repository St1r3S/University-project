package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.user.Group;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "specialisms")
public class Specialism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "specialism_name", unique = true, nullable = false)
    @NotEmpty(message = "{NotEmpty.Entity.Field}")
    private String specialismName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "specialism")
    private List<Group> groups;


    public Specialism(Long id, String specialismName) {
        this.id = id;
        this.specialismName = specialismName;
    }

    public Specialism(String specialismName) {
        this(null, specialismName);
    }
}

