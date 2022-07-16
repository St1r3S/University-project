package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
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
    public Student findById(Long id) throws NotFoundException {
        return studentDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such student!"));
    }

    @Override
    public Student save(Student entity) {
        return studentDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return studentDao.deleteById(id);
    }

    @Override
    public int deleteById(Student entity) {
        return studentDao.deleteById(entity.getId());
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findAllByLectureId(Long lectureId) {
        return studentDao.findAllByLectureId(lectureId);
    }

    @Override
    public List<Student> findAllByLectureId(Lecture lecture) {
        return studentDao.findAllByLectureId(lecture.getId());
    }

    @Override
    public List<Student> findAllBySpecialismId(Long specialismId) {
        return studentDao.findAllBySpecialismId(specialismId);
    }

    @Override
    public List<Student> findAllBySpecialismId(Specialism specialism) {
        return studentDao.findAllBySpecialismId(specialism.getId());
    }

    @Override
    public List<Student> findAllByGroupName(String groupName) {
        return studentDao.findAllByGroupName(groupName);
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
        return studentDao.findAllByBirthday(birthday);
    }

    @Override
    public int enrollLectureStudent(Lecture lecture, Student student) {
        return studentDao.enrollLectureStudent(lecture.getId(), student.getId());
    }

    @Override
    public int expelLectureStudent(Lecture lecture, Student student) {
        return studentDao.expelLectureStudent(lecture.getId(), student.getId());
    }
}
