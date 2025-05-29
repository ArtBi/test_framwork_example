package com.petclinic.api.tests;

import com.github.javafaker.Faker;
import com.petclinic.api.containers.PetstoreContainer;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@Slf4j
public class BaseTest {
    protected static final PetstoreContainer petstoreContainer = PetstoreContainer.getInstance();
    private static final ThreadLocal<Faker> threadLocalFaker = ThreadLocal.withInitial(Faker::new);

    @BeforeSuite
    public void startContainer() {
        log.info("Starting Petstore container");
        petstoreContainer.start();
        String baseUrl = petstoreContainer.getBaseUrl();
        log.info("Setting API base URL: {}", baseUrl);
        System.setProperty("api.base.url", baseUrl);
    }

    @AfterSuite
    public void stopContainer() {
        log.info("Stopping Petstore container");
        petstoreContainer.stop();
    }

    protected Faker getFaker() {
        return threadLocalFaker.get();
    }

    protected String getPetstoreBaseUrl() {
        return petstoreContainer.getBaseUrl();
    }
}
