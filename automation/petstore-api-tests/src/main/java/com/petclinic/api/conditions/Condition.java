package com.petclinic.api.conditions;

import io.restassured.response.Response;

public interface Condition {
    void check(Response response);

    @Override
    String toString();
}
