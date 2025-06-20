package com.github.artbi.api.petstore.tests.functional.pet;

import com.github.artbi.api.petstore.model.payloads.PetPayload;
import com.github.artbi.common.assertions.AssertableResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.github.artbi.common.conditions.Conditions.contentType;
import static com.github.artbi.common.conditions.Conditions.statusCode;

@Slf4j
@Feature("Pet Management")
public class DeletePetTest extends BasePetTest {

    @Test(groups = "regression")
    @Story("Delete Pet")
    @Description("Test deleting a pet and verifying it no longer exists")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeletePetAndReturnNotFoundOnRetrieval() {
        // Given
        log.info("Creating a new pet");
        PetPayload payload = getPetPayload();
        Integer petId = createPet(payload).getId();

        // When
        log.info("Deleting pet with ID: {}", petId);
        AssertableResponse deleteResponse = petApiService.deletePet(petId);

        // Then
        deleteResponse.shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        log.info("Verifying pet no longer exists");
        AssertableResponse getResponse = petApiService.getPetById(petId);
        getResponse.shouldHave(statusCode(404));
    }
}
