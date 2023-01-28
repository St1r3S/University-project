package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.AcademicYearDao;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateAcademicYearDao extends AbstractCrudDao<AcademicYear, Long> implements AcademicYearDao {
    private static final String FIND_ALL = "SELECT a FROM AcademicYear a";
    private static final String FIND_ALL_BY_IDS = "SELECT a FROM AcademicYear a WHERE a.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(a) FROM AcademicYear a";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM AcademicYear a WHERE a.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM AcademicYear a";
    private static final String ACADEMIC_YEAR_BY_YEAR_NUMBER = "SELECT a FROM AcademicYear a WHERE a.yearNumber = :yearNumber";
    private static final String ACADEMIC_YEAR_BY_SEMESTER_TYPE = "SELECT a FROM AcademicYear a WHERE a.semesterType = :semesterType";
    private static final String ACADEMIC_YEAR_BY_YEAR_NUMBER_AND_SEMESTER_TYPE = "SELECT a FROM AcademicYear a WHERE a.yearNumber = :yearNumber AND a.semesterType = :semesterType";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected AcademicYear create(AcademicYear entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected AcademicYear update(AcademicYear entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<AcademicYear> findById(Long id) {
        try {
            return Optional.of(entityManager.find(AcademicYear.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<AcademicYear> findAll() {
        TypedQuery<AcademicYear> query = entityManager.createQuery(FIND_ALL, AcademicYear.class);
        return query.getResultList();
    }

    @Override
    public List<AcademicYear> findAllById(List<Long> ids) {
        TypedQuery<AcademicYear> query = entityManager.createQuery(FIND_ALL_BY_IDS, AcademicYear.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        AcademicYear entity = entityManager.find(AcademicYear.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(AcademicYear entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<AcademicYear> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(AcademicYear::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public List<AcademicYear> findByYearNumber(Integer yearNumber) {
        TypedQuery<AcademicYear> query = entityManager.createQuery(ACADEMIC_YEAR_BY_YEAR_NUMBER, AcademicYear.class);
        return query.setParameter("yearNumber", yearNumber).getResultList();
    }

    @Override
    public List<AcademicYear> findBySemesterType(SemesterType semesterType) {
        TypedQuery<AcademicYear> query = entityManager.createQuery(ACADEMIC_YEAR_BY_SEMESTER_TYPE, AcademicYear.class);
        return query.setParameter("semesterType", semesterType).getResultList();
    }

    @Override
    public Optional<AcademicYear> findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType) {
        TypedQuery<AcademicYear> query = entityManager.createQuery(ACADEMIC_YEAR_BY_YEAR_NUMBER_AND_SEMESTER_TYPE, AcademicYear.class);
        try {
            return Optional.of(query.setParameter("yearNumber", yearNumber).setParameter("semesterType", semesterType).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
