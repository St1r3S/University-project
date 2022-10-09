package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.UserDao;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User save(User entity) {
        try {
            return userDao.save(entity);
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
    public List<User> saveAll(List<User> entities) {
        try {
            return userDao.saveAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userDao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll(100);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(List<Long> ids) {
        return userDao.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userDao.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            userDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);

    }

    @Override
    @Transactional
    public void delete(User entity) {
        try {
            userDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);

    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            userDao.deleteAllById(ids);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);

    }

    @Override
    @Transactional
    public void deleteAll(List<User> entities) {
        try {
            userDao.deleteAll(entities);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);

    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            userDao.deleteAll();
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);

    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String userName) {
        return userDao.findByLogin(userName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with login " + userName, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByLogin(email).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with email " + email, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByUserRole(UserRole userRole) {
        return userDao.findAllByUserRole(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByBirthday(LocalDate birthday) {
        return userDao.findAllByBirthday(birthday);
    }
}
