package com.egtdigital.automation.core.context;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Per-scenario shared state. Cucumber Spring provides the {@code cucumber-glue} scope, so a fresh
 * instance exists for every scenario and for every thread.
 */
@Component
@Scope("cucumber-glue")
public class ScenarioContext {

    private final Map<String, Object> values = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        values.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = values.get(key);
        if (value == null) {
            throw new IllegalStateException("No value stored in the scenario context for key '" + key + "'");
        }
        return type.cast(value);
    }

    public Optional<Object> find(String key) {
        return Optional.ofNullable(values.get(key));
    }

    public void clear() {
        values.clear();
    }
}
