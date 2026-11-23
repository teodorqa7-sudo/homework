package com.egtdigital.automation.api.client;

import io.restassured.response.Response;

public class HealthApiClient extends BaseApiClient {

    private static final String HEALTH_PATH = "/health";

    public HealthApiClient(String baseUri) {
        super(baseUri);
    }

    public Response getHealth() {
        return request().get(HEALTH_PATH);
    }
}
