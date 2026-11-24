# EGT Digital Automation Framework

Multi-module Maven BDD framework: **Cucumber 7 + JUnit 5 (Platform Suite) + Playwright + Rest Assured + Spring**.

## Module layout

| Module | Purpose | Key dependencies |
|---|---|---|
| `common` | Shared DTOs, JSON utils, constants. No test engines. | Jackson, Lombok, SLF4J |
| `core` | Test infrastructure: `@ConfigurationProperties`, scenario-scoped `ScenarioContext`, factories, providers. | Spring Boot starter |
| `ui` | Playwright page objects, Cucumber glue, `UiCucumberRunner`, `features/ui`. | Playwright, Cucumber, JUnit Suite, Spring Test |
| `api` | Rest Assured clients, Cucumber glue, `ApiCucumberRunner`, `features/api`. | Rest Assured, Cucumber, JUnit Suite, Spring Test |

Dependency versions live **only** in the root `pom.xml` (`dependencyManagement` with the Spring Boot BOM + Cucumber BOM). Child modules never declare versions.

## Prerequisites

- JDK 21 (`java -version`)
- Maven 3.9+ (`mvn -v`)

## Build

```
mvn -q clean install
```

## Run the tests

```
# API smoke (fully offline, uses an in-process stub HTTP server)
# -am also builds the common/core modules it depends on
mvn -pl api -am test

# UI smoke (needs Playwright browsers, see below; runs against a bundled local page by default)
mvn -pl ui -am test

# Everything (common + core + ui + api) - offline suite, @live is excluded
mvn test

# Live UI scenarios (need network): back-office login + Sauce Demo checkout
mvn -pl ui -am test "-Dcucumber.filter.tags=@live"

# Live API scenario (needs network): Petstore user create + retrieve
mvn -pl api -am test "-Dcucumber.filter.tags=@live"
```

### Install Playwright browsers (one-off per machine)

The `ui` POM pre-configures the Playwright CLI (`install chromium`), so no CLI quoting is needed:

```
# common/core must be resolvable first (one-off)
mvn -q clean install -DskipTests

# installs chromium
mvn -pl ui exec:java
```

Other browsers — in PowerShell quote the whole `-D` argument, otherwise it is split and Maven reports
`Unknown lifecycle phase ".args=..."`:

```
mvn -pl ui exec:java "-Dexec.args=install firefox"
mvn -pl ui exec:java "-Dexec.args=install webkit"
```

Set `PLAYWRIGHT_BROWSERS_PATH` if browsers must live outside the default cache.

## Pointing tests at a real environment

Edit `ui/src/test/resources/application.properties` and `api/src/test/resources/application.properties`:

```
ui.baseUrl=https://staging.example.com
api.baseUrl=https://staging.example.com
```

Leaving them empty keeps the offline smoke behaviour (bundled page / in-process stub). **Never commit passwords or tokens** — use `EnvironmentProvider.resolve(...)` or environment variables.

## Running from IntelliJ

1. Open `UiCucumberRunner` (or `ApiCucumberRunner`).
2. Right-click the class → **Run 'UiCucumberRunner'**.
3. The run configuration icon must be **JUnit** (letter J), not Cucumber.
4. Do **not** run a `.feature` file directly — the IDE then uses the Cucumber CLI and ignores `@SelectClasspathResource`.
5. Live scenarios are excluded by `cucumber.properties` (`cucumber.filter.tags=not @live`). To run the Sauce Demo checkout from the IDE, run `CheckoutCucumberSuite` — it pins `@checkout` via `@ConfigurationParameter` — or add `-Dcucumber.filter.tags=@checkout` to the run configuration's **VM options** (not program arguments). Classes named `*Suite` are not matched by the surefire include patterns, so `mvn test` stays offline.

## Conventions that keep the build healthy

- `@SelectClasspathResource("features/ui")` is relative to `src/test/resources` — never `src/test/resources/...`.
- GLUE packages are fully qualified and narrow (`...ui.stepdefinitions`, `...api.stepdefinitions`). A wide package such as `com` makes Cucumber scan everything and triggers JNA/ByteBuddy `NoClassDefFoundError`.
- All `org.junit.platform:*` artifacts come from one BOM (Spring Boot's managed `junit-bom`). Never mix versions — that is what causes `NoSuchMethodError`.
- `cucumber-spring` requires a `@CucumberContextConfiguration` class in the glue package **and** Spring Test on the classpath, otherwise you get `CompositeCucumberException` / `BeansException`.
- Surefire is configured to pick up `**/*Runner.java` in addition to the default `*Test` patterns, so `mvn test` actually runs the suites.
- Never send requests from a spec created by `RequestSpecBuilder.build()`. That object is a bare `RequestSpecificationImpl` with a null internal `responseSpecification`, so Rest Assured throws `NullPointerException: Cannot get property 'assertionClosure' on null object` in `applyPathParamsAndSendRequest`. Always build requests from `RestAssured.given()` — see `BaseApiClient#request()`.

## Reports

Cucumber writes `target/cucumber-reports/{ui,api}.html` and `.json` for each module.

## Pre-start checklist

- [ ] Feature files under `src/test/resources/features/{ui,api}/`
- [ ] `UiCucumberRunner` in `ui/src/test/java`, `ApiCucumberRunner` in `api/src/test/java`
- [ ] `@SelectClasspathResource` uses `features/...`
- [ ] GLUE packages fully qualified and narrow
- [ ] No mixed JUnit Platform versions
- [ ] Spring Test present wherever `cucumber-spring` is used
- [ ] Playwright browsers installed for the `ui` module

## Next steps

- Allure or HTML reporting
- Tag strategy (`@smoke`, `@regression`) + `cucumber.filter.tags`
- Parallel execution (`cucumber.execution.parallel.enabled`)
- CI/CD pipeline (Maven commands + report artifacts)
