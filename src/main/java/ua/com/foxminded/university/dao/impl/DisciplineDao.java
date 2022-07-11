package ua.com.foxminded.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.com.foxminded.university.dao.DAO;
import ua.com.foxminded.university.dao.mappers.DisciplineRowMapper;
import ua.com.foxminded.university.model.lecture.Discipline;

import java.util.List;
import java.util.Optional;

public class DisciplineDao implements DAO<Long, Discipline> {
    public static final String DISCIPLINE_ID = "id";
    public static final String DISCIPLINE_NAME = "discipline_name";
    public static final String DISCIPLINE_EDUCATOR_ID = "educator_id";
    public static final String CREATE = "INSERT INTO discipline(discipline_name, educator_id) VALUES (?,?)";
    public static final String RETRIEVE = "SELECT id, discipline_name, educator_id FROM discipline WHERE id = ?";
    public static final String UPDATE = "UPDATE discipline SET discipline_name = ?, educator_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM discipline WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, discipline_name, educator_id FROM discipline LIMIT 100";
    private static final String DISCIPLINES_BY_SPECIALISM_ID = "SELECT d.id, d.discipline_name, d.educator_id " +
            "FROM discipline AS d " +
            "INNER JOIN discipline_specialism AS ds ON d.id = ds.discipline_id " +
            "INNER JOIN specialism AS s ON ds.specialism_id = s.id where d.id = ?";
    private static final String DISCIPLINE_BY_DISCIPLINE_NAME = "SELECT id, discipline_name, educator_id " +
            "FROM discipline WHERE discipline_name = ?";

    private final JdbcTemplate jdbcTemplate;

    public DisciplineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Discipline entity) {
        jdbcTemplate.update(CREATE, entity.getDisciplineName(), entity.getEducatorId());
    }

    @Override
    public Optional<Discipline> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new DisciplineRowMapper(), id).stream().findFirst();
    }

    @Override
    public void update(Discipline entity) {
        jdbcTemplate.update(UPDATE, entity.getDisciplineName(), entity.getEducatorId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void delete(Discipline entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    @Override
    public List<Discipline> findAll() {
        return jdbcTemplate.query(FIND_ALL, new DisciplineRowMapper());
    }

    public List<Discipline> getDisciplinesBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(DISCIPLINES_BY_SPECIALISM_ID, new DisciplineRowMapper(), specialismId);
    }

    public Optional<Discipline> getDisciplineByDisciplineName(String disciplineName) {
        return jdbcTemplate.query(DISCIPLINE_BY_DISCIPLINE_NAME, new DisciplineRowMapper(), disciplineName)
                .stream()
                .findFirst();
    }


}
