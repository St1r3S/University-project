package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.model.Entity;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrudUserDao<T extends Entity<K>, K extends Number> extends CrudDao<T, K> {

    Optional<T> findByLogin(String userName);

    Optional<T> findByEmail(String email);

    List<T> findAllByUserRole(UserRole userRole);

    List<T> findAllByBirthday(LocalDate birthday);

}
