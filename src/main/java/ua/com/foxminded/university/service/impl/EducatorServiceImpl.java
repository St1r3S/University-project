package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcEducatorDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.service.EducatorService;

import java.util.List;

@Service
public class EducatorServiceImpl implements EducatorService {
    private final JdbcEducatorDao educatorDao;

    public EducatorServiceImpl(JdbcEducatorDao educatorDao) {
        this.educatorDao = educatorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Educator findById(Long id) throws NotFoundException {
        return educatorDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such educator!"));
    }

    @Override
    @Transactional
    public Educator save(Educator entity) {
        return educatorDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return educatorDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(Educator entity) {
        return educatorDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAll() {
        return educatorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllBySpecialismId(Long specialismId) {
        return educatorDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Educator> findAllBySpecialismId(Specialism specialism) {
        return educatorDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    @Transactional
    public int enrollEducatorSpecialism(Educator educator, Specialism specialism) {
        return educatorDao.enrollEducatorSpecialism(educator.getId(), specialism.getId());
    }

    @Override
    @Transactional
    public int expelEducatorSpecialism(Educator educator, Specialism specialism) {
        return educatorDao.expelEducatorSpecialism(educator.getId(), specialism.getId());
    }
}
