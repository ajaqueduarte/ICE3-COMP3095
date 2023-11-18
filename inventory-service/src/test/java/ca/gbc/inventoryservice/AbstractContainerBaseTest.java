package ca.gbc.inventoryservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.lifecycle.Startables;

import java.util.stream.Stream;

@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {

    // Assuming the inventory service uses a PostgreSQL database
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("test-inventory")
            .withUsername("test")
            .withPassword("test");

    // You can add other containers here if needed

    @BeforeAll
    static void startContainers() {
        // Start all containers required for tests
        Startables.deepStart(Stream.of(postgresContainer)).join();
        // Here you would set the system properties or environment variables
        // For example, for the database you might do something like:
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @AfterAll
    static void stopContainers() {
        // Stop all started containers
        postgresContainer.stop();
        // Add stop calls for other containers here
    }

    // You can add additional helper methods or setup/teardown methods here as needed.
}
