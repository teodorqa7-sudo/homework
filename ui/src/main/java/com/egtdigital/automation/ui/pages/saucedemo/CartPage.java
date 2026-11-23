package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Sauce Demo cart ({@code /cart.html}).
 */
public class CartPage extends BasePage {

    private static final String TITLE = "[data-test='title']";
    private static final String CART_ITEM = "[data-test='inventory-item']";
    private static final String CHECKOUT = "#checkout";

    public CartPage(Page page) {
        super(page);
    }

    public Locator pageTitle() {
        return page.locator(TITLE);
    }

    public Locator item(String productName) {
        return page.locator(CART_ITEM)
                .filter(new Locator.FilterOptions().setHasText(productName));
    }

    public void checkout() {
        page.locator(CHECKOUT).click();
    }
}
