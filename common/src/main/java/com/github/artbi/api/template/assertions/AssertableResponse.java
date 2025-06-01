package com.github.artbi.api.template.assertions;

import com.github.artbi.api.template.conditions.Condition;
import io.qameta.allure.Step;
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

    @Step("Then api response should have {condition}")
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