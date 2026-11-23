package com.egtdigital.automation.ui.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Homework step definitions: intentionally print only, no logic and no real API/UI calls.
 * Real implementations are added in later lectures.
 */
public class BankAccountStepDef {

    @Given("the current bank account balance is {double}")
    public void theCurrentBankAccountBalanceIs(double balance) {
        System.out.println("Current bank account balance is: " + balance);
    }

    @When("the player selects the bank account payment method")
    public void thePlayerSelectsTheBankAccountPaymentMethod() {
        System.out.println("The player selects the bank account payment method");
    }

    @And("the player confirms the deposit amount of {double}")
    public void thePlayerConfirmsTheDepositAmountOf(double amount) {
        System.out.println("The player confirms the deposit amount of: " + amount);
    }

    @And("the player submits the deposit request")
    public void thePlayerSubmitsTheDepositRequest() {
        System.out.println("The player submits the deposit request");
    }

    @Then("the deposit is processed successfully")
    public void theDepositIsProcessedSuccessfully() {
        System.out.println("The deposit is processed successfully");
    }

    @And("the bank account balance should be {double}")
    public void theBankAccountBalanceShouldBe(double expectedBalance) {
        System.out.println("The bank account balance should be: " + expectedBalance);
    }
}
