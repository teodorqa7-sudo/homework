package com.egtdigital.automation.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Back-office login page (Automation Playground demo site).
 *
 * <p>The page has no {@code id} and no {@code data-testid} attributes, so the locators use the
 * {@code name} attribute, the ARIA role and the accessible name. Each one was verified to match
 * exactly one element - see {@code docs/locators/back-office-login-locators.md}.
 *
 * <p>Note: the "Login" control is an {@code <a href="index.html">}, not a button, and the form has no
 * {@code action} - clicking it simply navigates to the dashboard.
 */
public class LoginPage extends BasePage {

    private static final String LOGIN_PATH = "/login.html";
    private static final String EMAIL = "input[name='email']";
    private static final String PASSWORD = "input[name='password']";
    private static final String LOGIN_LINK = "a.btn.btn-success";

    public LoginPage(Page page) {
        super(page);
    }

    public void openAt(String baseUrl) {
        open(withoutTrailingSlash(baseUrl) + LOGIN_PATH);
    }

    public Locator heading() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Please Sign In"));
    }

    public Locator email() {
        return page.locator(EMAIL);
    }

    public Locator password() {
        return page.locator(PASSWORD);
    }

    public Locator rememberMe() {
        return page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Remember Me"));
    }

    public Locator loginLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login"));
    }

    public void login(String email, String password) {
        email().fill(email);
        password().fill(password);
        loginLink().click();
    }

    private static String withoutTrailingSlash(String baseUrl) {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}
