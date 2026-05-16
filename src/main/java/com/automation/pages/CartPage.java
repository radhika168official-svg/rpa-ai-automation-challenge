package com.automation.pages;

import com.microsoft.playwright.Page;

import java.util.List;

public class CartPage {

    private final Page page;

    private static final String CART_ITEM_NAMES = ".inventory_item_name";
    private static final String CHECKOUT_BUTTON = "[data-test='checkout']";

    public CartPage(Page page) {
        this.page = page;
    }

    public List<String> getCartProductNames() {
        return page.locator(CART_ITEM_NAMES).allTextContents();
    }

    public void proceedToCheckout() {
        page.click(CHECKOUT_BUTTON);
    }
}
