# Locator inventory — Automation Playground back-office login

| | |
|---|---|
| **Page** | <https://automationplayground.com/back-office/pages/login.html> |
| **Title** | `RobotFrameworkTutorial.com Demo Admin Site` |
| **Stack** | Bootstrap 3 + SB Admin 2 (`panel`, `form-control`, `btn btn-success btn-block`) |
| **Evidence** | live DOM inspected 2026-11-23; every "matches" value below was measured with `document.querySelectorAll` / `document.evaluate` |

## 1. What the page contains

```html
<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <div class="login-panel panel panel-default">
        <div class="panel-heading"><h3 class="panel-title">Please Sign In</h3></div>
        <div class="panel-body">
          <form role="form">
            <fieldset>
              <div class="form-group">
                <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus>
              </div>
              <div class="form-group">
                <input class="form-control" placeholder="Password" name="password" type="password" value="">
              </div>
              <div class="checkbox">
                <label><input name="remember" type="checkbox" value="Remember Me">Remember Me </label>
              </div>
              <!-- Change this to a button or input when using this as a form -->
              <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>
            </fieldset>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
```

| Element | Tag / type | Stable attributes | Accessible name |
|---|---|---|---|
| Sign-in heading | `h3` | `class="panel-title"` | Please Sign In |
| E-mail field | `input type=email` | `name="email"`, `placeholder="E-mail"`, `autofocus` | E-mail |
| Password field | `input type=password` | `name="password"`, `placeholder="Password"` | Password |
| Remember Me | `input type=checkbox` | `name="remember"`, `value="Remember Me"` | Remember Me (from wrapping `<label>`) |
| Login control | `a` | `href="index.html"`, `class="btn btn-lg btn-success btn-block"` | Login |
| Form | `form` | `role="form"` (no `id`, no `name`, no `action`, no `method`) | — |
| Login panel | `div` | `class="login-panel panel panel-default"` | — |

## 2. Ranking I applied

1. **`data-testid` / `id`** — best, but **none exist** on this page (`[data-testid]` → 0, `#email` → 0).
2. **`name` attribute** — part of the form contract (the value the server receives), therefore the most stable thing available here.
3. **ARIA role + accessible name** — survives markup and CSS changes; not possible for the password field (see §4).
4. **`placeholder`** — user-visible copy: readable, but changes with wording/i18n.
5. **Semantic `type` / `href`** — stable, but may start matching new elements.
6. **CSS classes** — styling only; `.form-control` already matches 2 elements.
7. **Absolute XPath / DOM position** — never.

## 3. Locators per element

### 3.1 Sign-in heading (page-state assertion)

| Kind | Locator | Matches |
|---|---|---|
| **Playwright (recommended)** | `page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Please Sign In"))` | 1 |
| CSS | `h3.panel-title` | 1 |
| XPath | `//h3[normalize-space()='Please Sign In']` | 1 |

Use it to assert the login page rendered before typing. Prefer the role form over `getByText("Please Sign In")`: the text is also present on the `.panel-heading` ancestor, so `getByText` can resolve to more than one element.

### 3.2 E-mail field

| Kind | Locator | Matches |
|---|---|---|
| **Playwright (recommended)** | `page.locator("input[name='email']")` | 1 |
| Readable alternative | `page.getByPlaceholder("E-mail")` | 1 |
| Role-based | `page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("E-mail"))` | 1 |
| CSS | `input[type='email']` | 1 |
| XPath | `//input[@name='email']` | 1 |

`name="email"` is the value the form sends, so it survives restyling and copy changes. The field is `autofocus`, so `assertThat(page.locator("input[name='email']")).isFocused()` is a cheap "page is ready" check.

### 3.3 Password field

| Kind | Locator | Matches |
|---|---|---|
| **Playwright (recommended)** | `page.locator("input[name='password']")` | 1 |
| Readable alternative | `page.getByPlaceholder("Password")` | 1 |
| CSS | `input[type='password']` | 1 |
| XPath | `//form//input[@type='password']` | 1 |

**Do not** try `getByRole(TEXTBOX, …)` here: `input[type=password]` exposes no ARIA role, so role-based lookup returns nothing. There is also no `<label>` and no `aria-label`, which is why `getByLabel("Password")` fails too.

### 3.4 Remember Me checkbox

