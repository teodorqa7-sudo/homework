package com.egtdigital.automation.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Petstore {@code User} resource (https://petstore.swagger.io/#/user).
 *
 * <p>Kept as a mutable Lombok class so it can be built with {@code builder()} and still be
 * deserialised by Jackson (no-args constructor) and compared recursively by AssertJ.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;
}
