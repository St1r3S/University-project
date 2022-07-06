package ua.com.foxminded.university;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresDatabaseContainer extends PostgreSQLContainer<PostgresDatabaseContainer> {
    private static final String IMAGE_VERSION = "postgres:13";
    private static PostgresDatabaseContainer container;

    private PostgresDatabaseContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresDatabaseContainer getInstance() {
        if (container == null) {
            container = new PostgresDatabaseContainer();
            container.start();
        }
        return container;
    }
}
