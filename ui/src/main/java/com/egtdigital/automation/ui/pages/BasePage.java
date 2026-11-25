package com.egtdigital.automation.ui.pages;

import com.microsoft.playwright.Page;

/**
 * Common behaviour for page objects. Locators belong to the concrete page classes, never to the
 * step definitions.
 */
public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    public void open(String url) {
        page.navigate(url);
    }
}
