package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.UserDao;
import ua.com.foxminded.university.model.user.User;
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
public class HibernateUserDao extends AbstractCrudDao<User, Long> implements UserDao {

    private static final String FIND_ALL = "SELECT u FROM User u";
    private static final String FIND_ALL_BY_IDS = "SELECT u FROM User u WHERE u.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(u) FROM User u";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM User u WHERE u.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM User u";
    private static final String USER_BY_USER_NAME = "SELECT u FROM User u WHERE u.userName = :userName";
    private static final String USER_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
    private static final String USER_BY_USER_ROLE = "SELECT u FROM User u WHERE u.userRole = :userRole";
    private static final String USER_BY_BIRTHDAY = "SELECT u FROM User u WHERE u.birthday = :birthday";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected User create(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected User update(User entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.of(entityManager.find(User.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(FIND_ALL, User.class);
        return query.getResultList();
    }

    @Override
    public List<User> findAllById(List<Long> ids) {
        TypedQuery<User> query = entityManager.createQuery(FIND_ALL_BY_IDS, User.class);
        return query.setParameter("ids", ids).getResultList();

    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        User entity = entityManager.find(User.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<User> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(User::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        TypedQuery<User> query = entityManager.createQuery(USER_BY_USER_NAME, User.class);
        try {
            return Optional.of(query.setParameter("userName", userName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(USER_BY_EMAIL, User.class);
        try {
            return Optional.of(query.setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAllByUserRole(UserRole userRole) {
        TypedQuery<User> query = entityManager.createQuery(USER_BY_USER_ROLE, User.class);
        return query.setParameter("userRole", userRole).getResultList();
    }

    @Override
    public List<User> findAllByBirthday(LocalDate birthday) {
        TypedQuery<User> query = entityManager.createQuery(USER_BY_BIRTHDAY, User.class);
        return query.setParameter("birthday", birthday).getResultList();
    }
}
