/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.tests.functional.pet;

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
    public void testDeletePet() {
        log.info("Creating a new pet");
        Integer petId = createPet().getId();

        log.info("Deleting pet with ID: {}", petId);
        AssertableResponse deleteResponse = petApiService.deletePet(petId);
        deleteResponse.shouldHave(statusCode(200)).shouldHave(contentType("application/json"));

        log.info("Verifying pet no longer exists");
        AssertableResponse getResponse = petApiService.getPetById(petId);
        getResponse.shouldHave(statusCode(404));
    }
}
