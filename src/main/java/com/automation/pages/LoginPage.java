package com.automation.pages;

import com.automation.config.ConfigReader;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON = "#login-button";
    private static final String ERROR_MESSAGE = "[data-test='error']";
    private static final String INVENTORY_CONTAINER = "#inventory_container";
    private static final String LOGIN_LOGO = ".login_logo";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(ConfigReader.get("ui.base.url"));
    }

    public void login(String username, String password) {
        page.fill(USERNAME_INPUT, username);
        page.fill(PASSWORD_INPUT, password);
        page.click(LOGIN_BUTTON);
    }

    public boolean isLoginPageDisplayed() {
        return page.isVisible(LOGIN_LOGO);
    }

    public boolean isInventoryPageDisplayed() {
        return page.isVisible(INVENTORY_CONTAINER);
    }

    public String getErrorMessage() {
        return page.textContent(ERROR_MESSAGE);
    }
}
