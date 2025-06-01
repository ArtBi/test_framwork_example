package com.github.artbi.api.template.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {

    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Override
    public String toString() {
        return "status code is %d".formatted(statusCode);
    }
}