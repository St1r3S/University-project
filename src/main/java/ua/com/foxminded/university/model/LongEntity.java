package ua.com.foxminded.university.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@MappedSuperclass
@Data
abstract public class LongEntity implements Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    public LongEntity(Long id) {
        this.id = id;
    }

}