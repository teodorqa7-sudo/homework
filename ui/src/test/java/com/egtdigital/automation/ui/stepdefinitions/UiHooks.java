package com.egtdigital.automation.ui.stepdefinitions;

import com.egtdigital.automation.core.config.UiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.ui.browser.BrowserManager;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class UiHooks {

    public static final String PAGE_KEY = "ui.page";

    private final BrowserManager browserManager;
    private final UiProperties properties;
    private final ScenarioContext context;

    public UiHooks(BrowserManager browserManager, UiProperties properties, ScenarioContext context) {
        this.browserManager = browserManager;
        this.properties = properties;
        this.context = context;
    }

    /**
     * Only scenarios tagged {@code @browser} need a real browser, so console-only scenarios
     * (for example the {@code @homework} feature) run even without Playwright browsers installed.
     */
    @Before(value = "@browser", order = 0)
    public void startBrowser() {
        Page page = browserManager.start(properties);
        context.put(PAGE_KEY, page);
    }

    @After(value = "@browser", order = 0)
    public void stopBrowser() {
        browserManager.close();
        context.clear();
    }
}
