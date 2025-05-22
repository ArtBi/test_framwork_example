package com.petclinic.api.service;

import com.petclinic.api.assertions.AssertableResponse;
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
    //    private static final String BASE_URL = "http://petclinic.swagger.io/v2";
    private static final String BASE_URL = "http://localhost:8080/api/v3";

    protected static String getBaseUrl() {
        return BASE_URL;
    }

    protected RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        boolean enableLogging = Boolean.parseBoolean(System.getProperty("logging", "true"));
        if (enableLogging) {
            log.info("Enabling logging");
            return List.of(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else {
            return List.of();
        }
    }

    protected AssertableResponse get(String endpoint) {
        Response response = setup()
                .when()
                .get(endpoint);
        return new AssertableResponse(response);
    }

    protected AssertableResponse get(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
        Response response = setup()
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .get(endpoint);
        return new AssertableResponse(response);
    }


    protected AssertableResponse post(String endpoint, Object body) {
        Response response = setup()
                .body(body)
                .when()
                .post(endpoint);
        return new AssertableResponse(response);
    }

    protected AssertableResponse post(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
        Response response = setup()
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .post(endpoint);
        return new AssertableResponse(response);
    }

    protected AssertableResponse put(String endpoint, Object body) {
        Response response = setup()
                .body(body)
                .when()
                .put(endpoint);
        return new AssertableResponse(response);
    }

    protected AssertableResponse delete(String endpoint, Map<String, String> pathParams) {
        Response response = setup()
                .pathParams(pathParams)
                .when()
                .delete(endpoint);
        return new AssertableResponse(response);
    }
}