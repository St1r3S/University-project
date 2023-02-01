package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.DisciplineDao;
import ua.com.foxminded.university.model.lesson.Discipline;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class HibernateDisciplineDao extends AbstractCrudDao<Discipline, Long> implements DisciplineDao {
    private static final String FIND_ALL = "SELECT d FROM Discipline d";
    private static final String FIND_ALL_BY_IDS = "SELECT d FROM Discipline d WHERE d.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(d) FROM Discipline d";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Discipline d WHERE d.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Discipline d";
    private static final String DISCIPLINE_BY_DISCIPLINE_NAME = "SELECT d FROM Discipline d WHERE d.disciplineName = :disciplineName";
    private static final String DISCIPLINES_BY_SPECIALISM_ID = "SELECT d FROM Discipline d WHERE d.specialism.id = :specialismId";
    private static final String DISCIPLINES_BY_ACADEMIC_YEAR_ID = "SELECT d FROM Discipline d WHERE d.academicYear.id = :academicYearId";
    private static final String DISCIPLINES_BY_SPECIALISM_ID_AND_ACADEMIC_YEAR_ID = "SELECT d FROM Discipline d WHERE d.specialism.id = :specialismId AND d.academicYear.id = :academicYearId";
    private static final String DISCIPLINE_BY_EDUCATOR_ID = "SELECT d FROM Discipline d WHERE d.educator.id = :educatorId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Discipline create(Discipline entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Discipline update(Discipline entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Discipline> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Discipline.class, id));
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Discipline> findAll() {
        TypedQuery<Discipline> query = entityManager.createQuery(FIND_ALL, Discipline.class);
        return query.getResultList();
    }

    @Override
    public List<Discipline> findAllById(List<Long> ids) {
        TypedQuery<Discipline> query = entityManager.createQuery(FIND_ALL_BY_IDS, Discipline.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Discipline entity = entityManager.find(Discipline.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Discipline entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Discipline> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Discipline::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Discipline> findByDisciplineName(String disciplineName) {
        TypedQuery<Discipline> query = entityManager.createQuery(DISCIPLINE_BY_DISCIPLINE_NAME, Discipline.class);
        try {
            return Optional.of(query.setParameter("disciplineName", disciplineName).getSingleResult());
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Discipline> findAllBySpecialismId(Long specialismId) {
        TypedQuery<Discipline> query = entityManager.createQuery(DISCIPLINES_BY_SPECIALISM_ID, Discipline.class);
        return query.setParameter("specialismId", specialismId).getResultList();
    }

    @Override
    public List<Discipline> findAllByAcademicYearId(Long academicYearId) {
        TypedQuery<Discipline> query = entityManager.createQuery(DISCIPLINES_BY_ACADEMIC_YEAR_ID, Discipline.class);
        return query.setParameter("academicYearId", academicYearId).getResultList();
    }

    @Override
    public List<Discipline> findAllBySpecialismIdAndAcademicYearId(Long specialismId, Long academicYearId) {
        TypedQuery<Discipline> query = entityManager.createQuery(DISCIPLINES_BY_SPECIALISM_ID_AND_ACADEMIC_YEAR_ID, Discipline.class);
        return query.setParameter("specialismId", specialismId).setParameter("academicYearId", academicYearId).getResultList();
    }

    @Override
    public Optional<Discipline> findByEducatorId(Long educatorId) {
        TypedQuery<Discipline> query = entityManager.createQuery(DISCIPLINE_BY_EDUCATOR_ID, Discipline.class);
        try {
            return Optional.of(query.setParameter("educatorId", educatorId).getSingleResult());
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
