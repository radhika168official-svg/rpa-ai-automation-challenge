package com.automation.pages;

import com.microsoft.playwright.Page;

public class CheckoutPage {

    private final Page page;

    private static final String FIRST_NAME_INPUT = "[data-test='firstName']";
    private static final String LAST_NAME_INPUT = "[data-test='lastName']";
    private static final String ZIP_INPUT = "[data-test='postalCode']";
    private static final String CONTINUE_BUTTON = "[data-test='continue']";

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void fillCheckoutInformation(String firstName, String lastName, String zip) {
        page.fill(FIRST_NAME_INPUT, firstName);
        page.fill(LAST_NAME_INPUT, lastName);
        page.fill(ZIP_INPUT, zip);
    }

    public void continueToOverview() {
        page.click(CONTINUE_BUTTON);
    }
}
