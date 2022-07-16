package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcStudentDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Lecture;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final JdbcStudentDao studentDao;

    public StudentServiceImpl(JdbcStudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) throws NotFoundException {
        return studentDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such student!"));
    }

    @Override
    @Transactional
    public Student save(Student entity) {
        return studentDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return studentDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(Student entity) {
        return studentDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByLectureId(Long lectureId) {
        return studentDao.findAllByLectureId(lectureId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByLectureId(Lecture lecture) {
        return studentDao.findAllByLectureId(lecture.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return studentDao.findAllBySpecialismId(specialismId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllBySpecialismId(Specialism specialism) {
        return studentDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByGroupName(String groupName) {
        return studentDao.findAllByGroupName(groupName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return studentDao.findAllByBirthday(birthday);
    }

    @Override
    @Transactional
    public int enrollLectureStudent(Lecture lecture, Student student) {
        return studentDao.enrollLectureStudent(lecture.getId(), student.getId());
    }

    @Override
    @Transactional
    public int expelLectureStudent(Lecture lecture, Student student) {
        return studentDao.expelLectureStudent(lecture.getId(), student.getId());
    }
}
