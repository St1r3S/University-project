package ua.com.foxminded.university.model.lesson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "room_number", unique = true, nullable = false)
    private String roomNumber;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "room")
    private List<Lesson> lessons;

    public Room(Long id, String roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public Room(String roomNumber) {
        this(null, roomNumber);
    }
}
