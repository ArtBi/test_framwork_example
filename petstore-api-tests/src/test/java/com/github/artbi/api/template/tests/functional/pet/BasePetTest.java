/*
 * Copyright (c) 2025
 */
package com.github.artbi.api.template.tests.functional.pet;

import com.github.artbi.api.template.model.enums.PetStatus;
import com.github.artbi.api.template.model.payloads.PetPayload;
import com.github.artbi.api.template.model.responses.PetCreationResponse;
import com.github.artbi.api.template.service.PetApiService;
import com.github.artbi.api.template.tests.BaseTest;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;

import java.util.Collections;

import static com.github.artbi.api.template.conditions.Conditions.bodyField;
import static com.github.artbi.api.template.conditions.Conditions.contentType;
import static com.github.artbi.api.template.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

@Slf4j
public class BasePetTest extends BaseTest {

  protected PetApiService petApiService;
  protected PetPayload petPayload;

  @BeforeClass
  public void setUp() {
    petApiService = new PetApiService();
    initializePetPayload();
  }

  @Step("Initialize pet payload with random data")
  protected void initializePetPayload() {
    petPayload =
            PetPayload.builder()
                    .id(getFaker().random().nextInt(1, 100000))
                    .name(getFaker().funnyName().name())
                    .category(PetPayload.Category.builder().id(1).name("Dogs").build())
                    .photoUrls(Collections.singletonList("https://example.com/photo.jpg"))
                    .tags(
                            Collections.singletonList(PetPayload.Tag.builder().id(1).name("friendly").build()))
                    .status(PetStatus.AVAILABLE)
                    .build();
  }

  @Step("Create a new pet")
  protected PetCreationResponse createPet() {
    return petApiService
            .createPet(petPayload)
            .shouldHave(statusCode(200))
            .shouldHave(contentType("application/json"))
            .shouldHave(bodyField("id", not(emptyOrNullString())))
            .shouldHave(bodyField("name", equalTo(petPayload.getName())))
            .shouldHave(bodyField("status", equalTo(petPayload.getStatus().getValue())))
            .as(PetCreationResponse.class);
  }
}
