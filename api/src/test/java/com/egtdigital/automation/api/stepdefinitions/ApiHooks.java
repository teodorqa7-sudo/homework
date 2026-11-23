package com.egtdigital.automation.api.stepdefinitions;

import com.egtdigital.automation.api.support.StubApiServer;
import com.egtdigital.automation.core.config.ApiProperties;
import com.egtdigital.automation.core.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class ApiHooks {

    public static final String BASE_URI_KEY = "api.baseUri";
    public static final String RESPONSE_KEY = "api.response";

    private final StubApiServer stubApiServer;
    private final ApiProperties properties;
    private final ScenarioContext context;

    public ApiHooks(StubApiServer stubApiServer, ApiProperties properties, ScenarioContext context) {
        this.stubApiServer = stubApiServer;
        this.properties = properties;
        this.context = context;
    }

    @Before(order = 0)
    public void startStubServer() {
        stubApiServer.start();
        context.put(BASE_URI_KEY, resolveBaseUri());
    }

    @After(order = 0)
    public void cleanUp() {
        stubApiServer.stop();
        RestAssured.reset();
        context.clear();
    }

    private String resolveBaseUri() {
        String configured = properties.baseUrl();
        return (configured == null || configured.isBlank()) ? stubApiServer.baseUri() : configured;
    }
}
