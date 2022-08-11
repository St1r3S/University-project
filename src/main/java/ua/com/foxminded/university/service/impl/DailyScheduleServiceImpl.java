package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.DailyScheduleDao;
import ua.com.foxminded.university.model.schedule.DailySchedule;
import ua.com.foxminded.university.service.DailyScheduleService;

import java.util.List;

@Service
public class DailyScheduleServiceImpl implements DailyScheduleService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final DailyScheduleDao dailyScheduleDao;

    public DailyScheduleServiceImpl(DailyScheduleDao dailyScheduleDao) {
        this.dailyScheduleDao = dailyScheduleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public DailySchedule findById(Long id) {
        return dailyScheduleDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such daily schedule with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return dailyScheduleDao.existsById(id);
    }

    @Override
    @Transactional
    public DailySchedule save(DailySchedule entity) {
        try {
            return dailyScheduleDao.save(entity);
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
    public List<DailySchedule> saveAll(List<DailySchedule> entities) {
        try {
            return dailyScheduleDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            dailyScheduleDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(DailySchedule entity) {
        try {
            dailyScheduleDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            dailyScheduleDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<DailySchedule> entities) {
        try {
            dailyScheduleDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            dailyScheduleDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySchedule> findAll() {
        return dailyScheduleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySchedule> findAllById(List<Long> ids) {
        return dailyScheduleDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return dailyScheduleDao.count();
    }
}
