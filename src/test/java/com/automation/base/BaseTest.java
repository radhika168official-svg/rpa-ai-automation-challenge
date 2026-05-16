package com.automation.base;

import com.automation.config.ConfigReader;
import com.automation.listeners.TestListener;
import com.automation.utils.PageHolder;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        Playwright pw = Playwright.create();
        playwright.set(pw);

        String browserType = ConfigReader.get("browser");
        boolean headless = ConfigReader.getBoolean("headless");
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(headless ? 0 : 700);

        Browser br = switch (browserType.toLowerCase()) {
            case "firefox" -> pw.firefox().launch(options);
            case "webkit" -> pw.webkit().launch(options);
            default -> pw.chromium().launch(options);
        };

        browser.set(br);
        BrowserContext ctx = br.newContext();
        context.set(ctx);
        Page pg = ctx.newPage();
        page.set(pg);
        PageHolder.set(pg);
    }

    @AfterMethod
    public void tearDown() {
        PageHolder.remove();
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();
        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }

    protected Page getPage() {
        return page.get();
    }

    public static Page getCurrentPage() {
        return PageHolder.get();
    }
}
