package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.lesson.Specialism;

import java.util.Optional;

@Repository
public interface SpecialismRepository extends JpaRepository<Specialism, Long> {
    Optional<Specialism> findBySpecialismName(String specialismName);
}
