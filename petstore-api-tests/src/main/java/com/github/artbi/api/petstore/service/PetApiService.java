package com.github.artbi.api.petstore.service;

import com.github.artbi.api.petstore.model.payloads.PetPayload;
import com.github.artbi.api.petstore.model.responses.PetCreationResponse;
import com.github.artbi.common.assertions.AssertableResponse;
import com.github.artbi.common.service.BaseApiService;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PetApiService extends BaseApiService {

    private final String PET_ENDPOINT;

    public PetApiService() {
        PET_ENDPOINT = getBaseUrl() + "/pet";
    }

    @Step("Create Pet")
    public AssertableResponse createPet(PetPayload pet) {
        log.info("Creating pet with name: {}, status: {}", pet.getName(), pet.getStatus());
        AssertableResponse response = post(PET_ENDPOINT, pet);
        log.info("Pet created with response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    @Step("Get pet by Id")
    public AssertableResponse getPetById(Integer petId) {
        log.info("Getting pet by ID: {}", petId);
        AssertableResponse response = get(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()), Map.of());
        log.info("Get pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    @Step("Update pet")
    public AssertableResponse updatePet(PetPayload pet) {
        log.info("Updating pet with ID: {}, name: {}, status: {}", pet.getId(), pet.getName(), pet.getStatus());
        AssertableResponse response = put(PET_ENDPOINT, pet);
        log.info("Update pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    @Step("Delete pet")
    public AssertableResponse deletePet(Integer petId) {
        log.info("Deleting pet with ID: {}", petId);
        AssertableResponse response = delete(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()));
        log.info("Delete pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    @Step("Find pets by status")
    public AssertableResponse findPetsByStatus(String status) {
        log.info("Finding pets by status: {}", status);
        AssertableResponse response = get(PET_ENDPOINT + "/findByStatus", Map.of(), Map.of("status", status));
        log.info("Find pets by status response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    @Step("Update pet status")
    public AssertableResponse updatePetStatus(PetCreationResponse pet, String status) {
        log.info("Updating pet status for pet ID: {}, name: {}, new status: {}", pet.getId(), pet.getName(), status);
        AssertableResponse response = post(PET_ENDPOINT + "/{petId}", Map.of("petId", pet.getId().toString()),
                Map.of("name", pet.getName(), "status", status));
        log.info("Update pet status response status: {}", response.getResponse().getStatusCode());
        return response;
    }
}
