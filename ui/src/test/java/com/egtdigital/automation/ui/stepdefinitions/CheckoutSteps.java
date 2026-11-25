package com.egtdigital.automation.ui.stepdefinitions;

import com.egtdigital.automation.core.config.UiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.core.provider.EnvironmentProvider;
import com.egtdigital.automation.ui.pages.saucedemo.CartPage;
import com.egtdigital.automation.ui.pages.saucedemo.CheckoutCompletePage;
import com.egtdigital.automation.ui.pages.saucedemo.CheckoutInfoPage;
import com.egtdigital.automation.ui.pages.saucedemo.CheckoutOverviewPage;
import com.egtdigital.automation.ui.pages.saucedemo.InventoryPage;
import com.egtdigital.automation.ui.pages.saucedemo.SauceLoginPage;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * End-to-end checkout on Sauce Demo: add to cart -> cart -> buyer information -> confirm -> verify.
 *
 * <p>Note: Playwright's {@code hasText(String)} is an exact, whitespace-normalized match. Use
 * {@code containsText(...)} or {@code hasText(Pattern)} whenever only part of the text is known.
 */
@RequiredArgsConstructor
public class CheckoutSteps {

    private static final String PASSWORD_ENVIRONMENT_VARIABLE = "SAUCE_DEMO_PASSWORD";
    private static final Pattern MONEY_TOTAL = Pattern.compile("Total: \\$\\d+\\.\\d{2}");

    private final ScenarioContext context;
    private final UiProperties properties;

    @Given("I am signed in to the Sauce Demo shop as {string}")
    public void iAmSignedInToTheSauceDemoShopAs(String username) {
        SauceLoginPage loginPage = new SauceLoginPage(page());
        loginPage.openAt(properties.sauceDemoBaseUrl());
        loginPage.login(username,
                EnvironmentProvider.resolve(PASSWORD_ENVIRONMENT_VARIABLE, properties.sauceDemoPassword()));
        assertThat(new InventoryPage(page()).pageTitle()).hasText("Products");
    }

    @When("I add the {string} to the cart")
    public void iAddTheToTheCart(String productName) {
        InventoryPage inventoryPage = new InventoryPage(page());
        inventoryPage.addToCart(productName);
        assertThat(inventoryPage.cartBadge()).hasText("1");
    }

    @And("I open the cart")
    public void iOpenTheCart() {
        new InventoryPage(page()).openCart();
    }

    @Then("the cart contains {string}")
    public void theCartContains(String productName) {
        CartPage cartPage = new CartPage(page());
        assertThat(cartPage.pageTitle()).hasText("Your Cart");
        assertThat(cartPage.item(productName)).isVisible();
    }

    @When("I check out with first name {string}, last name {string} and postal code {string}")
    public void iCheckOutWithFirstNameLastNameAndPostalCode(String firstName, String lastName, String postalCode) {
        new CartPage(page()).checkout();
        new CheckoutInfoPage(page()).fill(firstName, lastName, postalCode);
    }

    @And("I confirm the order")
    public void iConfirmTheOrder() {
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(page());
        assertThat(overviewPage.pageTitle()).hasText("Checkout: Overview");
        assertThat(overviewPage.total()).hasText(MONEY_TOTAL);
        overviewPage.finish();
    }

    @Then("the order is placed successfully")
    public void theOrderIsPlacedSuccessfully() {
        CheckoutCompletePage completePage = new CheckoutCompletePage(page());
        assertThat(completePage.pageTitle()).hasText("Checkout: Complete!");
        assertThat(completePage.header()).hasText("Thank you for your order!");
        assertThat(completePage.text()).containsText("Your order has been dispatched");
    }

    private Page page() {
        return context.get(UiHooks.PAGE_KEY, Page.class);
    }
}
