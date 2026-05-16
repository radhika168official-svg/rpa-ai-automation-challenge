package com.automation.pages;

import com.microsoft.playwright.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutOverviewPage {

    private final Page page;

    private static final String ITEM_TOTAL = ".summary_subtotal_label";
    private static final String TAX = ".summary_tax_label";
    private static final String GRAND_TOTAL = ".summary_total_label";
    private static final String FINISH_BUTTON = "[data-test='finish']";

    public CheckoutOverviewPage(Page page) {
        this.page = page;
    }

    public BigDecimal getItemTotal() {
        return parseAmount(page.textContent(ITEM_TOTAL));
    }

    public BigDecimal getTax() {
        return parseAmount(page.textContent(TAX));
    }

    public BigDecimal getGrandTotal() {
        return parseAmount(page.textContent(GRAND_TOTAL));
    }

    public boolean isTotalCalculationCorrect() {
        BigDecimal expected = getItemTotal().add(getTax()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actual = getGrandTotal().setScale(2, RoundingMode.HALF_UP);
        return expected.compareTo(actual) == 0;
    }

    public void completeOrder() {
        page.click(FINISH_BUTTON);
    }

    private BigDecimal parseAmount(String text) {
        String numeric = text.replaceAll("[^0-9.]", "");
        return new BigDecimal(numeric);
    }
}
