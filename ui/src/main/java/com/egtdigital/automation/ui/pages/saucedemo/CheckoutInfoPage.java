package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Checkout step one - buyer information ({@code /checkout-step-one.html}).
 */
public class CheckoutInfoPage extends BasePage {

    private static final String FIRST_NAME = "#first-name";
    private static final String LAST_NAME = "#last-name";
    private static final String POSTAL_CODE = "#postal-code";
    private static final String CONTINUE = "#continue";

    public CheckoutInfoPage(Page page) {
        super(page);
    }

    public Locator firstName() {
        return page.locator(FIRST_NAME);
    }

    public Locator lastName() {
        return page.locator(LAST_NAME);
    }

    public Locator postalCode() {
        return page.locator(POSTAL_CODE);
    }

    public Locator continueButton() {
        return page.locator(CONTINUE);
    }

    public void fill(String firstName, String lastName, String postalCode) {
        firstName().fill(firstName);
        lastName().fill(lastName);
        postalCode().fill(postalCode);
        continueButton().click();
    }
}
