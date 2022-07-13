package ua.com.foxminded.university.dao.implementation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.interfaces.DisciplineDao;
import ua.com.foxminded.university.dao.mappers.DisciplineRowMapper;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.model.lecture.Discipline;

import java.util.List;
import java.util.Optional;

@Repository
public class DisciplineDaoImpl extends AbstractCrudDao<Discipline, Long> implements DisciplineDao {
    public static final String DISCIPLINE_ID = "id";
    public static final String DISCIPLINE_NAME = "discipline_name";
    public static final String DISCIPLINE_EDUCATOR_ID = "educator_id";
    //    public static final String CREATE = "INSERT INTO discipline(discipline_name, educator_id) VALUES (?,?)";
    public static final String RETRIEVE = "SELECT id, discipline_name, educator_id FROM discipline WHERE id = ?";
    public static final String UPDATE = "UPDATE discipline SET discipline_name = ?, educator_id = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM discipline WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, discipline_name, educator_id FROM discipline LIMIT 100";
    private static final String DISCIPLINES_BY_SPECIALISM_ID = "SELECT d.id, d.discipline_name, d.educator_id " +
            "FROM discipline AS d " +
            "INNER JOIN discipline_specialism AS ds ON d.id = ds.discipline_id " +
            "INNER JOIN specialism AS s ON ds.specialism_id = s.id where s.id = ?";
    private static final String DISCIPLINE_BY_DISCIPLINE_NAME = "SELECT id, discipline_name, educator_id " +
            "FROM discipline WHERE discipline_name = ?";
    private static final String INSERT_DISCIPLINE_SPECIALISM = "INSERT INTO discipline_specialism(discipline_id, specialism_id) VALUES (?, ?)";
    private static final String DELETE_DISCIPLINE_SPECIALISM = "DELETE FROM discipline_specialism WHERE discipline_id = ? AND specialism_id = ?";

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public DisciplineDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("discipline").usingGeneratedKeyColumns("id");
    }

    @Override
    public Discipline create(Discipline entity) {
        Number id = jdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource()
                        .addValue(DISCIPLINE_NAME, entity.getDisciplineName())
                        .addValue(DISCIPLINE_EDUCATOR_ID, entity.getEducatorId())
        );
        entity.setId(id.longValue());
        return entity;
    }

    @Override
    public Optional<Discipline> retrieve(Long id) {
        return jdbcTemplate.query(RETRIEVE, new DisciplineRowMapper(), id).stream().findFirst();
    }

    @Override
    public Discipline update(Discipline entity) throws NotFoundException {
        if (1 == jdbcTemplate.update(UPDATE, entity.getDisciplineName(), entity.getEducatorId(), entity.getId())) {
            return entity;
        }
        throw new NotFoundException("Unable to update entity " + entity);
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

    @Override
    public List<Discipline> getDisciplinesBySpecialismId(Long specialismId) {
        return jdbcTemplate.query(DISCIPLINES_BY_SPECIALISM_ID, new DisciplineRowMapper(), specialismId);
    }

    @Override
    public Optional<Discipline> getDisciplineByDisciplineName(String disciplineName) {
        return jdbcTemplate.query(DISCIPLINE_BY_DISCIPLINE_NAME, new DisciplineRowMapper(), disciplineName)
                .stream()
                .findFirst();
    }

    @Override
    public void enroll(Long disciplineId, Long specialismId) {
        jdbcTemplate.update(INSERT_DISCIPLINE_SPECIALISM, disciplineId, specialismId);
    }

    @Override
    public void expel(Long disciplineId, Long specialismId) {
        jdbcTemplate.update(DELETE_DISCIPLINE_SPECIALISM, disciplineId, specialismId);
    }


}
