package ua.com.foxminded.university.service.impl;

import org.springframework.stereotype.Service;
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
    public Room findById(Long id) throws NotFoundException {
        return roomDao.findById(id).orElseThrow(() -> new NotFoundException("There's no such room!"));
    }

    @Override
    public Room save(Room entity) {
        return roomDao.save(entity);
    }

    @Override
    public int deleteById(Long id) {
        return roomDao.deleteById(id);
    }

    @Override
    public int deleteById(Room entity) {
        return roomDao.deleteById(entity.getId());
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }
}
