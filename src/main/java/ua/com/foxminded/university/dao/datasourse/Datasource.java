package ua.com.foxminded.university.dao.datasourse;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface Datasource extends Closeable {
    Connection getConnection() throws SQLException;
}
