package ua.com.foxminded.university;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class UniversityMigrationTest extends BaseDaoTest {

    public static final String SELECT_TABLE_NAMES_QUERY = "select table_name\n" +
            "from information_schema.tables\n" +
            "where table_schema = 'public'";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void shouldCreateTables() {
        List<String> strings = jdbcTemplate.queryForList(SELECT_TABLE_NAMES_QUERY, String.class);
        Assertions.assertTrue(strings.containsAll(Arrays.asList("specialisms",
                "schedule_days", "rooms", "academic_years",
                "disciplines", "groupss", "lessons",
                "users")));
    }
}