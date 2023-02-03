package ua.com.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;

import java.util.List;

@Repository
public interface EducatorRepository extends UserSearch<Educator>, JpaRepository<Educator, Long> {

    List<Educator> findAllByAcademicRank(AcademicRank academicRank);

}
