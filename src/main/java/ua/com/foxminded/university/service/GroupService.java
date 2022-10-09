package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.user.Group;

import java.util.List;

public interface GroupService extends CrudService<Group, Long> {

    Group findByGroupName(String groupName);

    List<Group> findAllBySpecialismId(Long specialismId);

    List<Group> findAllByAcademicYearId(Long academicYearId);
}
