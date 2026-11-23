package com.egtdigital.automation.api.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Base for Rest Assured clients. One instance targets one base URI, so callers pass the
 * resolved environment (or stub) URI in the constructor.
 *
 * <p>Every request is created from {@link RestAssured#given()} because that entry point returns a
 * fully initialised specification. A specification obtained from
 * {@code new RequestSpecBuilder().build()} is a bare {@code RequestSpecificationImpl} whose internal
 * {@code responseSpecification} is never assigned; sending a request with it fails inside
 * {@code RequestSpecificationImpl.applyPathParamsAndSendRequest} with
 * {@code NullPointerException: Cannot get property 'assertionClosure' on null object}.
 */
public abstract class BaseApiClient {

    private final String baseUri;

    protected BaseApiClient(String baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * @return a fresh, fully initialised and sendable request specification.
     */
    protected RequestSpecification request() {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
