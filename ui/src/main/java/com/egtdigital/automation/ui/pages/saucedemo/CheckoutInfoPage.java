package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
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

    public void fill(String firstName, String lastName, String postalCode) {
        page.locator(FIRST_NAME).fill(firstName);
        page.locator(LAST_NAME).fill(lastName);
        page.locator(POSTAL_CODE).fill(postalCode);
        page.locator(CONTINUE).click();
    }
}
