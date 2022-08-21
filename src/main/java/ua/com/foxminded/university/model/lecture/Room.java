package ua.com.foxminded.university.model.lecture;

import lombok.*;
import ua.com.foxminded.university.model.LongEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Room extends LongEntity {

    private String roomNumber;

    public Room(Long id, String roomNumber) {
        super(id);
        this.roomNumber = roomNumber;
    }

    public Room(String roomNumber) {
        this(null, roomNumber);
    }
}
