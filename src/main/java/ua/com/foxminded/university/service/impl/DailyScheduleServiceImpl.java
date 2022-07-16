package ua.com.foxminded.university.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcDailyScheduleDao;
import ua.com.foxminded.university.model.schedule.DailySchedule;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class DailyScheduleServiceImpl implements CrudService<DailySchedule, Long> {
    private final JdbcDailyScheduleDao dailyScheduleDao;

    public DailyScheduleServiceImpl(JdbcDailyScheduleDao dailyScheduleDao) {
        this.dailyScheduleDao = dailyScheduleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public DailySchedule findById(Long id) {
        return dailyScheduleDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such daily schedule with id " + id, 1));
    }

    @Override
    @Transactional
    public DailySchedule save(DailySchedule entity) {
        return dailyScheduleDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        dailyScheduleDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteById(DailySchedule entity) {
        dailyScheduleDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySchedule> findAll() {
        return dailyScheduleDao.findAll();
    }
}
