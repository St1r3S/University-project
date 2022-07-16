package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcLectureNumberDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.LectureNumber;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class LectureNumberServiceImpl implements CrudService<LectureNumber, Long> {
    private final JdbcLectureNumberDao lectureNumberDao;

    public LectureNumberServiceImpl(JdbcLectureNumberDao lectureNumberDao) {
        this.lectureNumberDao = lectureNumberDao;
    }

    @Override
    @Transactional(readOnly = true)
    public LectureNumber findById(Long id) throws NotFoundException {
        return lectureNumberDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such lecture number!"));
    }

    @Override
    @Transactional
    public LectureNumber save(LectureNumber entity) {
        return lectureNumberDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return lectureNumberDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(LectureNumber entity) {
        return lectureNumberDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LectureNumber> findAll() {
        return lectureNumberDao.findAll();
    }

}
