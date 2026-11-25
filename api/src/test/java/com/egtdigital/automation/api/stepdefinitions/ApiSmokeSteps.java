package com.egtdigital.automation.api.stepdefinitions;

import com.egtdigital.automation.api.client.HealthApiClient;
import com.egtdigital.automation.core.config.ApiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ApiSmokeSteps {

    private final ScenarioContext context;
    private final ApiProperties properties;

    @When("I request the API health endpoint")
    public void iRequestTheApiHealthEndpoint() {
        String baseUri = context.get(ApiHooks.BASE_URI_KEY, String.class);
        Response response = new HealthApiClient(baseUri, properties.timeoutMs()).getHealth();
        context.put(ApiHooks.RESPONSE_KEY, response);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatus) {
        assertThat(response().statusCode()).isEqualTo(expectedStatus);
    }

    @Then("the response body should report status {string}")
    public void theResponseBodyShouldReportStatus(String expectedStatus) {
        assertThat(response().jsonPath().getString("status")).isEqualTo(expectedStatus);
    }

    private Response response() {
        return context.get(ApiHooks.RESPONSE_KEY, Response.class);
    }
}
