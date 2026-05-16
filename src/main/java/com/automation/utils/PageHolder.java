package com.automation.utils;

import com.microsoft.playwright.Page;

public class PageHolder {

    private static final ThreadLocal<Page> currentPage = new ThreadLocal<>();

    public static void set(Page page) {
        currentPage.set(page);
    }

    public static Page get() {
        return currentPage.get();
    }

    public static void remove() {
        currentPage.remove();
    }
}
