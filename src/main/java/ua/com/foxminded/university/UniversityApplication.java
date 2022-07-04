package ua.com.foxminded.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.com.foxminded.university.dao.datasourse.Datasource;
import ua.com.foxminded.university.dao.datasourse.SimpleDatasource;
import ua.com.foxminded.university.utils.SqlUtils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static ua.com.foxminded.university.utils.ResourceUtils.loadPropertiesFromResources;

@SpringBootApplication
public class UniversityApplication implements Closeable {
    private final Datasource datasource;

    public UniversityApplication(Datasource datasource) throws SQLException {
        this.datasource = datasource;
        // setup database
        SqlUtils.executeSqlScriptFile(datasource, "sql/init_schema.sql");
    }

    public static void main(String[] args) throws IOException, SQLException {
        Properties databaseProperties = loadPropertiesFromResources("db.properties");
        try (Datasource datasource = new SimpleDatasource(databaseProperties); UniversityApplication universityApplication = new UniversityApplication(datasource)) {
            SpringApplication.run(UniversityApplication.class, args);
        }

    }

    @Override
    public void close() throws IOException {
        try {
            SqlUtils.executeSqlScriptFile(datasource, "sql/drop_schema.sql");
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
