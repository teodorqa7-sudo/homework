package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Sauce Demo login page ({@code https://www.saucedemo.com/}).
 *
 * <p>The site uses {@code data-test} attributes (not {@code data-testid}); the ids below are stable
 * and also carry matching {@code data-test} values.
 */
public class SauceLoginPage extends BasePage {

    private static final String USERNAME = "#user-name";
    private static final String PASSWORD = "#password";
    private static final String LOGIN_BUTTON = "#login-button";

    public SauceLoginPage(Page page) {
        super(page);
    }

    public void openAt(String baseUrl) {
        open(baseUrl);
    }

    public Locator username() {
        return page.locator(USERNAME);
    }

    public Locator password() {
        return page.locator(PASSWORD);
    }

    public Locator loginButton() {
        return page.locator(LOGIN_BUTTON);
    }

    public void login(String username, String password) {
        username().fill(username);
        password().fill(password);
        loginButton().click();
    }
}
