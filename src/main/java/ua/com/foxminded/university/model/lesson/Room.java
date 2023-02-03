package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxminded.university.model.LongEntity;

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
@Table(name = "rooms")
public class Room extends LongEntity {
    @Column(name = "room_number", unique = true, nullable = false)
    private String roomNumber;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "room")
    private List<Lesson> lessons;

    public Room(Long id, String roomNumber) {
        super(id);
        this.roomNumber = roomNumber;
    }

    public Room(String roomNumber) {
        this(null, roomNumber);
    }
}
