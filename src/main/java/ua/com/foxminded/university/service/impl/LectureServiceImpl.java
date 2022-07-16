package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcLectureDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.service.LectureService;

import java.time.LocalDate;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {
    private final JdbcLectureDao lectureDao;

    public LectureServiceImpl(JdbcLectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Lecture findById(Long id) throws NotFoundException {
        return lectureDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such lecture!"));
    }

    @Override
    @Transactional
    public Lecture save(Lecture entity) {
        return lectureDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return lectureDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(Lecture entity) {
        return lectureDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAll() {
        return lectureDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByStudentId(Long studentId) {
        return lectureDao.findAllByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByStudentId(Student student) {
        return lectureDao.findAllByStudentId(student.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByWeekNumber(Integer weekNumber) {
        return lectureDao.findAllByWeekNumber(weekNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByDayOfWeekAndWeekNumber(DayOfWeek dayOfWeek, Integer weekNumber) {
        return lectureDao.findAllByDayOfWeekAndWeekNumber(dayOfWeek, weekNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByDate(LocalDate date) {
        return lectureDao.findAllByDate(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllByRoomNumber(String roomNumber) {
        return lectureDao.findAllByRoomNumber(roomNumber);
    }
}
