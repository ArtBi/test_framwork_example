package com.github.artbi.api.template.tests.functional.pet;

import com.github.artbi.api.template.assertions.AssertableResponse;
import com.github.artbi.api.template.model.responses.PetCreationResponse;
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
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@Feature("Pet Management")
public class CreateRetrievePetTest extends BasePetTest {

    @Test(groups = "smoke")
    @Story("Create and Retrieve Pet")
    @Description("Test creating a new pet and then retrieving it by ID")
    @Severity(SeverityLevel.CRITICAL)
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
}