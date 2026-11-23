# Locator inventory — Sauce Demo (saucedemo.com) checkout flow

| | |
|---|---|
| **Site** | <https://www.saucedemo.com> |
| **Stack** | React SPA ("Swag Labs"); `document.title` is `Swag Labs` on **every** page |
| **Test hooks** | **`data-test`, not `data-testid`** |
| **Evidence** | live walkthrough 2026-11-23: login → add to cart → cart → checkout step one → step two → complete |

## 0. The one thing that breaks first

Playwright's `getByTestId()` looks for `data-testid`. Sauce Demo uses **`data-test`**, so either

- use CSS `[data-test='checkout']` (what this framework does), or
- call `playwright.selectors().setTestIdAttribute("data-test")` once per run, then `getByTestId("checkout")`.

## 1. Page map

| Step | URL | `[data-test='title']` |
|---|---|---|
| Login | `/` | — |
| Inventory | `/inventory.html` | `Products` |
| Cart | `/cart.html` | `Your Cart` |
| Checkout step one | `/checkout-step-one.html` | `Checkout: Your Information` |
| Checkout step two | `/checkout-step-two.html` | `Checkout: Overview` |
| Checkout complete | `/checkout-complete.html` | `Checkout: Complete!` |

`document.title` is `Swag Labs` everywhere, so assert on `[data-test='title']` or the URL — never on the title.

## 2. Locators

### Login

| Element | Locator | Notes |
|---|---|---|
| Username | `#user-name` / `[data-test='username']` | also `name="user-name"` |
| Password | `#password` / `[data-test='password']` | |
| Login button | `#login-button` / `[data-test='login-button']` | `<input type="submit" value="Login">` |

### Inventory

| Element | Locator | Notes |
|---|---|---|
| Page title | `[data-test='title']` | `Products` |
| Product card | `[data-test='inventory-item']` | 6 cards — scope by text |
| Add to cart | `#add-to-cart-sauce-labs-backpack` / `[data-test='add-to-cart-sauce-labs-backpack']` | id disappears after adding |
| Cart badge | `[data-test='shopping-cart-badge']` | not rendered when the cart is empty |
| Cart link | `[data-test='shopping-cart-link']` | `<a>` **without href**, `role="button"` |

### Cart

| Element | Locator | Notes |
|---|---|---|
| Title | `[data-test='title']` | `Your Cart` |
| Line item | `[data-test='inventory-item']` | same attribute as on the inventory page |
| Item name | `[data-test='inventory-item-name']` | |
| Quantity | `[data-test='item-quantity']` | `1` |
| Price | `[data-test='inventory-item-price']` | `$29.99` |
| Remove | `#remove-sauce-labs-backpack` | |
| Continue shopping | `#continue-shopping` | |
| Checkout | `#checkout` / `[data-test='checkout']` | |

### Checkout step one

| Element | Locator | Also |
|---|---|---|
| First name | `#first-name` | `[data-test='firstName']`, `name="firstName"` |
| Last name | `#last-name` | `[data-test='lastName']` |
| Zip / postal code | `#postal-code` | `[data-test='postalCode']` |
| Continue | `#continue` | `<input type="submit" value="Continue">` |
| Cancel | `#cancel` | |

### Checkout step two

| Element | Locator | Observed value |
|---|---|---|
| Title | `[data-test='title']` | `Checkout: Overview` |
| Payment | `[data-test='payment-info-value']` | `SauceCard #31337` |
| Shipping | `[data-test='shipping-info-value']` | `Free Pony Express Delivery!` |
| Item total | `[data-test='subtotal-label']` | `Item total: $29.99` |
| Tax | `[data-test='tax-label']` | `Tax: $2.40` |
| Total | `[data-test='total-label']` | `Total: $32.39` |
| Finish | `#finish` / `[data-test='finish']` | |

### Checkout complete

| Element | Locator | Observed value |
|---|---|---|
| Title | `[data-test='title']` | `Checkout: Complete!` |
| Header | `[data-test='complete-header']` | `Thank you for your order!` |
| Text | `[data-test='complete-text']` | `Your order has been dispatched, ...` |
| Back Home | `#back-to-products` | |
| Generate PDF order | `[data-test='generate-pdf-order']` | newer button on the page |

## 3. Gotchas (verified)

| Trap | Reality |
|---|---|
| `getByTestId("checkout")` | matches nothing — the attribute is `data-test` |
| `page.title()` as a page marker | always `Swag Labs` |
| Cart link by role + name | `<a>` has no `href` (role=button) and its `aria-label` mutates: `Cart, empty` → `Cart, 1 items` (sic) — use `[data-test='shopping-cart-link']` |
| `#add-to-cart-<slug>` after adding | the element is replaced by `#remove-<slug>`; the add id no longer exists |
| `[data-test='inventory-item']` | used on **both** inventory and cart pages |
| Cart badge | rendered only when the cart is not empty |
| Complete page | badge disappears again (`Cart, empty`) |
| `assertThat(locator).hasText("Total:")` | `hasText` is an **exact** (normalized) match — `Total: $32.39` fails it. Use `containsText(...)` or `hasText(Pattern)` for partial text |
