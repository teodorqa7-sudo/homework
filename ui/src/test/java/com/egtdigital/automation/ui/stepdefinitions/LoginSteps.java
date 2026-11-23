package com.egtdigital.automation.ui.stepdefinitions;

import com.egtdigital.automation.core.config.UiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.ui.pages.DashboardPage;
import com.egtdigital.automation.ui.pages.LoginPage;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginSteps {

    private static final Pattern DASHBOARD_URL = Pattern.compile(".*/index\\.html$");

    private final ScenarioContext context;
    private final UiProperties properties;

    public LoginSteps(ScenarioContext context, UiProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @Given("the back-office login page is open")
    public void theBackOfficeLoginPageIsOpen() {
        loginPage().openAt(properties.backOfficeBaseUrl());
    }

    @Then("the login form is displayed")
    public void theLoginFormIsDisplayed() {
        LoginPage loginPage = loginPage();
        assertThat(loginPage.heading()).isVisible();
        assertThat(loginPage.email()).isVisible();
        assertThat(loginPage.password()).isVisible();
        assertThat(loginPage.loginLink()).isVisible();
    }

    @When("I sign in as {string} with password {string}")
    public void iSignInAsWithPassword(String email, String password) {
        loginPage().login(email, password);
    }

    @Then("the back-office dashboard is displayed")
    public void theBackOfficeDashboardIsDisplayed() {
        assertThat(new DashboardPage(page()).heading()).isVisible();
        assertThat(page()).hasURL(DASHBOARD_URL);
    }

    private Page page() {
        return context.get(UiHooks.PAGE_KEY, Page.class);
    }

    private LoginPage loginPage() {
        return new LoginPage(page());
    }
}
