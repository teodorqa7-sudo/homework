package com.egtdigital.automation.api.runners;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Run this class from the IDE (JUnit icon) or via `mvn -pl api test`.
 * Never run a .feature file directly - that bypasses these annotations.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/api")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.egtdigital.automation.api.stepdefinitions")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/api.html, json:target/cucumber-reports/api.json")
public class ApiCucumberRunner {
}
