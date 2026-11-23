package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Checkout confirmation ({@code /checkout-complete.html}).
 */
public class CheckoutCompletePage extends BasePage {

    private static final String TITLE = "[data-test='title']";
    private static final String COMPLETE_HEADER = "[data-test='complete-header']";
    private static final String COMPLETE_TEXT = "[data-test='complete-text']";

    public CheckoutCompletePage(Page page) {
        super(page);
    }

    public Locator pageTitle() {
        return page.locator(TITLE);
    }

    public Locator header() {
        return page.locator(COMPLETE_HEADER);
    }

    public Locator text() {
        return page.locator(COMPLETE_TEXT);
    }
}
