package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.repository.DisciplineRepository;
import ua.com.foxminded.university.service.DisciplineService;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final DisciplineRepository disciplineRepository;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Override
    @Transactional
    public Discipline save(Discipline entity) {
        try {
            return disciplineRepository.save(entity);
        } catch (Exception ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
            throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public List<Discipline> saveAll(List<Discipline> entities) {
        try {
            return disciplineRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findById(Long id) {
        return disciplineRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such discipline with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return disciplineRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllById(List<Long> ids) {
        return disciplineRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return disciplineRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            disciplineRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Discipline entity) {
        try {
            disciplineRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }


    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            disciplineRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Discipline> entities) {
        try {
            disciplineRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            disciplineRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findByDisciplineName(String disciplineName) {
        return disciplineRepository.findByDisciplineName(disciplineName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such discipline with name " + disciplineName, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllBySpecialismId(Long specialismId) {
        return disciplineRepository.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discipline> findAllByAcademicYearId(Long academicYearId) {
        return disciplineRepository.findAllBySpecialismId(academicYearId);
    }

    @Override
    public List<Discipline> findAllBySpecialismIdAndAcademicYearId(Long specialismId, Long academicYearId) {
        return disciplineRepository.findAllBySpecialismIdAndAcademicYearId(specialismId, academicYearId);
    }

    @Override
    @Transactional(readOnly = true)
    public Discipline findByEducatorId(Long educatorId) {
        return disciplineRepository.findByEducatorId(educatorId).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such discipline with educator id " + educatorId, 1));
    }
}
