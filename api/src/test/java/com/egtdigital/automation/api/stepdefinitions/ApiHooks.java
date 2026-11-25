package com.egtdigital.automation.api.stepdefinitions;

import com.egtdigital.automation.api.support.StubApiServer;
import com.egtdigital.automation.core.config.ApiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiHooks {

    public static final String BASE_URI_KEY = "api.baseUri";
    public static final String RESPONSE_KEY = "api.response";
    public static final String STUB_SERVER_KEY = "api.stubServer";

    private final ApiProperties properties;
    private final ScenarioContext context;

    /**
     * The stub is scenario state, never an application-wide bean: a fresh instance is created here,
     * kept in the scenario-scoped context and stopped in {@link #cleanUp()}.
     *
     * <p>{@code @live} scenarios talk to a real service, so they skip the stub entirely.
     */
    @Before(value = "not @live", order = 0)
    public void startStubServer() {
        StubApiServer server = new StubApiServer();
        server.start();
        context.put(STUB_SERVER_KEY, server);
        context.put(BASE_URI_KEY, resolveBaseUri(server));
    }

    @After(order = 0)
    public void cleanUp() {
        context.find(STUB_SERVER_KEY)
                .filter(StubApiServer.class::isInstance)
                .map(StubApiServer.class::cast)
                .ifPresent(StubApiServer::stop);
        RestAssured.reset();
        context.clear();
    }

    private String resolveBaseUri(StubApiServer server) {
        String configured = properties.baseUrl();
        return (configured == null || configured.isBlank()) ? server.baseUri() : configured;
    }
}
