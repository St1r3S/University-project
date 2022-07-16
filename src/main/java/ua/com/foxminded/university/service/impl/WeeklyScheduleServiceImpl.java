package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public WeeklySchedule findById(Long id) throws NotFoundException {
        return weeklyScheduleDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such weekly schedule!"));
    }

    @Override
    @Transactional
    public WeeklySchedule save(WeeklySchedule entity) {
        return weeklyScheduleDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return weeklyScheduleDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(WeeklySchedule entity) {
        return weeklyScheduleDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleDao.findAll();
    }
}
