package com.egtdigital.automation.core.factory;

import com.egtdigital.automation.common.dto.UserDto;
import net.datafaker.Faker;

import java.util.Locale;

/**
 * Builds Petstore users with Faker-generated values.
 *
 * <p>The demo service keeps every user it has ever received, so {@code username} carries a
 * millisecond suffix: a bare {@code faker.internet().username()} can repeat between runs and the
 * retrieval step would then read a record from another run.
 */
public final class UserFactory {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);
    private static final int ACTIVE_USER_STATUS = 1;

    private UserFactory() {
    }

    public static UserDto randomUser() {
        long uniqueSuffix = System.currentTimeMillis();
        String username = "qa_" + sanitise(FAKER.name().firstName()) + "_" + uniqueSuffix;

        return UserDto.builder()
                .id(FAKER.number().numberBetween(1_000_000_000L, 9_999_999_999L))
                .username(username)
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.internet().password(10, 16))
                .phone(FAKER.phoneNumber().phoneNumber())
                .userStatus(ACTIVE_USER_STATUS)
                .build();
    }

    private static String sanitise(String value) {
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }
}
