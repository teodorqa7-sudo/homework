@api @smoke
Feature: API smoke checks

  Scenario: Health endpoint reports UP
    When I request the API health endpoint
    Then the response status code should be 200
    And the response body should report status "UP"
