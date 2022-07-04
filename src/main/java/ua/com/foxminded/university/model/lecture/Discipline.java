package ua.com.foxminded.university.model.lecture;

import ua.com.foxminded.university.model.LongEntity;
import ua.com.foxminded.university.model.user.Educator;

public class Discipline extends LongEntity {
    public static final String DISCIPLINE_ID = "id";
    public static final String DISCIPLINE_NAME = "discipline_name";
    public static final String DISCIPLINE_EDUCATOR_ID = "educator_id";
    private String name;
    private Educator educator;

    public Discipline(Long id, String name, Educator educator) {
        super(id);
        this.name = name;
        this.educator = educator;
    }

    public Discipline(String name, Educator educator) {
        this(null, name, educator);
    }
}
