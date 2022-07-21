package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcDisciplineDao;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.service.DisciplineService;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcDisciplineDao disciplineDao;

    public DisciplineServiceImpl(JdbcDisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findById(Long id) {
        return disciplineDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such daily schedule with id " + id, 1));
    }

    @Override
    @Transactional
    public Discipline save(Discipline entity) {
        try {
            return disciplineDao.save(entity);
        } catch (EmptyResultDataAccessException ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
        }
        throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            disciplineDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void deleteById(Discipline entity) {
        try {
            disciplineDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
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
    public Discipline findByDisciplineName(String disciplineName) {
        return disciplineDao.findByDisciplineName(disciplineName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such discipline with name " + disciplineName, 1));
    }

    @Override
    @Transactional
    public void enrollDisciplineSpecialism(Discipline discipline, Specialism specialism) {
        disciplineDao.enrollDisciplineSpecialism(discipline.getId(), specialism.getId());
    }

    @Override
    @Transactional
    public void expelDisciplineSpecialism(Discipline discipline, Specialism specialism) {
        disciplineDao.expelDisciplineSpecialism(discipline.getId(), specialism.getId());
    }
}
