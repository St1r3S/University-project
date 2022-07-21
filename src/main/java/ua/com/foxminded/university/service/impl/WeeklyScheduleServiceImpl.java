package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcWeeklyScheduleDao;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class WeeklyScheduleServiceImpl implements CrudService<WeeklySchedule, Long> {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcWeeklyScheduleDao weeklyScheduleDao;

    public WeeklyScheduleServiceImpl(JdbcWeeklyScheduleDao weeklyScheduleDao) {
        this.weeklyScheduleDao = weeklyScheduleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public WeeklySchedule findById(Long id) {
        return weeklyScheduleDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such weekly schedule with id " + id, 1));
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
    public void deleteById(WeeklySchedule entity) {
        try {
            weeklyScheduleDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleDao.findAll();
    }
}
