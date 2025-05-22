package com.petclinic.api.tests;

import com.petclinic.api.assertions.AssertableResponse;
import com.petclinic.api.model.enums.PetStatus;
import com.petclinic.api.model.payloads.PetPayload;
import com.petclinic.api.model.responses.PetCreationResponse;
import com.petclinic.api.service.PetApiService;
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

        // Отримання улюбленця за ID
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
        // Створення улюбленця
        PetCreationResponse createdPet = createPet();

        // Оновлення статусу
        String newStatus = "sold";
        AssertableResponse updateResponse = petApiService.updatePetStatus(createdPet, newStatus);
        updateResponse
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        // Перевірка оновленого статусу
        AssertableResponse getResponse = petApiService.getPetById(createdPet.getId());
        getResponse
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("status", equalTo(newStatus)));
    }

    @Test
    public void testFindPetsByStatus() {
        // Пошук улюбленців за статусом
        AssertableResponse response = petApiService.findPetsByStatus(PetStatus.AVAILABLE.getValue());
        response
                .shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("$.size()", greaterThan(0)));

        // Перевірка, що всі улюбленці мають правильний статус
        assertThat(response.getResponse().jsonPath().getList("status"))
                .as("Перевірка статусу всіх улюбленців")
                .allMatch(status -> status.equals(PetStatus.AVAILABLE.getValue()));
    }

    @Test
    public void testDeletePet() {
        // Створення улюбленця
        Integer petId = createPet().getId();

        // Видалення улюбленця
        AssertableResponse deleteResponse = petApiService.deletePet(petId);
        deleteResponse.
                shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        // Перевірка, що улюбленця більше немає
        AssertableResponse getResponse = petApiService.getPetById(petId);
        getResponse.
                shouldHave(statusCode(404));
    }
} 