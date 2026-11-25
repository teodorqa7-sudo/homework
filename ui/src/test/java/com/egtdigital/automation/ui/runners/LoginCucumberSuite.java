package com.egtdigital.automation.ui.runners;

import io.cucumber.core.options.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * IDE-only launcher for the live back-office login scenarios.
 *
 * <p>{@code cucumber.properties} excludes {@code @live} from the default suite. A
 * {@link ConfigurationParameter} has higher precedence than that file, so this class can be run
 * straight from the IDE (right-click, Run) without any VM options.
 *
 * <p>Deliberately named {@code *Suite} rather than {@code *Runner}: surefire only picks up classes
 * ending in {@code Runner.java}, {@code Test.java} or {@code Tests.java}, so {@code mvn test} keeps
 * running only the offline suite.
 *
 * <p>Equivalent command line:
 * {@code mvn -pl ui -am test "-Dcucumber.filter.tags=@login"}
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/ui")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.egtdigital.automation.ui.stepdefinitions")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@login")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/login.html, json:target/cucumber-reports/login.json")
public class LoginCucumberSuite {
}
