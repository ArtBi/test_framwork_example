package com.petclinic.api.assertions;

import com.petclinic.api.conditions.Condition;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertableResponse {

    private final Response response;

    public AssertableResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public AssertableResponse shouldHave(Condition condition) {
        log.info("Checking the condition that {}", condition);
        condition.check(response);
        return this;
    }

    public <T> T as(Class<T> type) {
        return response.as(type);
    }

    public Headers getHeaders() {
        return getResponse().getHeaders();
    }
} 