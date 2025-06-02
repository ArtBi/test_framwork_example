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
public class UpdatePetTest extends BasePetTest {

  @Test(groups = "regression")
  @Story("Update Pet Status")
  @Description("Test updating a pet's status and verifying the change")
  @Severity(SeverityLevel.NORMAL)
  public void testUpdatePetStatus() {
    log.info("Creating a new pet");
    PetCreationResponse createdPet = createPet();

    String newStatus = "sold";
    log.info("Updating pet status to: {}", newStatus);
    AssertableResponse updateResponse = petApiService.updatePetStatus(createdPet, newStatus);
    updateResponse.shouldHave(statusCode(200)).shouldHave(contentType("application/json"));

    log.info("Verifying updated pet status");
    AssertableResponse getResponse = petApiService.getPetById(createdPet.getId());
    getResponse.shouldHave(statusCode(200)).shouldHave(bodyField("status", equalTo(newStatus)));
  }
}
