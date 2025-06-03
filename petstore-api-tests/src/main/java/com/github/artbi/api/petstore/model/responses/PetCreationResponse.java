/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.model.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.artbi.api.petstore.model.enums.PetStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetCreationResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty("category")
    private Category category;

    @JsonProperty(value = "photoUrls", required = true)
    private List<String> photoUrls;

    @JsonProperty("tags")
    private List<Tag> tags;

    @JsonProperty("status")
    private PetStatus status;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Category {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("name")
        private String name;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Tag {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("name")
        private String name;
    }
}
