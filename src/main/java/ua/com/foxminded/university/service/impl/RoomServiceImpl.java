package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.dao.jdbc.JdbcRoomDao;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Room;
import ua.com.foxminded.university.service.CrudService;

import java.util.List;

@Service
public class RoomServiceImpl implements CrudService<Room, Long> {
    private final JdbcRoomDao roomDao;

    public RoomServiceImpl(JdbcRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Room findById(Long id) throws NotFoundException {
        return roomDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such room!"));
    }

    @Override
    @Transactional
    public Room save(Room entity) {
        return roomDao.save(entity);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return roomDao.deleteById(id);
    }

    @Override
    @Transactional
    public int deleteById(Room entity) {
        return roomDao.deleteById(entity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomDao.findAll();
    }
}
