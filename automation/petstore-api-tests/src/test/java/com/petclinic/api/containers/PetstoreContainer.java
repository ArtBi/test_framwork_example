package com.petclinic.api.containers;

import com.petclinic.api.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

/**
 * Container for the Swagger Petstore API.
 * This container runs the Swagger Petstore Docker image and exposes port 8080.
 */
@Slf4j
public class PetstoreContainer extends GenericContainer<PetstoreContainer> {

    private static final DockerImageName PETSTORE_IMAGE = DockerImageName.parse("swaggerapi/petstore3:latest");
    private static final int PETSTORE_PORT = 8080;
    private static final Object LOCK = new Object();
    private static volatile PetstoreContainer container;


    /**
     * Creates a new instance of the Petstore container.
     */
    public PetstoreContainer() {
        super(PETSTORE_IMAGE);
        log.info("Initializing Petstore container with image: {}", PETSTORE_IMAGE);
        withExposedPorts(PETSTORE_PORT);
        withReuse(ProjectConfig.get().reuseContainersEnabled());
        log.info("Configuring container to wait for HTTP endpoint: /api/v3/openapi.json");
        waitingFor(Wait.forHttp("/api/v3/openapi.json")
                .forStatusCode(200)
                .withStartupTimeout(Duration.ofMinutes(2)));
    }

    /**
     * Gets a singleton instance of the Petstore container.
     * Thread-safe implementation using double-checked locking.
     *
     * @return the singleton instance of the Petstore container
     */
    public static PetstoreContainer getInstance() {
        if (container == null) {
            synchronized (LOCK) {
                if (container == null) {
                    log.info("Creating new Petstore container instance");
                    container = new PetstoreContainer();
                } else {
                    log.info("Returning existing Petstore container instance (within synchronized block)");
                }
            }
        } else {
            log.info("Returning existing Petstore container instance");
        }
        return container;
    }

    public static void stopContainer() {
        log.info("Stopping Petstore container");
        if (container != null) {
            synchronized (LOCK) {
                if (container != null) {
                    container.stop();
                    container = null;
                }
            }
        }
    }

    /**
     * Gets the base URL for the Petstore API.
     *
     * @return the base URL for the Petstore API
     */
    public String getBaseUrl() {
        String baseUrl = String.format("http://%s:%d/api/v3", getHost(), getMappedPort(PETSTORE_PORT));
        log.info("Generated Petstore API base URL: {}", baseUrl);
        return baseUrl;
    }


}
