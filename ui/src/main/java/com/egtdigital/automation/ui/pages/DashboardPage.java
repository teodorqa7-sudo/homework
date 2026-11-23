package com.egtdigital.automation.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Back-office dashboard shown after the login link is clicked.
 *
 * <p>The document title is identical to the login page's, so the single {@code <h1>Dashboard</h1>}
 * is the reliable marker that navigation happened.
 */
public class DashboardPage extends BasePage {

    public DashboardPage(Page page) {
        super(page);
    }

    public Locator heading() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Dashboard"));
    }
}
