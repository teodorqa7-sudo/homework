package com.egtdigital.automation.ui.stepdefinitions;

import com.egtdigital.automation.core.config.UiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.ui.pages.SmokePage;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UiSmokeSteps {

    private static final String LOCAL_SMOKE_PAGE = "pages/smoke.html";

    private final ScenarioContext context;
    private final UiProperties properties;

    public UiSmokeSteps(ScenarioContext context, UiProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @Given("the UI smoke page is open")
    public void theUiSmokePageIsOpen() {
        smokePage().open(targetUrl());
    }

    @Then("the page heading should be {string}")
    public void thePageHeadingShouldBe(String expectedHeading) {
        assertThat(smokePage().welcomeHeading()).hasText(expectedHeading);
    }

    private SmokePage smokePage() {
        return new SmokePage(context.get(UiHooks.PAGE_KEY, Page.class));
    }

    private String targetUrl() {
        String configured = properties.baseUrl();
        if (configured != null && !configured.isBlank()) {
            return configured;
        }
        URL resource = Objects.requireNonNull(
                getClass().getClassLoader().getResource(LOCAL_SMOKE_PAGE),
                "Bundled smoke page '" + LOCAL_SMOKE_PAGE + "' is missing from the ui test classpath");
        try {
            return Path.of(resource.toURI()).toUri().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to resolve " + LOCAL_SMOKE_PAGE, e);
        }
    }
}
