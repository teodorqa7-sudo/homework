package com.egtdigital.automation.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SmokePage extends BasePage {

    private static final String WELCOME_HEADING = "#welcome";

    public SmokePage(Page page) {
        super(page);
    }

    public Locator welcomeHeading() {
        return page.locator(WELCOME_HEADING);
    }
}
