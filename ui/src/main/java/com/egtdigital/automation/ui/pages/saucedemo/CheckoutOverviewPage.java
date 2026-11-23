package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Checkout step two - order overview ({@code /checkout-step-two.html}).
 */
public class CheckoutOverviewPage extends BasePage {

    private static final String TITLE = "[data-test='title']";
    private static final String TOTAL = "[data-test='total-label']";
    private static final String FINISH = "#finish";

    public CheckoutOverviewPage(Page page) {
        super(page);
    }

    public Locator pageTitle() {
        return page.locator(TITLE);
    }

    public Locator total() {
        return page.locator(TOTAL);
    }

    public void finish() {
        page.locator(FINISH).click();
    }
}
