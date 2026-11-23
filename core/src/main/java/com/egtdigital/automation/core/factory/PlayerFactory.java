package com.egtdigital.automation.core.factory;

import com.egtdigital.automation.common.dto.PlayerDto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Test data factory. Every call returns a unique player so parallel or repeated
 * scenarios never collide on username/email.
 */
public final class PlayerFactory {

    private static final String EMAIL_DOMAIN = "@egt-digital.test";

    private PlayerFactory() {
    }

    public static PlayerDto uniquePlayer() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return PlayerDto.builder()
                .username("qa_player_" + suffix)
                .email("qa_player_" + suffix + EMAIL_DOMAIN)
                .currency("EUR")
                .balance(BigDecimal.ZERO)
                .build();
    }
}
