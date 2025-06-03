/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum PetStatus {
    AVAILABLE("available"), PENDING("pending"), SOLD("sold");

    private final String value;

    PetStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
