package com.petclinic.api.service;

import com.petclinic.api.assertions.AssertableResponse;
import com.petclinic.api.model.payloads.PetPayload;
import com.petclinic.api.model.responses.PetCreationResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PetApiService extends BaseApiService {

    private static final String PET_ENDPOINT = getBaseUrl() + "/pet";

    public AssertableResponse createPet(PetPayload pet) {
        log.info("Creating pet with name: {}, status: {}", pet.getName(), pet.getStatus());
        AssertableResponse response = post(PET_ENDPOINT, pet);
        log.info("Pet created with response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    public AssertableResponse getPetById(Integer petId) {
        log.info("Getting pet by ID: {}", petId);
        AssertableResponse response = get(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()), Map.of());
        log.info("Get pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    public AssertableResponse updatePet(PetPayload pet) {
        log.info("Updating pet with ID: {}, name: {}, status: {}", pet.getId(), pet.getName(), pet.getStatus());
        AssertableResponse response = put(PET_ENDPOINT, pet);
        log.info("Update pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    public AssertableResponse deletePet(Integer petId) {
        log.info("Deleting pet with ID: {}", petId);
        AssertableResponse response = delete(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()));
        log.info("Delete pet response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    public AssertableResponse findPetsByStatus(String status) {
        log.info("Finding pets by status: {}", status);
        AssertableResponse response = get(PET_ENDPOINT + "/findByStatus", Map.of(), Map.of("status", status));
        log.info("Find pets by status response status: {}", response.getResponse().getStatusCode());
        return response;
    }

    public AssertableResponse updatePetStatus(PetCreationResponse pet, String status) {
        log.info("Updating pet status for pet ID: {}, name: {}, new status: {}", pet.getId(), pet.getName(), status);
        AssertableResponse response = post(PET_ENDPOINT + "/{petId}", Map.of("petId", pet.getId().toString()), Map.of("name", pet.getName(), "status", status));
        log.info("Update pet status response status: {}", response.getResponse().getStatusCode());
        return response;
    }
} 
