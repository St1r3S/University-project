package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.repository.UserRepository;
import ua.com.foxminded.university.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User entity) {
        try {
            return userRepository.save(entity);
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
    public List<User> saveAll(List<User> entities) {
        try {
            return userRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(User entity) {
        try {
            userRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            userRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<User> entities) {
        try {
            userRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            userRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with login " + userName, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByUserName(email).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such user with email " + email, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByUserRole(UserRole userRole) {
        return userRepository.findAllByUserRole(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByBirthday(LocalDate birthday) {
        return userRepository.findAllByBirthday(birthday);
    }
}
