package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.ScheduleDayDao;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateScheduleDayDao extends AbstractCrudDao<ScheduleDay, Long> implements ScheduleDayDao {

    private static final String FIND_ALL = "SELECT s FROM ScheduleDay s";
    private static final String FIND_ALL_BY_IDS = "SELECT s FROM ScheduleDay s WHERE s.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(s) FROM ScheduleDay s";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM ScheduleDay s WHERE s.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM ScheduleDay s";
    private static final String SCHEDULE_DAY_BY_DAY_OF_WEEK = "SELECT s FROM ScheduleDay s WHERE s.dayOfWeek = :dayOfWeek";
    private static final String SCHEDULE_DAY_BY_SEMESTER_TYPE = "SELECT s FROM ScheduleDay s WHERE s.semesterType = :semesterType";
    private static final String SCHEDULE_DAY_BY_DAY_OF_WEEK_AND_SEMESTER_TYPE = "SELECT s FROM ScheduleDay s WHERE s.dayOfWeek = :dayOfWeek AND s.semesterType = :semesterType";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected ScheduleDay create(ScheduleDay entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected ScheduleDay update(ScheduleDay entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<ScheduleDay> findById(Long id) {
        try {
            return Optional.of(entityManager.find(ScheduleDay.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<ScheduleDay> findAll() {
        TypedQuery<ScheduleDay> query = entityManager.createQuery(FIND_ALL, ScheduleDay.class);
        return query.getResultList();
    }

    @Override
    public List<ScheduleDay> findAllById(List<Long> ids) {
        TypedQuery<ScheduleDay> query = entityManager.createQuery(FIND_ALL_BY_IDS, ScheduleDay.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        ScheduleDay entity = entityManager.find(ScheduleDay.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(ScheduleDay entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<ScheduleDay> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(ScheduleDay::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public List<ScheduleDay> findByDayOfWeek(DayOfWeek dayOfWeek) {
        TypedQuery<ScheduleDay> query = entityManager.createQuery(SCHEDULE_DAY_BY_DAY_OF_WEEK, ScheduleDay.class);
        return query.setParameter("dayOfWeek", dayOfWeek).getResultList();
    }

    @Override
    public List<ScheduleDay> findBySemesterType(SemesterType semesterType) {
        TypedQuery<ScheduleDay> query = entityManager.createQuery(SCHEDULE_DAY_BY_SEMESTER_TYPE, ScheduleDay.class);
        return query.setParameter("semesterType", semesterType).getResultList();
    }

    @Override
    public Optional<ScheduleDay> findByDayOfWeekAndSemesterType(DayOfWeek dayOfWeek, SemesterType semesterType) {
        TypedQuery<ScheduleDay> query = entityManager.createQuery(SCHEDULE_DAY_BY_DAY_OF_WEEK_AND_SEMESTER_TYPE, ScheduleDay.class);
        try {
            return Optional.of(query.setParameter("dayOfWeek", dayOfWeek).setParameter("semesterType", semesterType).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
