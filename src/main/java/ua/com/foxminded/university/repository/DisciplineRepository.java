package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.lesson.Discipline;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByDisciplineName(String disciplineName);

    List<Discipline> findAllBySpecialismId(Long specialismId);

    List<Discipline> findAllByAcademicYearId(Long academicYearId);

    List<Discipline> findAllBySpecialismIdAndAcademicYearId(Long specialismId, Long academicYearId);

    Optional<Discipline> findByEducatorId(Long educatorId);

}
