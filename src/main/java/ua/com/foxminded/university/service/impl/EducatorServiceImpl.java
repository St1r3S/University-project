package ua.com.foxminded.university.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.EducatorService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EducatorServiceImpl implements EducatorService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final EducatorDao educatorDao;

    public EducatorServiceImpl(EducatorDao educatorDao) {
        this.educatorDao = educatorDao;
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
    public List<Educator> saveAll(List<Educator> entities) {
        try {
            return educatorDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public Educator findById(Long id) {
        return educatorDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return educatorDao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAll() {
        return educatorDao.findAll(100);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllById(List<Long> ids) {
        return educatorDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return educatorDao.count();
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
    public void delete(Educator entity) {
        try {
            educatorDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            educatorDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<Educator> entities) {
        try {
            educatorDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            educatorDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    public Educator findByLogin(String userName) {
        return educatorDao.findByLogin(userName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with login " + userName, 1));
    }

    @Override
    public Educator findByEmail(String email) {
        return educatorDao.findByLogin(email).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with email " + email, 1));
    }

    @Override
    public List<Educator> findAllByUserRole(UserRole userRole) {
        return educatorDao.findAllByUserRole(userRole);
    }

    @Override
    public List<Educator> findAllByBirthday(LocalDate birthday) {
        return educatorDao.findAllByBirthday(birthday);
    }

    @Override
    public List<Educator> findAllByAcademicRank(AcademicRank academicRank) {
        return educatorDao.findAllByAcademicRank(academicRank);
    }
}
