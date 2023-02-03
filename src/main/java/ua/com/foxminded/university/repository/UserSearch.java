package ua.com.foxminded.university.repository;

import org.springframework.data.repository.NoRepositoryBean;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserSearch<T> {

    Optional<T> findByUserName(String userName);

    Optional<T> findByEmail(String email);

    List<T> findAllByUserRole(UserRole userRole);

    List<T> findAllByBirthday(LocalDate birthday);

}
