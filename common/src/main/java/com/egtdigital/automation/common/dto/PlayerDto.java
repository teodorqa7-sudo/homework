package com.egtdigital.automation.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Sample shared DTO. Kept as a mutable Lombok class so it can be built with
 * {@link PlayerDto#builder()} and still deserialised by Jackson.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto {

    private String username;
    private String email;
    private String currency;
    private BigDecimal balance;
}