| Kind | Locator | Matches |
|---|---|---|
| **Playwright (recommended)** | `page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Remember Me"))` | 1 |
| CSS | `input[name='remember']` | 1 |
| XPath | `//label[contains(normalize-space(.),'Remember Me')]/input` | 1 |

The accessible name comes from the wrapping `<label>`, so the role locator is both unique and readable. Note `value="Remember Me"` — that is the submitted value, not the label.

### 3.5 Login control

| Kind | Locator | Matches |
|---|---|---|
| **Playwright (recommended)** | `page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login"))` | 1 |
| CSS | `a.btn.btn-success` | 1 |
| CSS (alternative) | `a[href='index.html']` | 1 |
| XPath | `//a[normalize-space()='Login']` | 1 |

**It is an `<a href="index.html">`, not a `<button>`.** `button` → 0 elements, `input[type='submit']` → 0 elements, so `getByRole(AriaRole.BUTTON, …)` and `click("button:has-text('Login')")` both fail. The form has no `action`/`method` either — clicking this anchor simply navigates to `index.html`, so a login flow is: fill e-mail → fill password → click → assert the URL/title changed.

### 3.6 Scoping roots

| Element | Locator | Matches |
|---|---|---|
| Login panel | `page.locator(".login-panel")` | 1 |
| Form | `page.locator("form[role='form']")` | 1 |

Scope child locators under the form when a page grows: `page.locator("form[role='form']").locator("input[name='email']")`.

## 4. Gotchas (verified)

| Trap | Reality |
|---|---|
| `#email`, `#password`, `#login` | 0 matches — the page has **no `id` attributes at all** |
| `[data-testid=...]` | 0 matches — no test hooks exist; ask devs to add them |
| `getByLabel("E-mail")` | no `<label>`, no `aria-label`; the accessible name comes from `placeholder` |
| `.form-control` | matches **2** elements → Playwright strict-mode violation; never use alone |
| `button` / `input[type=submit]` | 0 matches — Login is an anchor |
| `getByRole(TEXTBOX)` for password | no role for `input[type=password]` |
| `getByText("Please Sign In")` | text also lives on the `.panel-heading` ancestor — prefer the heading role |
| `.btn.btn-lg.btn-block` | order-insensitive but styling-based; `a.btn.btn-success` is tighter |

## 5. Ready-to-use page object

```java
package com.egtdigital.automation.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    private static final String EMAIL = "input[name='email']";
    private static final String PASSWORD = "input[name='password']";
    private static final String LOGIN_LINK = "a.btn.btn-success";

    public LoginPage(Page page) {
        super(page);
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
}
```

## 6. Verification evidence

| Selector | Matches | Verdict |
|---|---|---|
| `input[name='email']` | 1 | unique |
| `input[placeholder='E-mail']` | 1 | unique |
| `input[name='password']` | 1 | unique |
| `input[placeholder='Password']` | 1 | unique |
| `input[name='remember']` | 1 | unique |
| `a[href='index.html']` | 1 | unique |
| `.btn.btn-success` | 1 | unique |
| `h3.panel-title` | 1 | unique |
| `form[role='form']` | 1 | unique |
| `.login-panel` | 1 | unique |
| `.form-control` | 2 | **ambiguous** |
| `button` | 0 | absent |
| `input[type='submit']` | 0 | absent |
| `[data-testid]` | 0 | absent |
| `#email` | 0 | absent |
| `//a[normalize-space()='Login']` | 1 | unique |
| `//h3[normalize-space()='Please Sign In']` | 1 | unique |
| `//input[@name='email']` | 1 | unique |
| `//form//input[@type='password']` | 1 | unique |
| `//label[contains(normalize-space(.),'Remember Me')]/input` | 1 | unique |

## 7. Ready-to-send email text (BG)

> **Тема:** Домашно – локатори за Automation Playground back-office login
>
> Здравейте,
>
> Прилагам описанието на локаторите за <https://automationplayground.com/back-office/pages/login.html>.
>
> Страницата няма `id` и `data-testid` атрибути, затова използвах `name` / `placeholder` / ARIA role и проверих уникалността на всеки локатор директно в live DOM – колоната „Matches" показва реалния брой съвпадения. Отбелязал съм и капаните: `.form-control` съвпада с 2 елемента, а „Login" е `<a>`, не `<button>`.
>
> Поздрави,
> Teodor Iliev
