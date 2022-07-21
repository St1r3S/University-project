package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcRoomDao;
import ua.com.foxminded.university.model.lecture.Room;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class RoomServiceImpl implements CrudService<Room, Long> {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final JdbcRoomDao roomDao;

    public RoomServiceImpl(JdbcRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Room findById(Long id) {
        return roomDao.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such room with id " + id, 1));
    }

    @Override
    @Transactional
    public Room save(Room entity) {
        try {
            return roomDao.save(entity);
        } catch (EmptyResultDataAccessException ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
        }
        throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            roomDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
    }

    @Override
    @Transactional
    public void deleteById(Room entity) {
        try {
            roomDao.deleteById(entity.getId());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
        }
        throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomDao.findAll();
    }
}
