/*
 * Copyright (c) 2025
 */
package com.github.artbi.common.conditions;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

@UtilityClass
public class Conditions {

    public static StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public static ContentTypeCondition contentType(String contentType) {
        return new ContentTypeCondition(contentType);
    }

    public static BodyFieldCondition bodyField(String jsonPath, Matcher matcher) {
        return new BodyFieldCondition(jsonPath, matcher);
    }
}
