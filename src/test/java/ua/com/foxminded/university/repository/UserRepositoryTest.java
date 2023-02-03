package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindByUserName() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        Optional<User> actual = repository.findByUserName("maccas82");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindByEmail() {
        User expected = new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        Optional<User> actual = repository.findByEmail("maccas82@gmail.com");

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindAllByUserRole() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );

        List<User> actual = repository.findAllByUserRole(UserRole.ADMIN);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByBirthday() {
        List<User> expected = List.of(
                new User(1L, "maccas82", "pass82", UserRole.ADMIN, "Michael",
                        "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com")
        );

        List<User> actual = repository.findAllByBirthday(LocalDate.parse("1982-06-22"));

        assertEquals(expected, actual);
    }

}