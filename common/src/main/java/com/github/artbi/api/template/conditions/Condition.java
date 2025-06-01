package com.github.artbi.api.template.conditions;

import io.restassured.response.Response;

public interface Condition {
    void check(Response response);

    @Override
    String toString();
}