/*
 * Copyright (c) 2025
 */
package com.github.artbi.common.conditions;

import io.restassured.response.Response;

public interface Condition {
    void check(Response response);

    @Override
    String toString();
}
