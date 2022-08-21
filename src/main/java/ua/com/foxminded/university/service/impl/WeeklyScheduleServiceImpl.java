package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.WeeklyScheduleDao;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;
import ua.com.foxminded.university.service.WeeklyScheduleService;

import java.util.List;

@Service
public class WeeklyScheduleServiceImpl implements WeeklyScheduleService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final WeeklyScheduleDao weeklyScheduleDao;

    public WeeklyScheduleServiceImpl(WeeklyScheduleDao weeklyScheduleDao) {
        this.weeklyScheduleDao = weeklyScheduleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public WeeklySchedule findById(Long id) {
        return weeklyScheduleDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such weekly schedule with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return weeklyScheduleDao.existsById(id);
    }

    @Override
    @Transactional
    public WeeklySchedule save(WeeklySchedule entity) {
        try {
            return weeklyScheduleDao.save(entity);
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
    public List<WeeklySchedule> saveAll(List<WeeklySchedule> entities) {
        try {
            return weeklyScheduleDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            weeklyScheduleDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(WeeklySchedule entity) {
        try {
            weeklyScheduleDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            weeklyScheduleDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<WeeklySchedule> entities) {
        try {
            weeklyScheduleDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            weeklyScheduleDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySchedule> findAllById(List<Long> ids) {
        return weeklyScheduleDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return weeklyScheduleDao.count();
    }

    @Override
    public WeeklySchedule findByWeekNumber(Integer weekNumber) {
        return weeklyScheduleDao.findByWeekNumber(weekNumber).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such weekly schedule with week number " + weekNumber, 1));
    }
}
