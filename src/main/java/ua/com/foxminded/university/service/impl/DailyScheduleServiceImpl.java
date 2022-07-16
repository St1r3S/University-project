package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
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
    public DailySchedule findById(Long id) throws NotFoundException {
        return dailyScheduleDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such daily schedule!"));
    }

    @Override
    public DailySchedule save(DailySchedule entity) {
        return dailyScheduleDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return dailyScheduleDao.deleteById(id);
    }

    @Override
    public int deleteById(DailySchedule entity) {
        return dailyScheduleDao.deleteById(entity.getId());
    }

    @Override
    public List<DailySchedule> findAll() {
        return dailyScheduleDao.findAll();
    }
}
