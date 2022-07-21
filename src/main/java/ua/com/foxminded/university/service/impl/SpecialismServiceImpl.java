package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcSpecialismDao;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.List;

@Service
public class SpecialismServiceImpl implements SpecialismService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcSpecialismDao specialismDao;

    public SpecialismServiceImpl(JdbcSpecialismDao specialismDao) {
        this.specialismDao = specialismDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Specialism findById(Long id) {
        return specialismDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such specialism with id " + id, 1));
    }

    @Override
    @Transactional
    public Specialism save(Specialism entity) {
        try {
            return specialismDao.save(entity);
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
            specialismDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void deleteById(Specialism entity) {
        try {
            specialismDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAll() {
        return specialismDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAllByDisciplineId(Long disciplineId) {
        return specialismDao.findAllByDisciplineId(disciplineId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAllByDisciplineId(Discipline discipline) {
        return specialismDao.findAllByDisciplineId(discipline.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAllByEducatorId(Long educatorId) {
        return specialismDao.findAllByEducatorId(educatorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAllByEducatorId(Educator educator) {
        return specialismDao.findAllByEducatorId(educator.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Specialism findBySpecialismName(String specialismName) {
        return specialismDao.findBySpecialismName(specialismName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such specialism with name " + specialismName, 1));
    }
}
