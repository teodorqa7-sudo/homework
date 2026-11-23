@ui @login @live @browser
Feature: Back-office login

  As a back-office user
  I want to sign in with my e-mail and password
  So that I can reach the admin dashboard

  Background:
    Given the back-office login page is open

  Scenario: The login form is displayed
    Then the login form is displayed

  Scenario: Signing in opens the dashboard
    When I sign in as "mentor@example.com" with password "Secret123!"
    Then the back-office dashboard is displayed
