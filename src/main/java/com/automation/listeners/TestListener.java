package com.automation.listeners;

import com.automation.utils.PageHolder;
import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Page page = PageHolder.get();
        if (page == null) {
            System.out.println("Screenshot skipped: Playwright page was not available");
            return;
        }

        try {
            Path screenshotDir = Paths.get("target", "screenshots");
            Files.createDirectories(screenshotDir);

            String fileName = result.getName() + ".png";
            Path screenshotPath = screenshotDir.resolve(fileName);

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(screenshotPath)
                    .setFullPage(true));

            System.out.println("Screenshot saved: " + screenshotPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
