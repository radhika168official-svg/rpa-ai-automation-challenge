package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {

    private final Page page;

    private static final String INVENTORY_CONTAINER = "#inventory_container";
    private static final String SORT_DROPDOWN = "[data-test='product-sort-container']";
    private static final String PRODUCT_PRICES = ".inventory_item_price";
    private static final String ADD_TO_CART_BUTTONS = ".btn_inventory";
    private static final String PRODUCT_NAMES = ".inventory_item_name";
    private static final String CART_BADGE = ".shopping_cart_badge";
    private static final String CART_LINK = ".shopping_cart_link";
    private static final String MENU_BUTTON = "#react-burger-menu-btn";
    private static final String LOGOUT_LINK = "#logout_sidebar_link";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public boolean isInventoryPageDisplayed() {
        return page.isVisible(INVENTORY_CONTAINER);
    }

    public void sortByPriceLowToHigh() {
        page.selectOption(SORT_DROPDOWN, new SelectOption().setValue("lohi"));
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        List<String> priceTexts = page.locator(PRODUCT_PRICES).allTextContents();
        for (String text : priceTexts) {
            prices.add(Double.parseDouble(text.replace("$", "").trim()));
        }
        return prices;
    }

    public boolean isFirstPriceLowest() {
        List<Double> prices = getProductPrices();
        if (prices.isEmpty()) return false;
        double min = prices.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        return prices.get(0) == min;
    }

    public List<String> addFirstTwoProductsToCart() {
        List<String> selectedNames = new ArrayList<>();
        Locator nameLocators = page.locator(PRODUCT_NAMES);
        Locator addButtons = page.locator(ADD_TO_CART_BUTTONS);

        selectedNames.add(nameLocators.nth(0).textContent().trim());
        addButtons.nth(0).click();

        selectedNames.add(nameLocators.nth(1).textContent().trim());
        addButtons.nth(1).click();

        return selectedNames;
    }

    public String getCartBadgeCount() {
        return page.textContent(CART_BADGE);
    }

    public void goToCart() {
        page.click(CART_LINK);
    }

    public void logout() {
        page.click(MENU_BUTTON);
        page.click(LOGOUT_LINK);
    }
}
