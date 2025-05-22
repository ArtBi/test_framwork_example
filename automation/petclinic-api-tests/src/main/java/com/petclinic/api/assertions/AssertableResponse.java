package com.petclinic.api.assertions;

import com.petclinic.api.conditions.Condition;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class AssertableResponse {

    private final Response response;

    public AssertableResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public AssertableResponse shouldHave(Condition condition) {
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