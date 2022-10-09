package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final GroupDao groupDao;

    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    @Transactional
    public Group save(Group entity) {
        try {
            return groupDao.save(entity);
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
    public List<Group> saveAll(List<Group> entities) {
        try {
            return groupDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public Group findById(Long id) {
        return groupDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such group with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return groupDao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAll() {
        return groupDao.findAll(100);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAllById(List<Long> ids) {
        return groupDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return groupDao.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            groupDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void delete(Group entity) {
        try {
            groupDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);

    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            groupDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);

    }

    @Override
    @Transactional
    public void deleteAll(List<Group> entities) {
        try {
            groupDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);

    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            groupDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);

    }

    @Override
    @Transactional(readOnly = true)
    public Group findByGroupName(String groupName) {
        return groupDao.findByGroupName(groupName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such group with name " + groupName, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAllBySpecialismId(Long specialismId) {
        return groupDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAllByAcademicYearId(Long academicYearId) {
        return groupDao.findAllByAcademicYearId(academicYearId);
    }
}
