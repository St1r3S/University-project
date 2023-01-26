package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.model.user.Group;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateGroupDao extends AbstractCrudDao<Group, Long> implements GroupDao {
    private static final String FIND_ALL = "SELECT g FROM Group g";
    private static final String FIND_ALL_BY_IDS = "SELECT g FROM Group g WHERE g.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(g) FROM Group g";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Group g WHERE g.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Group g";
    private static final String GROUP_BY_GROUP_NAME = "SELECT g FROM Group g WHERE g.groupName = :groupName";
    private static final String GROUP_BY_SPECIALISM_ID = "SELECT g FROM Group g WHERE g.specialism.id = :specialismId";
    private static final String GROUP_BY_ACADEMIC_YEAR_ID = "SELECT g FROM Group g WHERE g.academicYear.id = :academicYearId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Group create(Group entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Group update(Group entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Group> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Group.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Group> findAll() {
        TypedQuery<Group> query = entityManager.createQuery(FIND_ALL, Group.class);
        return query.getResultList();
    }

    @Override
    public List<Group> findAllById(List<Long> ids) {
        TypedQuery<Group> query = entityManager.createQuery(FIND_ALL_BY_IDS, Group.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Group entity = entityManager.find(Group.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Group entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Group> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Group::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Group> findByGroupName(String groupName) {
        TypedQuery<Group> query = entityManager.createQuery(GROUP_BY_GROUP_NAME, Group.class);
        try {
            return Optional.of(query.setParameter("groupName", groupName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAllBySpecialismId(Long specialismId) {
        TypedQuery<Group> query = entityManager.createQuery(GROUP_BY_SPECIALISM_ID, Group.class);
        return query.setParameter("specialismId", specialismId).getResultList();
    }

    @Override
    public List<Group> findAllByAcademicYearId(Long academicYearId) {
        TypedQuery<Group> query = entityManager.createQuery(GROUP_BY_ACADEMIC_YEAR_ID, Group.class);
        return query.setParameter("academicYearId", academicYearId).getResultList();
    }
}
