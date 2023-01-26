package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.LessonDao;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class HibernateLessonDao extends AbstractCrudDao<Lesson, Long> implements LessonDao {
    private static final String FIND_ALL = "SELECT l FROM Lesson l";
    private static final String FIND_ALL_BY_IDS = "SELECT l FROM Lesson l WHERE l.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(l) FROM Lesson l";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Lesson l WHERE l.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Lesson l";
    private static final String LESSON_BY_DISCIPLINE_ID = "SELECT l FROM Lesson l WHERE l.discipline.id = :disciplineId";
    private static final String LESSON_BY_GROUP_ID = "SELECT l FROM Lesson l WHERE l.group.id = :groupId";
    private static final String LESSON_BY_LESSON_NUMBER = "SELECT l FROM Lesson l WHERE l.lessonNumber = :lessonNumber";
    private static final String LESSON_BY_ROOM_ID = "SELECT l FROM Lesson l WHERE l.room.id = :roomId";
    private static final String LESSON_BY_SCHEDULE_DAY_ID = "SELECT l FROM Lesson l WHERE l.scheduleDay.id = :scheduleDayId";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    protected Lesson create(Lesson entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Lesson update(Lesson entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Lesson.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Lesson> findAll() {
        TypedQuery<Lesson> query = entityManager.createQuery(FIND_ALL, Lesson.class);
        return query.getResultList();
    }

    @Override
    public List<Lesson> findAllById(List<Long> ids) {
        TypedQuery<Lesson> query = entityManager.createQuery(FIND_ALL_BY_IDS, Lesson.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Lesson entity = entityManager.find(Lesson.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Lesson entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Lesson> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Lesson::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public List<Lesson> findAllByDisciplineId(Long disciplineId) {
        TypedQuery<Lesson> query = entityManager.createQuery(LESSON_BY_DISCIPLINE_ID, Lesson.class);
        return query.setParameter("disciplineId", disciplineId).getResultList();
    }

    @Override
    public List<Lesson> findAllByGroupId(Long groupId) {
        TypedQuery<Lesson> query = entityManager.createQuery(LESSON_BY_GROUP_ID, Lesson.class);
        return query.setParameter("groupId", groupId).getResultList();
    }

    @Override
    public List<Lesson> findAllByLessonNumber(LessonNumber lessonNumber) {
        TypedQuery<Lesson> query = entityManager.createQuery(LESSON_BY_LESSON_NUMBER, Lesson.class);
        return query.setParameter("lessonNumber", lessonNumber).getResultList();
    }

    @Override
    public List<Lesson> findAllByRoomId(Long roomId) {
        TypedQuery<Lesson> query = entityManager.createQuery(LESSON_BY_ROOM_ID, Lesson.class);
        return query.setParameter("roomId", roomId).getResultList();
    }

    @Override
    public List<Lesson> findAllByScheduleDayId(Long scheduleDayId) {
        TypedQuery<Lesson> query = entityManager.createQuery(LESSON_BY_SCHEDULE_DAY_ID, Lesson.class);
        return query.setParameter("scheduleDayId", scheduleDayId).getResultList();
    }
}
