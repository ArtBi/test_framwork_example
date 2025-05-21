package com.petclinic.api.model.payloads;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PetPayload {

    private Integer id;

    private String name;

    private Category category;

    private List<String> photoUrls;

    private List<Tag> tags;

    private String status;

    @Data
    @Builder
    public static class Category {
        private Integer id;

        private String name;
    }

    @Data
    @Builder
    public static class Tag {
        private Integer id;

        private String name;
    }
}