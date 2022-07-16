package ua.com.foxminded.university.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcWeeklyScheduleDao;
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
    public WeeklySchedule findById(Long id) {
        return weeklyScheduleDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such weekly schedule with id " + id, 1));
    }

    @Override
    @Transactional
    public WeeklySchedule save(WeeklySchedule entity) {
        return weeklyScheduleDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        weeklyScheduleDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteById(WeeklySchedule entity) {
        weeklyScheduleDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySchedule> findAll() {
        return weeklyScheduleDao.findAll();
    }
}
