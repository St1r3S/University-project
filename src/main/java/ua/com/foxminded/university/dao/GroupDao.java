package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.user.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao extends CrudDao<Group, Long> {

    Optional<Group> findByGroupName(String groupName);

    List<Group> findAllBySpecialismId(Long specialismId);

    List<Group> findAllByAcademicYearId(Long academicYearId);

}
