package com.egtdigital.automation.ui.browser;

import com.egtdigital.automation.core.config.UiProperties;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Owns the Playwright lifecycle for a single scenario: Playwright -> Browser -> Context -> Page.
 * Always closed from the Cucumber @After hook so no browser process leaks between scenarios.
 */
@Component
public class BrowserManager implements AutoCloseable {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public Page start(UiProperties properties) {
        close();
        playwright = Playwright.create();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(properties.headless());

        browser = switch (properties.browser().toLowerCase(Locale.ROOT)) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '" + properties.browser() + "'. Supported values: chromium, firefox, webkit.");
        };

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1440, 900));
        page = context.newPage();
        page.setDefaultTimeout(properties.defaultTimeoutMs());
        return page;
    }

    public Page page() {
        if (page == null) {
            throw new IllegalStateException("Browser has not been started for this scenario");
        }
        return page;
    }

    @Override
    public void close() {
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        page = null;
    }
}
