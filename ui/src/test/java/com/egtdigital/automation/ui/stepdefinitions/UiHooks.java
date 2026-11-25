package com.egtdigital.automation.ui.stepdefinitions;

import com.egtdigital.automation.core.config.UiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import com.egtdigital.automation.ui.browser.BrowserManager;
import com.egtdigital.automation.ui.browser.BrowserSession;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UiHooks {

    public static final String PAGE_KEY = "ui.page";
    public static final String SESSION_KEY = "ui.browserSession";

    private final BrowserManager browserManager;
    private final UiProperties properties;
    private final ScenarioContext context;

    /**
     * Only scenarios tagged {@code @browser} need a real browser, so console-only scenarios
     * (for example the {@code @homework} feature) run even without Playwright browsers installed.
     *
     * <p>The session is stored in the scenario-scoped context, never in the singleton
     * {@link BrowserManager}, so two scenarios can never share a browser.
     */
    @Before(value = "@browser", order = 0)
    public void startBrowser() {
        BrowserSession session = browserManager.start(properties);
        context.put(SESSION_KEY, session);
        context.put(PAGE_KEY, session.page());
    }

    /**
     * Demo mode: {@code ui.stepDelayMs} pauses after every step of a {@code @browser} scenario so a
     * human can follow along. Defaults to 0, which means no delay at all.
     */
    @AfterStep(value = "@browser", order = 0)
    public void pauseAfterStep() throws InterruptedException {
        long delay = properties.stepDelayMs();
        if (delay > 0) {
            Thread.sleep(delay);
        }
    }

    /**
     * Always clears the scenario context, even if closing the browser fails - otherwise a broken
     * teardown would leak state into the next scenario.
     */
    @After(value = "@browser", order = 0)
    public void stopBrowser() {
        try {
            context.find(SESSION_KEY)
                    .filter(BrowserSession.class::isInstance)
                    .map(BrowserSession.class::cast)
                    .ifPresent(BrowserSession::close);
        } finally {
            context.clear();
        }
    }
}
