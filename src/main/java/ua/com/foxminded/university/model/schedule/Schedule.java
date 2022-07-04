package ua.com.foxminded.university.model.schedule;

import ua.com.foxminded.university.model.lecture.Lecture;

import java.util.List;

public interface Schedule {
    List<Lecture> getSchedule();
}
