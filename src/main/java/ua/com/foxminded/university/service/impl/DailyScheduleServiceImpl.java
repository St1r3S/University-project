package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcDailyScheduleDao;
import ua.com.foxminded.university.exception.NotFoundException;
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
    public DailySchedule findById(Long id) throws NotFoundException {
        return dailyScheduleDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such daily schedule!"));
    }

    @Override
    @Transactional
    public DailySchedule save(DailySchedule entity) {
        return dailyScheduleDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return dailyScheduleDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(DailySchedule entity) {
        return dailyScheduleDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailySchedule> findAll() {
        return dailyScheduleDao.findAll();
    }
}
