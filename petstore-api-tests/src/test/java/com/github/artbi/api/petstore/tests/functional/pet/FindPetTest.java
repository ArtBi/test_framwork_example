/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.petstore.tests.functional.pet;

import com.github.artbi.api.petstore.model.enums.PetStatus;
import com.github.artbi.common.assertions.AssertableResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.github.artbi.common.conditions.Conditions.bodyField;
import static com.github.artbi.common.conditions.Conditions.contentType;
import static com.github.artbi.common.conditions.Conditions.statusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
@Feature("Pet Management")
public class FindPetTest extends BasePetTest {

    @DataProvider
    public Object[] statuses() {
        return new Object[]{PetStatus.AVAILABLE, PetStatus.PENDING, PetStatus.SOLD};
    }

    @Test(groups = "regression", dataProvider = "statuses")
    @Story("Find Pets by Status")
    @Description("Test finding pets by status and verifying the results")
    @Severity(SeverityLevel.NORMAL)
    public void testFindPetsByStatus(PetStatus status) {
        log.info("Finding pets by status: {}", status.getValue());
        AssertableResponse response = petApiService.findPetsByStatus(status.getValue());
        response.shouldHave(statusCode(200)).shouldHave(contentType("application/json"))
                .shouldHave(bodyField("$.size()", greaterThan(0)));

        log.info("Verifying all pets have the correct status");
        assertThat(response.getResponse().jsonPath().getList("status")).as("Verifying status of all pets")
                .allMatch(actualStatus -> actualStatus.equals(status.getValue()));
    }
}
