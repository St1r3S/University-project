package ua.com.foxminded.university.dao.hibernate;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.RoomDao;
import ua.com.foxminded.university.model.lesson.Room;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateRoomDao extends AbstractCrudDao<Room, Long> implements RoomDao {
    private static final String FIND_ALL = "SELECT r FROM Room r";
    private static final String FIND_ALL_BY_IDS = "SELECT r FROM Room r WHERE r.id IN (:ids)";
    private static final String COUNT = "SELECT COUNT(r) FROM Room r";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM Room r WHERE r.id IN (:ids)";
    private static final String DELETE_ALL = "DELETE FROM Room r";
    private static final String ROOM_BY_ROOM_NUMBER = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Room create(Room entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected Room update(Room entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Room> findById(Long id) {
        try {
            return Optional.of(entityManager.find(Room.class, id));
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Room> findAll() {
        TypedQuery<Room> query = entityManager.createQuery(FIND_ALL, Room.class);
        return query.getResultList();
    }

    @Override
    public List<Room> findAllById(List<Long> ids) {
        TypedQuery<Room> query = entityManager.createQuery(FIND_ALL_BY_IDS, Room.class);
        return query.setParameter("ids", ids).getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Room entity = entityManager.find(Room.class, id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(Room entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(List<Room> entities) {
        entityManager.createQuery(DELETE_ALL_BY_IDS)
                .setParameter("ids", entities.stream().map(Room::getId).collect(Collectors.toList()))
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery(DELETE_ALL)
                .executeUpdate();
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber) {
        TypedQuery<Room> query = entityManager.createQuery(ROOM_BY_ROOM_NUMBER, Room.class);
        try {
            return Optional.of(query.setParameter("roomNumber", roomNumber).getSingleResult());
        } catch (NoResultException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
