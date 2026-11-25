package com.egtdigital.automation.core.provider;

/**
 * Resolves configuration values with the precedence: environment variable -> system property -> fallback.
 * Keeps secrets out of committed properties files.
 */
public final class EnvironmentProvider {

    private EnvironmentProvider() {
    }

    public static String resolve(String key, String fallback) {
        String fromEnvironment = System.getenv(key);
        if (fromEnvironment != null && !fromEnvironment.isBlank()) {
            return fromEnvironment;
        }
        String fromSystemProperty = System.getProperty(key);
        if (fromSystemProperty != null && !fromSystemProperty.isBlank()) {
            return fromSystemProperty;
        }
        return fallback;
    }
}
