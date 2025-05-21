package com.petclinic.api.response;

import com.petclinic.api.conditions.Condition;
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
} 