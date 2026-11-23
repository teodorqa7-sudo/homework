@ui @checkout @live @browser
Feature: Sauce Demo checkout

  As an online shopper
  I want to complete an order end to end
  So that I can verify the checkout flow works

  Background:
    Given I am signed in to the Sauce Demo shop as "standard_user"

  Scenario: Complete an order with a single product
    When I add the "Sauce Labs Backpack" to the cart
    And I open the cart
    Then the cart contains "Sauce Labs Backpack"
    When I check out with first name "Teodor", last name "Iliev" and postal code "1000"
    And I confirm the order
    Then the order is placed successfully
