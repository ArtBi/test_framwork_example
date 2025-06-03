/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.tests;

import com.github.artbi.api.petstore.config.PetstoreConfig;
import com.github.artbi.api.petstore.containers.PetstoreContainer;
import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Optional;

@Slf4j
public class BaseTest {
    private static final String API_BASE_URL_PROPERTY = "api.base.url";
    private static final ThreadLocal<Faker> threadLocalFaker = ThreadLocal.withInitial(Faker::new);
    private static PetstoreContainer petstoreContainer;

    @BeforeSuite
    public void startContainer() {
        String baseUrl = initializeBaseUrl();
        configureApiBaseUrl(baseUrl);
        Allure.addAttachment("API Base URL", baseUrl);
    }

    @Step("Initialize base URL for API tests")
    private String initializeBaseUrl() {
        if (PetstoreConfig.get().testContainersEnabled()) {
            return startPetstoreContainer();
        } else {
            return PetstoreConfig.get().apiBaseUrl();
        }
    }

    @Step("Start Petstore container")
    private String startPetstoreContainer() {
        log.info("Starting Petstore container");
        petstoreContainer = PetstoreContainer.getInstance();
        petstoreContainer.start();
        return petstoreContainer.getBaseUrl();
    }

    @Step("Configure API base URL: {baseUrl}")
    private void configureApiBaseUrl(String baseUrl) {
        log.info("Setting API base URL: {}", baseUrl);
        System.setProperty(API_BASE_URL_PROPERTY, baseUrl);
    }

    @AfterSuite
    public void stopContainer() {
        log.info("Stopping Petstore container");
        Optional.ofNullable(petstoreContainer).ifPresent(PetstoreContainer::stop);
    }

    @Step("Get Faker instance for test data generation")
    protected Faker getFaker() {
        return threadLocalFaker.get();
    }
}
