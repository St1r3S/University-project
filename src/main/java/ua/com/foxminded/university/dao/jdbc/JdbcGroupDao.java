package ua.com.foxminded.university.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.dao.jdbc.mappers.GroupRowMapper;
import ua.com.foxminded.university.model.user.Group;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGroupDao extends AbstractCrudDao<Group, Long> implements GroupDao {
    public static final String GROUP_ID = "id";
    public static final String GROUP_NAME = "group_name";
    public static final String GROUP_SPECIALISM_ID = "specialism_id";
    public static final String GROUP_ACADEMIC_YEAR_ID = "academic_year_id";
    private static final String UPDATE = "UPDATE groupss SET group_name = ?, specialism_id = ?, academic_year_id = ? WHERE id = ?";
    private static final String RETRIEVE = "SELECT * FROM groupss WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM groupss LIMIT ?";
    private static final String FIND_ALL_BY_IDS = "SELECT * FROM groupss WHERE id IN (%s)";
    private static final String COUNT = "SELECT count(*) FROM groupss";
    private static final String DELETE = "DELETE FROM groupss WHERE id = ?";
    private static final String DELETE_ALL_BY_IDS = "DELETE FROM groupss WHERE id IN (%s)";
    private static final String DELETE_ALL = "DELETE FROM groupss";
    private static final String GROUP_BY_GROUP_NAME = "SELECT * FROM groupss WHERE group_name = ?";
    private static final String GROUP_BY_SPECIALISM_ID = "SELECT * FROM groupss WHERE specialism_id = ?";
    private static final String GROUP_BY_ACADEMIC_YEAR_ID = "SELECT * FROM groupss WHERE academic_year_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(("groupss"))
                .usingGeneratedKeyColumns("id");

    }

    @Override
    protected Group create(Group entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(GROUP_NAME, entity.getGroupName())
                        .addValue(GROUP_SPECIALISM_ID, entity.getSpecialismId())
                        .addValue(GROUP_ACADEMIC_YEAR_ID, entity.getAcademicYearId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    protected Group update(Group entity) {
        if (1 == jdbcTemplate.update(UPDATE, entity.getGroupName(), entity.getSpecialismId(),
                entity.getAcademicYearId(), entity.getId())) {
            return entity;
        }
        throw new EmptyResultDataAccessException("Unable to update entity " + entity, 1);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return jdbcTemplate.query(RETRIEVE, new GroupRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Group> findAll(Number limit) {
        return jdbcTemplate.query(FIND_ALL, new GroupRowMapper(), limit);
    }

    @Override
    public List<Group> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(
                String.format(FIND_ALL_BY_IDS, inSql),
                new GroupRowMapper(),
                ids.toArray());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Long id) {
        if (1 != jdbcTemplate.update(DELETE, id))
            throw new EmptyResultDataAccessException("Unable to delete group entity with id" + id, 1);
    }

    @Override
    public void delete(Group entity) {
        if (1 != jdbcTemplate.update(DELETE, entity.getId()))
            throw new EmptyResultDataAccessException("Unable to delete group entity with id" + entity.getId(), 1);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                ids.toArray());
    }

    @Override
    public void deleteAll(List<Group> entities) {
        String inSql = String.join(",", Collections.nCopies(entities.size(), "?"));

        jdbcTemplate.update(
                String.format(DELETE_ALL_BY_IDS, inSql),
                entities.stream().map(Group::getId).toArray());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<Group> findByGroupName(String groupName) {
        return jdbcTemplate.query(GROUP_BY_GROUP_NAME, new GroupRowMapper(), groupName)
                .stream()
                .findFirst();
    }

    @Override
    public List<Group> findAllBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(GROUP_BY_SPECIALISM_ID, new GroupRowMapper(), specialismId);
    }

    @Override
    public List<Group> findAllByAcademicYearId(Long academicYearId) {
        return jdbcTemplate.query(GROUP_BY_ACADEMIC_YEAR_ID, new GroupRowMapper(), academicYearId);
    }
}
