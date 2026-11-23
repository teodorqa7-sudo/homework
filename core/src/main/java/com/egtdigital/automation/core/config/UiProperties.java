package com.egtdigital.automation.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Bound from {@code application.properties} in the ui module.
 *
 * <pre>
 * ui.baseUrl=                                         # empty -> run the bundled offline smoke page
 * ui.backOfficeBaseUrl=https://.../back-office/pages   # @live back-office login
 * ui.sauceDemoBaseUrl=https://www.saucedemo.com        # @live Sauce Demo checkout
 * ui.sauceDemoPassword=secret_sauce                    # public demo password; override via SAUCE_DEMO_PASSWORD
 * ui.browser=chromium                                 # chromium | firefox | webkit
 * ui.headless=true
 * ui.defaultTimeoutMs=10000
 * </pre>
 */
@ConfigurationProperties(prefix = "ui")
public record UiProperties(
        @DefaultValue("") String baseUrl,
        @DefaultValue("https://automationplayground.com/back-office/pages") String backOfficeBaseUrl,
        @DefaultValue("https://www.saucedemo.com") String sauceDemoBaseUrl,
        @DefaultValue("secret_sauce") String sauceDemoPassword,
        @DefaultValue("chromium") String browser,
        @DefaultValue("true") boolean headless,
        @DefaultValue("10000") long defaultTimeoutMs
) {
}
