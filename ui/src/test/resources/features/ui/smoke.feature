@ui @smoke @browser
Feature: UI smoke checks

  As a QA engineer
  I want a minimal browser smoke scenario
  So that I can confirm the Playwright stack is wired correctly

  Scenario: The smoke page renders its welcome heading
    Given the UI smoke page is open
    Then the page heading should be "Welcome"
