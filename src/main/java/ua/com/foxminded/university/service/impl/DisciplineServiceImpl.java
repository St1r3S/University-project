package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.jdbc.JdbcDisciplineDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.service.DisciplineService;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private final JdbcDisciplineDao disciplineDao;

    public DisciplineServiceImpl(JdbcDisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
    }

    @Override
    public Discipline findById(Long id) throws NotFoundException {
        return disciplineDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such daily schedule"));
    }

    @Override
    public Discipline save(Discipline entity) {
        return disciplineDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return disciplineDao.deleteById(id);
    }

    @Override
    public int deleteById(Discipline entity) {
        return disciplineDao.deleteById(entity.getId());
    }

    @Override
    public List<Discipline> findAll() {
        return disciplineDao.findAll();
    }

    @Override
    public List<Discipline> findAllBySpecialismId(Long specialismId) {
        return disciplineDao.findAllBySpecialismId(specialismId);
    }

    @Override
    public List<Discipline> findAllBySpecialismId(Specialism specialism) {
        return disciplineDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    public Discipline findByDisciplineName(String disciplineName) throws NotFoundException {
        return disciplineDao.findByDisciplineName(disciplineName).orElseThrow(() -> new NotFoundException("There's no such discipline!"));
    }

    @Override
    public int enrollDisciplineSpecialism(Discipline discipline, Specialism specialism) {
        return disciplineDao.enrollDisciplineSpecialism(discipline.getId(), specialism.getId());
    }

    @Override
    public int expelDisciplineSpecialism(Discipline discipline, Specialism specialism) {
        return disciplineDao.expelDisciplineSpecialism(discipline.getId(), specialism.getId());
    }
}
