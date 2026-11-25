@ui @homework @browser
Feature: Bank account deposit

  As a player
  I want to deposit using the bank account payment method
  So that my balance is topped up

  Background:
    Given the current bank account balance is 100.00

  Scenario: Deposit with the bank account payment method
    When the player selects the bank account payment method
    And the player confirms the deposit amount of 25.00
    And the player submits the deposit request
    Then the deposit is processed successfully
    And the bank account balance should be 125.00
