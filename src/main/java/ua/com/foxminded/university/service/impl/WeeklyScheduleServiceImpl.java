package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.jdbc.JdbcWeeklyScheduleDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class WeeklyScheduleServiceImpl implements CrudService<WeeklySchedule, Long> {
    private final JdbcWeeklyScheduleDao weeklyScheduleDao;

    public WeeklyScheduleServiceImpl(JdbcWeeklyScheduleDao weeklyScheduleDao) {
        this.weeklyScheduleDao = weeklyScheduleDao;
    }

    @Override
    public WeeklySchedule findById(Long id) throws NotFoundException {
        return weeklyScheduleDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such weekly schedule!"));
    }

    @Override
    public WeeklySchedule save(WeeklySchedule entity) {
        return weeklyScheduleDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return weeklyScheduleDao.deleteById(id);
    }

    @Override
    public int deleteById(WeeklySchedule entity) {
        return weeklyScheduleDao.deleteById(entity.getId());
    }

    @Override
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleDao.findAll();
    }
}
