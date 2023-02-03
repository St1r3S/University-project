package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.repository.ScheduleDayRepository;
import ua.com.foxminded.university.service.ScheduleDayService;

import java.util.List;

@Service
public class ScheduleDayServiceImpl implements ScheduleDayService {

    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final ScheduleDayRepository scheduleDayRepository;

    public ScheduleDayServiceImpl(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    @Transactional
    public ScheduleDay save(ScheduleDay entity) {
        try {
            return scheduleDayRepository.save(entity);
        } catch (Exception ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
            throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public List<ScheduleDay> saveAll(List<ScheduleDay> entities) {
        try {
            return scheduleDayRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDay findById(Long id) {
        return scheduleDayRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such schedule day with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return scheduleDayRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findAll() {
        return scheduleDayRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findAllById(List<Long> ids) {
        return scheduleDayRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return scheduleDayRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            scheduleDayRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(ScheduleDay entity) {
        try {
            scheduleDayRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            scheduleDayRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<ScheduleDay> entities) {
        try {
            scheduleDayRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            scheduleDayRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek) {
        return scheduleDayRepository.findByDayOfWeek(dayOfWeek);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findBySemesterType(SemesterType semesterType) {
        return scheduleDayRepository.findBySemesterType(semesterType);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDay findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType) {
        return scheduleDayRepository.findByDayOfWeekAndSemesterType(dayOfWeek, semesterType).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such schedule day with day of week " + dayOfWeek
                        + " and semester type " + semesterType, 1));
    }
}
