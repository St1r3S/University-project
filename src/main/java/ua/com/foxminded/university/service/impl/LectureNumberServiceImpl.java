package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcLectureNumberDao;
import ua.com.foxminded.university.model.lecture.LectureNumber;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class LectureNumberServiceImpl implements CrudService<LectureNumber, Long> {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcLectureNumberDao lectureNumberDao;

    public LectureNumberServiceImpl(JdbcLectureNumberDao lectureNumberDao) {
        this.lectureNumberDao = lectureNumberDao;
    }

    @Override
    @Transactional(readOnly = true)
    public LectureNumber findById(Long id) {
        return lectureNumberDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such lecture number with id " + id, 1));
    }

    @Override
    @Transactional
    public LectureNumber save(LectureNumber entity) {
        try {
            return lectureNumberDao.save(entity);
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
            lectureNumberDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void deleteById(LectureNumber entity) {
        try {
            lectureNumberDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureNumber> findAll() {
        return lectureNumberDao.findAll();
    }

}
