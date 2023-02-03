package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.repository.SpecialismRepository;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.List;

@Service
public class SpecialismServiceImpl implements SpecialismService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final SpecialismRepository specialismRepository;

    public SpecialismServiceImpl(SpecialismRepository specialismRepository) {
        this.specialismRepository = specialismRepository;
    }


    @Override
    @Transactional
    public Specialism save(Specialism entity) {
        try {
            return specialismRepository.save(entity);
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
    public List<Specialism> saveAll(List<Specialism> entities) {
        try {
            return specialismRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Specialism findById(Long id) {
        return specialismRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such specialism with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return specialismRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAll() {
        return specialismRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Specialism> findAllById(List<Long> ids) {
        return specialismRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return specialismRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            specialismRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Specialism entity) {
        try {
            specialismRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            specialismRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Specialism> entities) {
        try {
            specialismRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            specialismRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Specialism findBySpecialismName(String specialismName) {
        return specialismRepository.findBySpecialismName(specialismName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such specialism with name " + specialismName, 1));
    }
}
