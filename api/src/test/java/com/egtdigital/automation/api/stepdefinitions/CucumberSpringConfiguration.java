package com.egtdigital.automation.api.stepdefinitions;

import com.egtdigital.automation.core.config.ApiProperties;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

/**
 * Entry point for Cucumber Spring. Must live in the glue package so the Cucumber
 * engine finds exactly one @CucumberContextConfiguration.
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfiguration.ApiTestConfiguration.class)
public class CucumberSpringConfiguration {

    @Configuration
    @PropertySource("classpath:application.properties")
    @EnableConfigurationProperties(ApiProperties.class)
    @ComponentScan(basePackages = {"com.egtdigital.automation.core", "com.egtdigital.automation.api"})
    static class ApiTestConfiguration {
    }
}
