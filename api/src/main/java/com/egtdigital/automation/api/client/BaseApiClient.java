package com.egtdigital.automation.api.client;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Base for Rest Assured clients. One instance targets one base URI, so callers pass the resolved
 * environment (or stub) URI in the constructor.
 *
 * <p>Every request is created from {@link RestAssured#given()} because that entry point returns a
 * fully initialised specification. A specification obtained from
 * {@code new RequestSpecBuilder().build()} is a bare {@code RequestSpecificationImpl} whose internal
 * {@code responseSpecification} is never assigned; sending a request with it fails inside
 * {@code RequestSpecificationImpl.applyPathParamsAndSendRequest} with
 * {@code NullPointerException: Cannot get property 'assertionClosure' on null object}.
 *
 * <p>The connect and socket timeouts come from {@code api.timeoutMs}. Rest Assured defaults the
 * socket timeout to 0 (infinite), which would let a non-responding server hang the build forever.
 */
public abstract class BaseApiClient {

    private static final String CONNECTION_TIMEOUT = "http.connection.timeout";
    private static final String SOCKET_TIMEOUT = "http.socket.timeout";

    private final String baseUri;
    private final long timeoutMs;

    protected BaseApiClient(String baseUri, long timeoutMs) {
        this.baseUri = baseUri;
        this.timeoutMs = Math.max(1L, timeoutMs);
    }

    /**
     * @return a fresh, fully initialised and sendable request specification.
     */
    protected RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .config(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam(CONNECTION_TIMEOUT, (int) timeoutMs)
                                .setParam(SOCKET_TIMEOUT, (int) timeoutMs)));
    }
}
