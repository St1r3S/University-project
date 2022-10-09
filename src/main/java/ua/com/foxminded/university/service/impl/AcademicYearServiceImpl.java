package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.AcademicYearDao;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.service.AcademicYearService;

import java.util.List;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final AcademicYearDao academicYearDao;

    public AcademicYearServiceImpl(AcademicYearDao academicYearDao) {
        this.academicYearDao = academicYearDao;
    }

    @Override
    @Transactional
    public AcademicYear save(AcademicYear entity) {
        try {
            return academicYearDao.save(entity);
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
    public List<AcademicYear> saveAll(List<AcademicYear> entities) {
        try {
            return academicYearDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicYear findById(Long id) {
        return academicYearDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such academic year with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return academicYearDao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYear> findAll() {
        return academicYearDao.findAll(100);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYear> findAllById(List<Long> ids) {
        return academicYearDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return academicYearDao.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            academicYearDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(AcademicYear entity) {
        try {
            academicYearDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            academicYearDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
    }

    @Override
    @Transactional
    public void deleteAll(List<AcademicYear> entities) {
        try {
            academicYearDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            academicYearDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYear> findByYearNumber(Integer yearNumber) {
        return academicYearDao.findByYearNumber(yearNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYear> findBySemesterType(SemesterType semesterType) {
        return academicYearDao.findBySemesterType(semesterType);
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicYear findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType) {
        return academicYearDao.findByYearNumberAndSemesterType(yearNumber, semesterType).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such academic year with year number " + yearNumber
                        + " and semester type " + semesterType, 1));
    }
}
