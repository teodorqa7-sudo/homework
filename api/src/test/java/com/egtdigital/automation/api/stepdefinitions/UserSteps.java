package com.egtdigital.automation.api.stepdefinitions;

import com.egtdigital.automation.api.client.UserApiClient;
import com.egtdigital.automation.common.dto.UserDto;
import com.egtdigital.automation.common.util.JsonUtils;
import com.egtdigital.automation.core.config.ApiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.core.factory.UserFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Petstore user lifecycle: create with Faker data -> carry it in the scenario context -> read it back
 * by username -> compare the two objects with soft assertions and with a recursive comparison.
 */
public class UserSteps {

    private static final String CREATED_USER_KEY = "petstore.createdUser";
    private static final String RETRIEVED_USER_KEY = "petstore.retrievedUser";

    private final ScenarioContext context;
    private final ApiProperties properties;

    public UserSteps(ScenarioContext context, ApiProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @When("I create a new petstore user")
    public void iCreateANewPetstoreUser() {
        UserDto user = UserFactory.randomUser();
        Response response = new UserApiClient(properties.petstoreBaseUrl()).create(user);
        assertThat(response.statusCode()).isEqualTo(200);
        context.put(CREATED_USER_KEY, user);
    }

    @And("I can retrieve the same user by username")
    public void iCanRetrieveTheSameUserByUsername() {
        UserDto created = createdUser();
        Response response = new UserApiClient(properties.petstoreBaseUrl())
                .getByUsername(created.getUsername());
        assertThat(response.statusCode()).isEqualTo(200);
        context.put(RETRIEVED_USER_KEY, JsonUtils.fromJson(response.asString(), UserDto.class));
    }

    @Then("the retrieved user matches the created user using soft assertions")
    public void theRetrievedUserMatchesTheCreatedUserUsingSoftAssertions() {
        UserDto expected = createdUser();
        UserDto actual = retrievedUser();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getId()).as("id").isEqualTo(expected.getId());
        softly.assertThat(actual.getUsername()).as("username").isEqualTo(expected.getUsername());
        softly.assertThat(actual.getFirstName()).as("firstName").isEqualTo(expected.getFirstName());
        softly.assertThat(actual.getLastName()).as("lastName").isEqualTo(expected.getLastName());
        softly.assertThat(actual.getEmail()).as("email").isEqualTo(expected.getEmail());
        softly.assertThat(actual.getPassword()).as("password").isEqualTo(expected.getPassword());
        softly.assertThat(actual.getPhone()).as("phone").isEqualTo(expected.getPhone());
        softly.assertThat(actual.getUserStatus()).as("userStatus").isEqualTo(expected.getUserStatus());
        softly.assertAll();
    }

    @Then("the retrieved user matches the created user using recursive comparison")
    public void theRetrievedUserMatchesTheCreatedUserUsingRecursiveComparison() {
        assertThat(retrievedUser())
                .usingRecursiveComparison()
                .isEqualTo(createdUser());
    }

    private UserDto createdUser() {
        return context.get(CREATED_USER_KEY, UserDto.class);
    }

    private UserDto retrievedUser() {
        return context.get(RETRIEVED_USER_KEY, UserDto.class);
    }
}
