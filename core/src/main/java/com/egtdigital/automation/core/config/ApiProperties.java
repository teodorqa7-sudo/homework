package com.egtdigital.automation.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Bound from {@code application.properties} in the api module.
 *
 * <pre>
 * api.baseUrl=          # empty -> run against the in-process stub API
 * api.timeoutMs=10000
 * </pre>
 */
@ConfigurationProperties(prefix = "api")
public record ApiProperties(
        @DefaultValue("") String baseUrl,
        @DefaultValue("10000") long timeoutMs
) {
}
