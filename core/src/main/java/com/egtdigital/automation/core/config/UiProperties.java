package com.egtdigital.automation.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Bound from {@code application.properties} in the ui module.
 *
 * <p>Passwords deliberately have NO default here: the properties file is the single source of truth,
 * and {@code EnvironmentProvider.resolve(...)} lets an environment variable win over it. Baking a
 * password into compiled code would create a second source that silently diverges.
 *
 * <pre>
 * ui.baseUrl=                                         # empty -> run the bundled offline smoke page
 * ui.backOfficeBaseUrl=https://.../back-office/pages   # @live back-office login
 * ui.backOfficePassword=                              # or BACK_OFFICE_PASSWORD env var
 * ui.sauceDemoBaseUrl=https://www.saucedemo.com        # @live Sauce Demo checkout
 * ui.sauceDemoPassword=                                # or SAUCE_DEMO_PASSWORD env var
 * ui.browser=chromium                                 # chromium | firefox | webkit
 * ui.headless=true                                    # false -> visible browser
 * ui.defaultTimeoutMs=10000
 * ui.slowMoMs=0                                        # ms added to every Playwright action (demo mode)
 * ui.stepDelayMs=0                                     # ms pause after every @browser step (demo mode)
 * </pre>
 */
@ConfigurationProperties(prefix = "ui")
public record UiProperties(
        @DefaultValue("") String baseUrl,
        @DefaultValue("https://automationplayground.com/back-office/pages") String backOfficeBaseUrl,
        @DefaultValue("") String backOfficePassword,
        @DefaultValue("https://www.saucedemo.com") String sauceDemoBaseUrl,
        @DefaultValue("") String sauceDemoPassword,
        @DefaultValue("chromium") String browser,
        @DefaultValue("true") boolean headless,
        @DefaultValue("10000") long defaultTimeoutMs,
        @DefaultValue("0") long slowMoMs,
        @DefaultValue("0") long stepDelayMs
) {
}
