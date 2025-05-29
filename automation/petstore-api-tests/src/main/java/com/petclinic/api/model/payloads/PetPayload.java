package com.petclinic.api.model.payloads;

import com.petclinic.api.model.enums.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetPayload {

    private Integer id;

    private String name;

    private Category category;

    private List<String> photoUrls;

    private List<Tag> tags;

    private PetStatus status;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private Integer id;

        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private Integer id;

        private String name;
    }
}