package ua.com.foxminded.university.utils;


import ua.com.foxminded.university.dao.datasourse.Datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static ua.com.foxminded.university.utils.TransactionUtils.transaction;

public class SqlUtils {
    public static void executeSqlScript(Connection connection, String sqlScript) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlScript);
        }
    }

    public static void executeSqlScriptFile(Datasource datasource, String fileName) throws SQLException {
        transaction(datasource, (connection) ->
                SqlUtils.executeSqlScript(connection, ResourceUtils.loadTextFileFromResources(fileName)));
    }
}
