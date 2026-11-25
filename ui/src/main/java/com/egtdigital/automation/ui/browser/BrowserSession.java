package com.egtdigital.automation.ui.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * One Playwright stack for one scenario: Playwright -> Browser -> Context -> Page.
 *
 * <p>A fresh instance is created per scenario by {@link BrowserManager#start} and closed by
 * {@code UiHooks#stopBrowser()}, so no browser state is ever shared between scenarios or threads.
 */
public class BrowserSession implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(BrowserSession.class);

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;

    BrowserSession(Playwright playwright, Browser browser, BrowserContext context, Page page) {
        this.playwright = playwright;
        this.browser = browser;
        this.context = context;
        this.page = page;
    }

    public Page page() {
        return page;
    }

    /**
     * Shuts the stack down innermost first. Every resource is attempted independently: if
     * {@code context.close()} throws, the browser and the Playwright driver are still closed,
     * otherwise a Chromium process would outlive the JVM and pile up across scenarios.
     *
     * <p>Failures are logged rather than rethrown: a teardown problem must not turn a passing
     * scenario red, but it must not vanish silently either.
     */
    @Override
    public void close() {
        closeQuietly("browser context", context::close);
        closeQuietly("browser", browser::close);
        closeQuietly("playwright driver", playwright::close);
    }

    private static void closeQuietly(String resource, Runnable closeAction) {
        try {
            closeAction.run();
        } catch (RuntimeException e) {
            LOG.warn("Failed to close {} - the scenario result is unaffected", resource, e);
        }
    }
}
