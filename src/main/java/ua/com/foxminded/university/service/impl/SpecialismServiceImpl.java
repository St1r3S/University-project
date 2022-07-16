package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.jdbc.JdbcSpecialismDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.List;

@Service
public class SpecialismServiceImpl implements SpecialismService {
    private final JdbcSpecialismDao specialismDao;

    public SpecialismServiceImpl(JdbcSpecialismDao specialismDao) {
        this.specialismDao = specialismDao;
    }

    @Override
    public Specialism findById(Long id) throws NotFoundException {
        return specialismDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such specialism!"));
    }

    @Override
    public Specialism save(Specialism entity) {
        return specialismDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return specialismDao.deleteById(id);
    }

    @Override
    public int deleteById(Specialism entity) {
        return specialismDao.deleteById(entity.getId());
    }

    @Override
    public List<Specialism> findAll() {
        return specialismDao.findAll();
    }

    @Override
    public List<Specialism> findAllByDisciplineId(Long disciplineId) {
        return specialismDao.findAllByDisciplineId(disciplineId);
    }

    @Override
    public List<Specialism> findAllByDisciplineId(Discipline discipline) {
        return specialismDao.findAllByDisciplineId(discipline.getId());
    }

    @Override
    public List<Specialism> findAllByEducatorId(Long educatorId) {
        return specialismDao.findAllByEducatorId(educatorId);
    }

    @Override
    public List<Specialism> findAllByEducatorId(Educator educator) {
        return specialismDao.findAllByEducatorId(educator.getId());
    }

    @Override
    public Specialism findBySpecialismName(String specialismName) throws NotFoundException {
        return specialismDao.findBySpecialismName(specialismName).orElseThrow(() -> new NotFoundException("There's no such specialism!"));
    }
}
