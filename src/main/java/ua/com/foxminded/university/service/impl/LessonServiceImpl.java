package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.repository.LessonRepository;
import ua.com.foxminded.university.service.LessonService;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    @Transactional
    public Lesson save(Lesson entity) {
        try {
            return lessonRepository.save(entity);
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
    public List<Lesson> saveAll(List<Lesson> entities) {
        try {
            return lessonRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such lesson with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return lessonRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllById(List<Long> ids) {
        return lessonRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return lessonRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            lessonRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Lesson entity) {
        try {
            lessonRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            lessonRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Lesson> entities) {
        try {
            lessonRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            lessonRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByDisciplineId(Long disciplineId) {
        return lessonRepository.findAllByDisciplineId(disciplineId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByGroupId(Long groupId) {
        return lessonRepository.findAllByGroupId(groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber) {
        return lessonRepository.findAllByLessonNumber(lessonNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByRoomId(Long roomId) {
        return lessonRepository.findAllByRoomId(roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByScheduleDayId(Long scheduleDayId) {
        return lessonRepository.findAllByScheduleDayId(scheduleDayId);
    }
}
