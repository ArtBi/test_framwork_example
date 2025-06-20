package com.github.artbi.api.petstore.tests.functional.pet;

import com.github.artbi.api.petstore.model.payloads.PetPayload;
import com.github.artbi.api.petstore.model.responses.PetCreationResponse;
import com.github.artbi.common.assertions.AssertableResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.github.artbi.common.conditions.Conditions.bodyField;
import static com.github.artbi.common.conditions.Conditions.contentType;
import static com.github.artbi.common.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@Feature("Pet Management")
public class UpdatePetTest extends BasePetTest {

    @Test(groups = "regression")
    @Story("Update Pet Status")
    @Description("Test updating a pet's status and verifying the change")
    @Severity(SeverityLevel.NORMAL)
    public void shouldUpdatePetStatusAndReturnUpdatedDetails() {
        // Given
        log.info("Creating a new pet");
        PetPayload payload = getPetPayload();
        PetCreationResponse createdPet = createPet(payload);
        String newStatus = "sold";

        // When
        log.info("Updating pet status to: {}", newStatus);
        AssertableResponse updateResponse = petApiService.updatePetStatus(createdPet, newStatus);

        // Then
        updateResponse.shouldHave(statusCode(200))
                .shouldHave(contentType("application/json"));

        log.info("Verifying updated pet status");
        AssertableResponse getResponse = petApiService.getPetById(createdPet.getId());
        getResponse.shouldHave(statusCode(200))
                .shouldHave(bodyField("status", equalTo(newStatus)));
    }
}
