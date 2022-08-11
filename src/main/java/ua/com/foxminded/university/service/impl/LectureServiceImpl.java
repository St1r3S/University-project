package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.model.lecture.DayOfWeek;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.service.LectureService;

import java.time.LocalDate;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final LectureDao lectureDao;

    public LectureServiceImpl(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Lecture findById(Long id) {
        return lectureDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such lecture with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return lectureDao.existsById(id);
    }

    @Override
    @Transactional
    public Lecture save(Lecture entity) {
        try {
            return lectureDao.save(entity);
        } catch (EmptyResultDataAccessException ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
        }
        throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
    }

    @Override
    @Transactional
    public List<Lecture> saveAll(List<Lecture> entities) {
        try {
            return lectureDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            lectureDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(Lecture entity) {
        try {
            lectureDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            lectureDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<Lecture> entities) {
        try {
            lectureDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            lectureDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAll() {
        return lectureDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecture> findAllById(List<Long> ids) {
        return lectureDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return lectureDao.count();
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
