package com.egtdigital.automation.api.client;

import com.egtdigital.automation.common.dto.UserDto;
import com.egtdigital.automation.common.util.JsonUtils;
import io.restassured.response.Response;

/**
 * Petstore {@code /user} resource.
 *
 * <p>Note: {@code POST /user} answers with an {@code ApiResponse} envelope, not with the user, so the
 * created object has to be kept by the caller and read back through {@link #getByUsername(String)}.
 */
public class UserApiClient extends BaseApiClient {

    private static final String USER_PATH = "/user";
    private static final String USER_BY_USERNAME_PATH = "/user/{username}";

    public UserApiClient(String baseUri) {
        super(baseUri);
    }

    public Response create(UserDto user) {
        return request()
                .body(JsonUtils.toJson(user))
                .post(USER_PATH);
    }

    public Response getByUsername(String username) {
        return request()
                .pathParam("username", username)
                .get(USER_BY_USERNAME_PATH);
    }
}
