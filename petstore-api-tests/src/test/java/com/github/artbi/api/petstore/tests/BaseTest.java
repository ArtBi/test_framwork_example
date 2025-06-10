package com.github.artbi.api.petstore.tests;

import com.github.artbi.api.petstore.config.PetstoreConfig;
import com.github.artbi.api.petstore.containers.PetstoreContainer;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@Slf4j
public class BaseTest {
    private static final String API_BASE_URL_PROPERTY = "api.base.url";
    private static final ThreadLocal<Faker> threadLocalFaker = ThreadLocal.withInitial(Faker::new);
    private static PetstoreContainer petstoreContainer;

    @BeforeSuite(alwaysRun = true)
    public void startContainer() {
        String baseUrl = initializeBaseUrl();
        log.info("Base URL initialized: {}", baseUrl);
        configureApiBaseUrl(baseUrl);
        Allure.addAttachment("API Base URL", baseUrl);
    }

    @AfterSuite
    public void stopTestContainer() {
        log.info("@AfterSuite: Stopping container");
        log.info("TestContainers enabled: {}", PetstoreConfig.get().testContainersEnabled());
        if (PetstoreConfig.get().testContainersEnabled()) {
            log.info("Stopping container because TestContainers is enabled");
            stopContainer();
        } else {
            log.info("Not stopping container because TestContainers is disabled");
        }
    }

    @Step("Initialize base URL for API tests")
    private String initializeBaseUrl() {
        log.info("Initialize API Base URL");
        if (PetstoreConfig.get().testContainersEnabled()) {
            log.info("TestContainers enabled");
            return startPetstoreContainer();
        } else {
            log.info("TestContainers disabled");
            return PetstoreConfig.get().apiBaseUrl();
        }
    }

    @Step("Start Petstore container")
    private String startPetstoreContainer() {
        log.info("Starting Petstore container");
        petstoreContainer = PetstoreContainer.getInstance();
        log.info("Got Petstore container instance, starting it");
        petstoreContainer.start();
        log.info("Petstore container started");
        String baseUrl = petstoreContainer.getBaseUrl();
        log.info("Petstore container base URL: {}", baseUrl);
        return baseUrl;
    }

    @Step("Configure API base URL: {baseUrl}")
    private void configureApiBaseUrl(String baseUrl) {
        log.info("Setting API base URL: {}", baseUrl);
        System.setProperty(API_BASE_URL_PROPERTY, baseUrl);
    }

    public void stopContainer() {
        log.info("Stopping Petstore container");
        if (petstoreContainer == null) {
            log.info("Petstore container is null, nothing to stop");
        } else {
            log.info("Stopping Petstore container instance");
            petstoreContainer.stop();
            log.info("Petstore container stopped");
        }
    }

    @Step("Get Faker instance for test data generation")
    protected Faker getFaker() {
        return threadLocalFaker.get();
    }
}
