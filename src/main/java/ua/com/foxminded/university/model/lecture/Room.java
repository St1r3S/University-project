package ua.com.foxminded.university.model.lecture;

import ua.com.foxminded.university.model.LongEntity;

public class Room extends LongEntity {
    public static final String ROOM_ID = "id";
    public static final String ROOM_NUMBER = "number";
    private String number;

    public Room(Long id, String number) {
        super(id);
        this.number = number;
    }

    public Room(String number) {
        this(null, number);
    }
}
