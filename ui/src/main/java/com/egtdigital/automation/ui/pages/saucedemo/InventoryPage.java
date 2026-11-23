package com.egtdigital.automation.ui.pages.saucedemo;

import com.egtdigital.automation.ui.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Sauce Demo product inventory ({@code /inventory.html}).
 *
 * <p>Product cards are matched by their visible text, then the action button is resolved inside the
 * card, so no per-product id has to be hard-coded.
 *
 * <p>{@code pageTitle()} returns the in-page title element, not the browser tab title
 * ({@link BasePage#title()}).
 */
public class InventoryPage extends BasePage {

    private static final String TITLE = "[data-test='title']";
    private static final String INVENTORY_ITEM = "[data-test='inventory-item']";
    private static final String CART_LINK = "[data-test='shopping-cart-link']";
    private static final String CART_BADGE = "[data-test='shopping-cart-badge']";

    public InventoryPage(Page page) {
        super(page);
    }

    public Locator pageTitle() {
        return page.locator(TITLE);
    }

    public Locator cartBadge() {
        return page.locator(CART_BADGE);
    }

    public Locator item(String productName) {
        return page.locator(INVENTORY_ITEM)
                .filter(new Locator.FilterOptions().setHasText(productName));
    }

    public void addToCart(String productName) {
        item(productName)
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Add to cart"))
                .click();
    }

    public void openCart() {
        page.locator(CART_LINK).click();
    }
}
