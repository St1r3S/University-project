package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.user.User;

@Repository
public interface UserRepository extends UserSearch<User>, JpaRepository<User, Long> {
}


