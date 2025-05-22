package com.petclinic.api.service;

import com.petclinic.api.assertions.AssertableResponse;
import com.petclinic.api.model.payloads.PetPayload;
import com.petclinic.api.model.responses.PetCreationResponse;

import java.util.Map;

public class PetApiService extends BaseApiService {

    private static final String PET_ENDPOINT = getBaseUrl() + "/pet";

    public AssertableResponse createPet(PetPayload pet) {
        return post(PET_ENDPOINT, pet);
    }

    public AssertableResponse getPetById(Integer petId) {
        return get(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()), Map.of());
    }

    public AssertableResponse updatePet(PetPayload pet) {
        return put(PET_ENDPOINT, pet);
    }

    public AssertableResponse deletePet(Integer petId) {
        return delete(PET_ENDPOINT + "/{petId}", Map.of("petId", petId.toString()));
    }

    public AssertableResponse findPetsByStatus(String status) {
        return get(PET_ENDPOINT + "/findByStatus", Map.of(), Map.of("status", status));
    }

    public AssertableResponse updatePetStatus(PetCreationResponse pet, String status) {
        return post(PET_ENDPOINT + "/{petId}", Map.of("petId", pet.getId().toString()), Map.of("name", pet.getName(), "status", status));
    }
} 