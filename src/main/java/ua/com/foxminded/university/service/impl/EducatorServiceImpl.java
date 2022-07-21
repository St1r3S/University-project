package ua.com.foxminded.university.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcEducatorDao;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.service.EducatorService;

import java.util.List;

@Service
public class EducatorServiceImpl implements EducatorService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcEducatorDao educatorDao;

    public EducatorServiceImpl(JdbcEducatorDao educatorDao) {
        this.educatorDao = educatorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Educator findById(Long id) {
        return educatorDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with id " + id, 1));
    }

    @Override
    @Transactional
    public Educator save(Educator entity) {
        try {
            return educatorDao.save(entity);
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
            educatorDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void deleteById(Educator entity) {
        try {
            educatorDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAll() {
        return educatorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllBySpecialismId(Long specialismId) {
        return educatorDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllBySpecialismId(Specialism specialism) {
        return educatorDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    @Transactional
    public void enrollEducatorSpecialism(Educator educator, Specialism specialism) {
        educatorDao.enrollEducatorSpecialism(educator.getId(), specialism.getId());
    }

    @Override
    @Transactional
    public void expelEducatorSpecialism(Educator educator, Specialism specialism) {
        educatorDao.expelEducatorSpecialism(educator.getId(), specialism.getId());
    }
}
