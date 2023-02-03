package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.SpecialismDao;
import ua.com.foxminded.university.model.lesson.Specialism;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateSpecialismDao extends AbstractCrudDao<Specialism, Long> implements SpecialismDao {
    private static final String FIND_ALL = "SELECT s FROM Specialism s";
    private static final String FIND_ALL_BY_IDS = "SELECT s FROM Specialism s WHERE s.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(s) FROM Specialism s";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Specialism s WHERE s.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Specialism s";
    private static final String SPECIALISM_BY_SPECIALISM_NAME = "SELECT s FROM Specialism s WHERE s.specialismName = :specialismName";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Specialism create(Specialism entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Specialism update(Specialism entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Specialism> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Specialism.class, id));
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Specialism> findAll() {
        TypedQuery<Specialism> query = entityManager.createQuery(FIND_ALL, Specialism.class);
        return query.getResultList();
    }

    @Override
    public List<Specialism> findAllById(List<Long> ids) {
        TypedQuery<Specialism> query = entityManager.createQuery(FIND_ALL_BY_IDS, Specialism.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Specialism entity = entityManager.find(Specialism.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Specialism entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Specialism> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Specialism::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Specialism> findBySpecialismName(String specialismName) {
        TypedQuery<Specialism> query = entityManager.createQuery(SPECIALISM_BY_SPECIALISM_NAME, Specialism.class);
        try {
            return Optional.of(query.setParameter("specialismName", specialismName).getSingleResult());
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
