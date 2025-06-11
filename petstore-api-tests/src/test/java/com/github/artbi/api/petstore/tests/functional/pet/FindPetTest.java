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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
@Feature("Pet Management")
public class FindPetTest extends BasePetTest {

    @DataProvider(name = "petStatusData")
    public Object[][] petStatusData() {
        return new Object[][]{
                {PetStatus.AVAILABLE.getValue(), 200},
                {PetStatus.PENDING.getValue(), 200},
                {PetStatus.SOLD.getValue(), 200}
        };
    }

    @Test(groups = "regression", dataProvider = "petStatusData")
    @Story("Find Pets by Status")
    @Description("Test finding pets by status and verifying the results")
    @Severity(SeverityLevel.NORMAL)
    public void shouldReturnPetsWithMatchingStatus(String status, int expectedStatusCode) {
        // Given
        log.info("Finding pets by status: {}", status);

        // When
        AssertableResponse response = petApiService.findPetsByStatus(status);

        // Then
        response.shouldHave(statusCode(expectedStatusCode))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("$.size()", greaterThan(0)));

        log.info("Verifying all pets have the correct status");
        assertThat(response.getResponse().jsonPath().getList("status")).as("Verifying status of all pets")
                .allMatch(actualStatus -> actualStatus.equals(status));
    }

    @Test(groups = {"regression", "negative"})
    @Story("Find Pets by Status")
    @Description("Test finding pets with invalid status returns proper error response")
    @Severity(SeverityLevel.MINOR)
    public void shouldReturnProperStatusCodeAndMessageForInvalidStatus() {
        // Given
        String invalidStatus = "invalid_status";
        String errorMessage = "Input error: query parameter `status value `%s` is not in the allowable values `[available, pending, sold]`".formatted(invalidStatus);
        log.info("Finding pets by invalid status: {}", invalidStatus);

        // When
        AssertableResponse response = petApiService.findPetsByStatus(invalidStatus);

        // Then
        response.shouldHave(statusCode(400))
                .shouldHave(contentType("application/json"))
                .shouldHave(bodyField("message", equalTo(errorMessage)));

        log.info("Test completed - invalid status properly handled");
    }
}
