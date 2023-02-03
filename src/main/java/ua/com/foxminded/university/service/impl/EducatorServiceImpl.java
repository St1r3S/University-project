package ua.com.foxminded.university.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.repository.EducatorRepository;
import ua.com.foxminded.university.service.DisciplineService;
import ua.com.foxminded.university.service.EducatorService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducatorServiceImpl implements EducatorService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final EducatorRepository educatorRepository;
    private final DisciplineService disciplineService;

    public EducatorServiceImpl(EducatorRepository educatorRepository, DisciplineService disciplineService) {
        this.educatorRepository = educatorRepository;
        this.disciplineService = disciplineService;
    }

    @Override
    @Transactional
    public Educator save(Educator entity) {
        try {
            return educatorRepository.save(entity);
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
    public List<Educator> saveAll(List<Educator> entities) {
        try {
            return educatorRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Educator findById(Long id) {
        return educatorRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return educatorRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAll() {
        return educatorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllById(List<Long> ids) {
        return educatorRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return educatorRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            educatorRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Educator entity) {
        try {
            educatorRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            educatorRepository.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Educator> entities) {
        try {
            educatorRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            educatorRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    public Educator findByUsername(String userName) {
        return educatorRepository.findByUserName(userName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with login " + userName, 1));
    }

    @Override
    public Educator findByEmail(String email) {
        return educatorRepository.findByUserName(email).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such educator with email " + email, 1));
    }

    @Override
    public List<Educator> findAllByUserRole(UserRole userRole) {
        return educatorRepository.findAllByUserRole(userRole);
    }

    @Override
    public List<Educator> findAllByBirthday(LocalDate birthday) {
        return educatorRepository.findAllByBirthday(birthday);
    }

    @Override
    public List<Educator> findAllByAcademicRank(AcademicRank academicRank) {
        return educatorRepository.findAllByAcademicRank(academicRank);
    }

    @Override
    public List<Educator> findAllFreeEducators() {
        List<Long> busyEducatorsIds = disciplineService.findAll().stream().map(discipline -> discipline.getEducator().getId()).collect(Collectors.toList());
        return educatorRepository.findAll()
                .stream()
                .filter(educator -> !busyEducatorsIds.contains(educator.getId()))
                .collect(Collectors.toList());
    }
}
