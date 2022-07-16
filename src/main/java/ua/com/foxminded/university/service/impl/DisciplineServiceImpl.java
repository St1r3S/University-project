package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public Discipline findById(Long id) throws NotFoundException {
        return disciplineDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such daily schedule"));
    }

    @Override
    @Transactional
    public Discipline save(Discipline entity) {
        return disciplineDao.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        disciplineDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteById(Discipline entity) throws NotFoundException {
        disciplineDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAll() {
        return disciplineDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllBySpecialismId(Long specialismId) {
        return disciplineDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllBySpecialismId(Specialism specialism) {
        return disciplineDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findByDisciplineName(String disciplineName) throws NotFoundException {
        return disciplineDao.findByDisciplineName(disciplineName).orElseThrow(() -> new NotFoundException("There's no such discipline!"));
    }

    @Override
    @Transactional
    public void enrollDisciplineSpecialism(Discipline discipline, Specialism specialism) throws NotFoundException {
        disciplineDao.enrollDisciplineSpecialism(discipline.getId(), specialism.getId());
    }

    @Override
    @Transactional
    public void expelDisciplineSpecialism(Discipline discipline, Specialism specialism) throws NotFoundException {
        disciplineDao.expelDisciplineSpecialism(discipline.getId(), specialism.getId());
    }
}
