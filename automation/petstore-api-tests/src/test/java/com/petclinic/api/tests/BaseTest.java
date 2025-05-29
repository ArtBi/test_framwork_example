package com.petclinic.api.tests;

import com.github.javafaker.Faker;
import com.petclinic.api.ProjectConfig;
import com.petclinic.api.containers.PetstoreContainer;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@Slf4j
public class BaseTest {
    private static final String API_BASE_URL_PROPERTY = "api.base.url";
    private static final ThreadLocal<Faker> threadLocalFaker = ThreadLocal.withInitial(Faker::new);
    private static PetstoreContainer petstoreContainer;

    @BeforeSuite
    public void startContainer() {
        String baseUrl = initializeBaseUrl();
        configureApiBaseUrl(baseUrl);
    }

    private String initializeBaseUrl() {
        if (ProjectConfig.get().testContainersEnabled()) {
            return startPetstoreContainer();
        } else {
            return ProjectConfig.get().apiBaseUrl();
        }
    }

    private String startPetstoreContainer() {
        log.info("Starting Petstore container");
        petstoreContainer = PetstoreContainer.getInstance();
        petstoreContainer.start();
        return petstoreContainer.getBaseUrl();
    }

    private void configureApiBaseUrl(String baseUrl) {
        log.info("Setting API base URL: {}", baseUrl);
        System.setProperty(API_BASE_URL_PROPERTY, baseUrl);
    }

    @AfterSuite
    public void stopContainer() {
        if (petstoreContainer != null) {
            log.info("Stopping Petstore container");
            petstoreContainer.stop();
        }
    }

    protected Faker getFaker() {
        return threadLocalFaker.get();
    }
}
