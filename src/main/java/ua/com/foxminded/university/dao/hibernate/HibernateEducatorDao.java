package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
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
public class HibernateEducatorDao extends AbstractCrudDao<Educator, Long> implements EducatorDao {
    private static final String FIND_ALL = "SELECT e FROM Educator e";
    private static final String FIND_ALL_BY_IDS = "SELECT e FROM Educator e WHERE e.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(e) FROM Educator e";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Educator e WHERE e.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Educator e";
    private static final String EDUCATOR_BY_USER_NAME = "SELECT e FROM Educator e WHERE e.userName = :userName";
    private static final String EDUCATOR_BY_EMAIL = "SELECT e FROM Educator e WHERE e.email = :email";
    private static final String EDUCATOR_BY_USER_ROLE = "SELECT e FROM Educator e WHERE e.userRole = :userRole";
    private static final String EDUCATOR_BY_BIRTHDAY = "SELECT e FROM Educator e WHERE e.birthday = :birthday";
    private static final String EDUCATOR_BY_ACADEMIC_RANK = "SELECT e FROM Educator e WHERE e.academicRank = :academicRank";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Educator create(Educator entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Educator update(Educator entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Educator> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Educator.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Educator> findAll() {
        TypedQuery<Educator> query = entityManager.createQuery(FIND_ALL, Educator.class);
        return query.getResultList();
    }

    @Override
    public List<Educator> findAllById(List<Long> ids) {
        TypedQuery<Educator> query = entityManager.createQuery(FIND_ALL_BY_IDS, Educator.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Educator entity = entityManager.find(Educator.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Educator entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Educator> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Educator::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Educator> findByUserName(String userName) {
        TypedQuery<Educator> query = entityManager.createQuery(EDUCATOR_BY_USER_NAME, Educator.class);
        try {
            return Optional.of(query.setParameter("userName", userName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Educator> findByEmail(String email) {
        TypedQuery<Educator> query = entityManager.createQuery(EDUCATOR_BY_EMAIL, Educator.class);
        try {
            return Optional.of(query.setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Educator> findAllByUserRole(UserRole userRole) {
        TypedQuery<Educator> query = entityManager.createQuery(EDUCATOR_BY_USER_ROLE, Educator.class);
        return query.setParameter("userRole", userRole).getResultList();
    }

    @Override
    public List<Educator> findAllByBirthday(LocalDate birthday) {
        TypedQuery<Educator> query = entityManager.createQuery(EDUCATOR_BY_BIRTHDAY, Educator.class);
        return query.setParameter("birthday", birthday).getResultList();
    }

    @Override
    public List<Educator> findAllByAcademicRank(AcademicRank academicRank) {
        TypedQuery<Educator> query = entityManager.createQuery(EDUCATOR_BY_ACADEMIC_RANK, Educator.class);
        return query.setParameter("academicRank", academicRank).getResultList();
    }
}
