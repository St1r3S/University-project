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
    public void deleteById(Long id) throws NotFoundException {
        educatorDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteById(Educator entity) throws NotFoundException {
        educatorDao.deleteById(entity.getId());
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
    public void enrollEducatorSpecialism(Educator educator, Specialism specialism) throws NotFoundException {
        educatorDao.enrollEducatorSpecialism(educator.getId(), specialism.getId());
    }

    @Override
    @Transactional
    public void expelEducatorSpecialism(Educator educator, Specialism specialism) throws NotFoundException {
        educatorDao.expelEducatorSpecialism(educator.getId(), specialism.getId());
    }
}
