package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.DisciplineDao;
import ua.com.foxminded.university.model.lecture.Discipline;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.service.DisciplineService;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final DisciplineDao disciplineDao;

    public DisciplineServiceImpl(DisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findById(Long id) {
        return disciplineDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such daily schedule with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return disciplineDao.existsById(id);
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
    @Transactional(readOnly = true)
    public List<Discipline> saveAll(List<Discipline> entities) {
        try {
            return disciplineDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
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
    @Transactional(readOnly = true)
    public void delete(Discipline entity) {
        try {
            disciplineDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAllById(List<Long> ids) {
        try {
            disciplineDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAll(List<Discipline> entities) {
        try {
            disciplineDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAll() {
        try {
            disciplineDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAll() {
        return disciplineDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllById(List<Long> ids) {
        return disciplineDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return disciplineDao.count();
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
