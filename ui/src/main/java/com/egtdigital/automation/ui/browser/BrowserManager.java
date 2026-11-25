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
 * Factory for a per-scenario Playwright stack.
 *
 * <p>Deliberately <b>stateless</b>. This is a singleton Spring bean, so it must never hold a
 * {@link Playwright}, {@link Browser}, {@link BrowserContext} or {@link Page}: that would put one
 * scenario's state in an application-wide object and make parallel execution close other scenarios'
 * browsers. Everything lives in the returned {@link BrowserSession}, which is stored in the
 * scenario-scoped {@code ScenarioContext} and closed at the end of the scenario.
 */
@Component
public class BrowserManager {

    public BrowserSession start(UiProperties properties) {
        Playwright playwright = Playwright.create();
        try {
            Browser browser = launch(playwright, properties);
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1440, 900));
            Page page = context.newPage();
            page.setDefaultTimeout(properties.defaultTimeoutMs());
            return new BrowserSession(playwright, browser, context, page);
        } catch (RuntimeException e) {
            // never leak a driver process when the launch fails half way
            try {
                playwright.close();
            } catch (RuntimeException closeFailure) {
                e.addSuppressed(closeFailure);
            }
            throw e;
        }
    }

    private static Browser launch(Playwright playwright, UiProperties properties) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(properties.headless())
                .setSlowMo(properties.slowMoMs());

        return switch (properties.browser().toLowerCase(Locale.ROOT)) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '" + properties.browser() + "'. Supported values: chromium, firefox, webkit.");
        };
    }
}
