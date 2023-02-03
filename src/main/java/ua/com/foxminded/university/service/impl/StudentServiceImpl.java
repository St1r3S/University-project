package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.repository.StudentRepository;
import ua.com.foxminded.university.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    @Transactional
    public Student save(Student entity) {
        try {
            return studentRepository.save(entity);
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
    public List<Student> saveAll(List<Student> entities) {
        try {
            return studentRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such student with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllById(List<Long> ids) {
        return studentRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return studentRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Student entity) {
        try {
            studentRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            studentRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Student> entities) {
        try {
            studentRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            studentRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student findByUsername(String userName) {
        return studentRepository.findByUserName(userName).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such student with login " + userName, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public Student findByEmail(String email) {
        return studentRepository.findByUserName(email).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such student with email " + email, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByUserRole(UserRole userRole) {
        return studentRepository.findAllByUserRole(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return studentRepository.findAllByBirthday(birthday);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByGroupId(Long groupId) {
        return studentRepository.findAllByGroupId(groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return studentRepository.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByAcademicYearId(Long academicYearId) {
        return studentRepository.findAllByAcademicYearId(academicYearId);
    }
}
