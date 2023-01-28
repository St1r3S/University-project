package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {

    private static final String FIND_ALL = "SELECT s FROM Student s";
    private static final String FIND_ALL_BY_IDS = "SELECT s FROM Student s WHERE s.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(s) FROM Student s";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Student s WHERE s.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Student s";
    private static final String STUDENT_BY_USER_NAME = "SELECT s FROM Student s WHERE s.userName = :userName";
    private static final String STUDENT_BY_EMAIL = "SELECT s FROM Student s WHERE s.email = :email";
    private static final String STUDENT_BY_USER_ROLE = "SELECT s FROM Student s WHERE s.userRole = :userRole";
    private static final String STUDENT_BY_BIRTHDAY = "SELECT s FROM Student s WHERE s.birthday = :birthday";
    private static final String STUDENT_BY_GROUP_ID = "SELECT s FROM Student s WHERE s.group.id = :groupId";
    private static final String STUDENT_BY_SPECIALISM_ID = "SELECT s FROM Student s WHERE s.specialism.id = :specialismId";
    private static final String STUDENT_BY_ACADEMIC_YEAR_ID = "SELECT s FROM Student s WHERE s.academicYear.id = :academicYearId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Student create(Student entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Student update(Student entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Student> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Student.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> query = entityManager.createQuery(FIND_ALL, Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findAllById(List<Long> ids) {
        TypedQuery<Student> query = entityManager.createQuery(FIND_ALL_BY_IDS, Student.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Student entity = entityManager.find(Student.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Student entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Student> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Student::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Student> findByUserName(String userName) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_USER_NAME, Student.class);
        try {
            return Optional.of(query.setParameter("userName", userName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_EMAIL, Student.class);
        try {
            return Optional.of(query.setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAllByUserRole(UserRole userRole) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_USER_ROLE, Student.class);
        return query.setParameter("userRole", userRole).getResultList();
    }

    @Override
    public List<Student> findAllByBirthday(LocalDate birthday) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_BIRTHDAY, Student.class);
        return query.setParameter("birthday", birthday).getResultList();
    }

    @Override
    public List<Student> findAllByGroupId(Long groupId) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_GROUP_ID, Student.class);
        return query.setParameter("groupId", groupId).getResultList();
    }

    @Override
    public List<Student> findAllBySpecialismId(Long specialismId) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_SPECIALISM_ID, Student.class);
        return query.setParameter("specialismId", specialismId).getResultList();
    }

    @Override
    public List<Student> findAllByAcademicYearId(Long academicYearId) {
        TypedQuery<Student> query = entityManager.createQuery(STUDENT_BY_ACADEMIC_YEAR_ID, Student.class);
        return query.setParameter("academicYearId", academicYearId).getResultList();
    }
}
