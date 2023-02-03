package ua.com.foxminded.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.repository.RoomRepository;
import ua.com.foxminded.university.service.LessonService;
import ua.com.foxminded.university.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private static final Logger logger = LoggerFactory.getLogger("ua.com.foxminded.university.service");
    private final RoomRepository roomRepository;

    private final LessonService lessonService;

    public RoomServiceImpl(RoomRepository roomRepository, LessonService lessonService) {
        this.roomRepository = roomRepository;
        this.lessonService = lessonService;
    }


    @Override
    @Transactional
    public Room save(Room entity) {
        try {
            return roomRepository.save(entity);
        } catch (Exception ex) {
            if (entity.getId() == null) {
                logger.error("Unable to create entity {} due {}", entity, ex.getMessage(), ex);
            } else {
                logger.error("Unable to update entity {} due {}", entity, ex.getMessage(), ex);
            }
            throw new EmptyResultDataAccessException("Unable to save entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public List<Room> saveAll(List<Room> entities) {
        try {
            return roomRepository.saveAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to update entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to save entities " + entities, 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such room with id " + id, 1));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return roomRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> findAllById(List<Long> ids) {
        return roomRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return roomRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            roomRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Unable to delete entity with id {} due {}", id, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity with id " + id, 1);
        }
    }

    @Override
    @Transactional
    public void delete(Room entity) {
        try {
            roomRepository.deleteById(entity.getId());
        } catch (Exception ex) {
            logger.error("Unable to delete entity {} due {}", entity, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entity " + entity, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAllById(List<Long> ids) {
        try {
            roomRepository.deleteAllById(ids);
        } catch (Exception ex) {
            logger.error("Unable to delete entities with ids {} due {}", ids, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities with ids " + ids, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Room> entities) {
        try {
            roomRepository.deleteAll(entities);
        } catch (Exception ex) {
            logger.error("Unable to delete entities {} due {}", entities, ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete entities " + entities, 1);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            roomRepository.deleteAll();
        } catch (Exception ex) {
            logger.error("Unable to delete all entities due {}", ex.getMessage(), ex);
            throw new EmptyResultDataAccessException("Unable to delete all entities ", 1);
        }
    }


    @Override
    public Room findByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).orElseThrow(
                () -> new EmptyResultDataAccessException("There's no such room with number " + roomNumber, 1));
    }

    @Override
    public List<Room> findAllFreeRooms(LessonNumber lessonNumber, ScheduleDay scheduleDay) {
        List<Long> busyRooms = lessonService.findAll().stream()
                .filter(lesson -> lesson.getLessonNumber() == lessonNumber
                        && lesson.getScheduleDay().getId().equals(scheduleDay.getId()))
                .map(lesson -> lesson.getRoom().getId()).collect(Collectors.toList());

        return roomRepository.findAll().stream().filter(room -> !busyRooms.contains(room.getId())).collect(Collectors.toList());
    }
}
