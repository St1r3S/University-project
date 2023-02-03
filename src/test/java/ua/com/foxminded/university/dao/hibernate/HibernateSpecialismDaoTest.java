package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.SpecialismDao;
import ua.com.foxminded.university.model.lesson.Specialism;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateSpecialismDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateSpecialismDaoTest {
    @Autowired
    SpecialismDao dao;

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Specialism expected = new Specialism("112");

        dao.save(expected);
        Optional<Specialism> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Specialism expected = new Specialism(1L, "112");

        dao.save(expected);
        Optional<Specialism> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Specialism expected = new Specialism(1L, "122");

        Optional<Specialism> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    void shouldVerifyFindAll() {
        List<Specialism> expected = List.of(
                new Specialism(1L, "122"),
                new Specialism(2L, "125"),
                new Specialism(3L, "127")
        );
        List<Specialism> actual = dao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        List<Specialism> expected = List.of(
                new Specialism(1L, "122"),
                new Specialism(2L, "125")
        );

        List<Specialism> actual = dao.findAllById(List.of(1L, 2L));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyCount() {
        long actual = dao.count();

        assertEquals(3L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        Specialism entity = new Specialism(1L, "122");

        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllByEntities() {
        List<Specialism> entities = List.of(
                new Specialism(1L, "122"),
                new Specialism(2L, "125")
        );
        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
        assertFalse(dao.findById(3L).isPresent());
    }

    @Test
    void shouldVerifyFindBySpecialismName() {
        Specialism expected = new Specialism(1L, "122");

        Optional<Specialism> actual = dao.findBySpecialismName("122");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

}