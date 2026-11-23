@api @user @live
Feature: Petstore user lifecycle

  As an API consumer
  I want to create a user and read it back
  So that I can prove the user round-trips through the service unchanged

  Scenario: Create a user and retrieve it by username
    When I create a new petstore user
    And I can retrieve the same user by username
    Then the retrieved user matches the created user using soft assertions
    And the retrieved user matches the created user using recursive comparison
