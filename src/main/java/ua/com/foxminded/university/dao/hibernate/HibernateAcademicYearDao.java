package ua.com.foxminded.university.dao.hibernate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.AcademicYearDao;
import ua.com.foxminded.university.dao.hibernate.mappers.AcademicYearRowMapper;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateAcademicYearDao extends AbstractCrudDao<AcademicYear, Long> implements AcademicYearDao {
    public static final String ACADEMIC_YEAR_ID = "id";
    public static final String ACADEMIC_YEAR_YEAR_NUMBER = "year_number";
    public static final String ACADEMIC_YEAR_SEMESTER_TYPE = "semester_type";
    private static final String UPDATE = "UPDATE academic_years SET year_number = ?, semester_type = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM academic_years WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM academic_years LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM academic_years WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM academic_years";
    private static final String DELETE = "DELETE FROM academic_years WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM academic_years WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM academic_years";
    private static final String ACADEMIC_YEAR_BY_YEAR_NUMBER = "SELECT * FROM academic_years WHERE year_number = ?";
    private static final String ACADEMIC_YEAR_BY_SEMESTER_TYPE = "SELECT * FROM academic_years WHERE semester_type = ?";
    private static final String ACADEMIC_YEAR_BY_YEAR_NUMBER_AND_SEMESTER_TYPE = "SELECT * FROM academic_years WHERE year_number = ? AND semester_type = ?";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected AcademicYear create(AcademicYear entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    protected AcademicYear update(AcademicYear entity) {
        try {
            return entityManager.merge(entity);
        } catch (IllegalArgumentException ex) {
            throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
        }
    }

    @Override
    public Optional<AcademicYear> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new AcademicYearRowMapper(), id).stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<AcademicYear> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new AcademicYearRowMapper(), limit);
    }

    @Override
    public List<AcademicYear> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new AcademicYearRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete academic year entity with id" + id, 1);
    }

    @Override
    public void delete(AcademicYear entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete academic year entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<AcademicYear> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(AcademicYear::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public List<AcademicYear> findByYearNumber(Integer yearNumber) {
        return jdbcTemplate.query(ACADEMIC_YEAR_BY_YEAR_NUMBER, new AcademicYearRowMapper(), yearNumber);
    }

    @Override
    public List<AcademicYear> findBySemesterType(SemesterType semesterType) {
        return jdbcTemplate.query(ACADEMIC_YEAR_BY_SEMESTER_TYPE, new AcademicYearRowMapper(), semesterType.getValue());
    }

    @Override
    public Optional<AcademicYear> findByYearNumberAndSemesterType(Integer yearNumber, SemesterType semesterType) {
        return jdbcTemplate.query(ACADEMIC_YEAR_BY_YEAR_NUMBER_AND_SEMESTER_TYPE, new AcademicYearRowMapper(), yearNumber, semesterType.getValue()).stream().findFirst();
    }
}
