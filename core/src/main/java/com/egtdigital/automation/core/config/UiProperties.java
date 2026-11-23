package com.egtdigital.automation.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Bound from {@code application.properties} in the ui module.
 *
 * <pre>
 * ui.baseUrl=            # empty -> run the bundled offline smoke page
 * ui.browser=chromium    # chromium | firefox | webkit
 * ui.headless=true
 * ui.defaultTimeoutMs=10000
 * </pre>
 */
@ConfigurationProperties(prefix = "ui")
public record UiProperties(
        @DefaultValue("") String baseUrl,
        @DefaultValue("chromium") String browser,
        @DefaultValue("true") boolean headless,
        @DefaultValue("10000") long defaultTimeoutMs
) {
}
