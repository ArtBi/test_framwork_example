package com.petclinic.api.tests;

import com.petclinic.api.assertions.AssertableResponse;
import com.petclinic.api.model.enums.PetStatus;
import com.petclinic.api.model.payloads.PetPayload;
import com.petclinic.api.model.responses.PetCreationResponse;
import com.petclinic.api.service.PetApiService;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.petclinic.api.conditions.Conditions.bodyField;
import static com.petclinic.api.conditions.Conditions.contentType;
import static com.petclinic.api.conditions.Conditions.statusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

@Slf4j
public class PetApiTest extends BaseTest {

    private PetApiService petApiService;
    private PetPayload petPayload;

    @BeforeClass
    public void setUp() {
        petApiService = new PetApiService();
        initializePetPayload();
    }

    protected void initializePetPayload() {
        petPayload = PetPayload.builder()
                .id(getFaker().random().nextInt(1, 100000))
                .name(getFaker().funnyName().name())
                .category(PetPayload.Category.builder()
                        .id(1)
                        .name("Dogs")
                        .build())
                .photoUrls(Collections.singletonList("https://example.com/photo.jpg"))
                .tags(Collections.singletonList(PetPayload.Tag.builder()
                        .id(1)
                        .name("friendly")
                        .build()))
                .status(PetStatus.AVAILABLE)
                .build();
    }

    protected PetCreationResponse createPet() {
        return petApiService.createPet(petPayload)
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("id", not(emptyOrNullString())))
                .shouldHave(bodyField("name", equalTo(petPayload.getName())))
                .shouldHave(bodyField("status", equalTo(petPayload.getStatus().getValue())))
                .as(PetCreationResponse.class);
    }


    @Test
    public void testCreateAndGetPet() {
        PetCreationResponse createdPet = createPet();

        log.info("Getting pet by ID: {}", createdPet.getId());
        AssertableResponse getResponse = petApiService.getPetById(createdPet.getId());
        getResponse
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("id", equalTo(createdPet.getId())))
                .shouldHave(bodyField("name", equalTo(createdPet.getName())))
                .shouldHave(bodyField("status", equalTo(createdPet.getStatus().getValue())))
                .shouldHave(bodyField("category.name", equalTo(createdPet.getCategory().getName())));
    }

    @Test
    public void testUpdatePetStatus() {
        log.info("Creating a new pet");
        PetCreationResponse createdPet = createPet();

        String newStatus = "sold";
        log.info("Updating pet status to: {}", newStatus);
        AssertableResponse updateResponse = petApiService.updatePetStatus(createdPet, newStatus);
        updateResponse
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        log.info("Verifying updated pet status");
        AssertableResponse getResponse = petApiService.getPetById(createdPet.getId());
        getResponse
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("status", equalTo(newStatus)));
    }

    @Test
    public void testFindPetsByStatus() {
        log.info("Finding pets by status: {}", PetStatus.AVAILABLE.getValue());
        AssertableResponse response = petApiService.findPetsByStatus(PetStatus.AVAILABLE.getValue());
        response
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("$.size()", greaterThan(0)));

        log.info("Verifying all pets have the correct status");
        assertThat(response.getResponse().jsonPath().getList("status"))
                .as("Verifying status of all pets")
                .allMatch(status -> status.equals(PetStatus.AVAILABLE.getValue()));
    }

    @Test
    public void testDeletePet() {
        log.info("Creating a new pet");
        Integer petId = createPet().getId();

        log.info("Deleting pet with ID: {}", petId);
        AssertableResponse deleteResponse = petApiService.deletePet(petId);
        deleteResponse.
                shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        log.info("Verifying pet no longer exists");
        AssertableResponse getResponse = petApiService.getPetById(petId);
        getResponse.
                shouldHave(statusCode(404));
    }
} 
