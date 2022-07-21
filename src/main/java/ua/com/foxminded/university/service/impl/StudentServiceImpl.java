package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcStudentDao;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcStudentDao studentDao;

    public StudentServiceImpl(JdbcStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such student with id " + id, 1));
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
    public void deleteById(Student entity) {
        try {
            studentDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentDao.findAll();
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
