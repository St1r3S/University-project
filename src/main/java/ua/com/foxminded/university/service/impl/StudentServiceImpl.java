package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such student with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return studentDao.existsById(id);
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        try {
            return studentDao.save(entity);
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
    public List<Student> saveAll(List<Student> entities) {
        try {
            return studentDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            studentDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(Student entity) {
        try {
            studentDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            studentDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<Student> entities) {
        try {
            studentDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            studentDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllById(List<Long> ids) {
        return studentDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return studentDao.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByLectureId(Long lectureId) {
        return studentDao.findAllByLectureId(lectureId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByLectureId(Lecture lecture) {
        return studentDao.findAllByLectureId(lecture.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return studentDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllBySpecialismId(Specialism specialism) {
        return studentDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByGroupName(String groupName) {
        return studentDao.findAllByGroupName(groupName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return studentDao.findAllByBirthday(birthday);
    }

    @Override
    @Transactional
    public void enrollLectureStudent(Lecture lecture, Student student) {
        studentDao.enrollLectureStudent(lecture.getId(), student.getId());
    }

    @Override
    @Transactional
    public void expelLectureStudent(Lecture lecture, Student student) {
        studentDao.expelLectureStudent(lecture.getId(), student.getId());
    }
}
