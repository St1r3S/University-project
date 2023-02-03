package ua.com.foxminded.university.service;

import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;

public interface CrudUserService<T, K> extends CrudService<T, K> {

    T findByUsername(String userName);

    T findByEmail(String email);

    List<T> findAllByUserRole(UserRole userRole);

    List<T> findAllByBirthday(LocalDate birthday);

}