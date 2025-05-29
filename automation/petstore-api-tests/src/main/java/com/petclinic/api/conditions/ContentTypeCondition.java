package com.petclinic.api.conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentTypeCondition implements Condition {

    private final String contentType;

    @Override
    public void check(Response response) {
        response.then().assertThat().contentType(contentType);
    }

    @Override
    public String toString() {
        return "content type is [%s]".formatted(contentType);
    }
}
