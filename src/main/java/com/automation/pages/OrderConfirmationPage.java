package com.automation.pages;

import com.microsoft.playwright.Page;

public class OrderConfirmationPage {

    private final Page page;

    private static final String SUCCESS_HEADER = ".complete-header";

    public OrderConfirmationPage(Page page) {
        this.page = page;
    }

    public String getSuccessMessage() {
        return page.textContent(SUCCESS_HEADER);
    }

    public boolean isOrderSuccessful() {
        return page.isVisible(SUCCESS_HEADER);
    }
}
