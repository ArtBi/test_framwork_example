package com.github.artbi.api.template.tests.functional.pet;

import com.github.artbi.api.template.assertions.AssertableResponse;
import com.github.artbi.api.template.model.enums.PetStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.github.artbi.api.template.conditions.Conditions.bodyField;
import static com.github.artbi.api.template.conditions.Conditions.contentType;
import static com.github.artbi.api.template.conditions.Conditions.statusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
@Feature("Pet Management")
public class FindPetTest extends BasePetTest {

    @Test(groups = "regression")
    @Story("Find Pets by Status")
    @Description("Test finding pets by status and verifying the results")
    @Severity(SeverityLevel.NORMAL)
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
}