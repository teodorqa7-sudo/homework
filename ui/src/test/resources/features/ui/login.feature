@ui @login @browser
Feature: Back-office login

  As a back-office user
  I want to sign in with my e-mail and password
  So that I can reach the admin dashboard

  Background:
    Given the back-office login page is open

  Scenario: The login form is displayed
    Then the login form is displayed

  # The demo site performs no real authentication: the "Login" anchor only navigates to index.html.
  # The password is deliberately NOT part of this file - it is resolved from BACK_OFFICE_PASSWORD
  # (environment variable) or ui.backOfficePassword (properties file).
  Scenario: Signing in opens the dashboard
    When I sign in as "mentor@example.com"
    Then the back-office dashboard is displayed
