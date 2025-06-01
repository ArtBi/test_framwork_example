package com.github.artbi.api.template.service;

import com.github.artbi.api.template.assertions.AssertableResponse;
import com.github.artbi.api.template.config.BaseConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BaseApiService {
    protected static String getBaseUrl() {
        String baseUrl = System.getProperty("api.base.url", BaseConfig.get().apiBaseUrl());
        log.info("Using API base URL: {}", baseUrl);
        return baseUrl;
    }

    protected RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        boolean enableLogging = BaseConfig.get().loggingEnabled();
        if (enableLogging) {
            log.info("Enabling logging");
            return List.of(
                    new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            return List.of(new ResponseLoggingFilter());
        }
    }

    protected AssertableResponse get(String endpoint) {
        log.debug("Executing GET request to endpoint: {}", endpoint);
        Response response = setup().when().get(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }

    protected AssertableResponse get(
            String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
        log.debug(
                "Executing GET request to endpoint: {} with pathParams: {} and queryParams: {}",
                endpoint,
                pathParams,
                queryParams);
        Response response =
                setup().pathParams(pathParams).queryParams(queryParams).when().get(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }

    protected AssertableResponse post(String endpoint, Object body) {
        log.debug("Executing POST request to endpoint: {} with body: {}", endpoint, body);
        Response response = setup().body(body).when().post(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }

    protected AssertableResponse post(
            String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
        log.debug(
                "Executing POST request to endpoint: {} with pathParams: {} and queryParams: {}",
                endpoint,
                pathParams,
                queryParams);
        Response response =
                setup().pathParams(pathParams).queryParams(queryParams).when().post(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }

    protected AssertableResponse put(String endpoint, Object body) {
        log.debug("Executing PUT request to endpoint: {} with body: {}", endpoint, body);
        Response response = setup().body(body).when().put(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }

    protected AssertableResponse delete(String endpoint, Map<String, String> pathParams) {
        log.debug("Executing DELETE request to endpoint: {} with pathParams: {}", endpoint, pathParams);
        Response response = setup().pathParams(pathParams).when().delete(endpoint);
        log.debug("Received response with status code: {}", response.getStatusCode());
        return new AssertableResponse(response);
    }
}