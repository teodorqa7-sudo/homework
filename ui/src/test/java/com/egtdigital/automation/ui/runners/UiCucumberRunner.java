package com.egtdigital.automation.ui.runners;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Run this class from the IDE (JUnit icon) or via `mvn -pl ui test`.
 * Never run a .feature file directly - that bypasses these annotations.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/ui")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.egtdigital.automation.ui.stepdefinitions")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/ui.html, json:target/cucumber-reports/ui.json")
public class UiCucumberRunner {
}
