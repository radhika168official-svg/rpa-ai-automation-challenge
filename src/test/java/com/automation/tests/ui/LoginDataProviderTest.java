package com.automation.tests.ui;

import com.automation.base.BaseTest;
import com.automation.config.ConfigReader;
import com.automation.pages.LoginPage;
import com.automation.retry.RetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LoginDataProviderTest extends BaseTest {

    @DataProvider(name = "loginScenarios", parallel = true)
    public Object[][] loginScenarios() {
        String password = ConfigReader.get("sauce.password");
        return new Object[][] {
            { ConfigReader.get("sauce.standard.user"),     password, true,  null },
            { ConfigReader.get("sauce.locked.user"),       password, false, "Epic sadface: Sorry, this user has been locked out." },
            { ConfigReader.get("sauce.problem.user"),      password, true,  null },
            { ConfigReader.get("sauce.performance.user"),  password, true,  null },
        };
    }

    @Test(dataProvider = "loginScenarios", retryAnalyzer = RetryAnalyzer.class)
    public void loginWithVariousUsers(String username, String password, boolean expectSuccess, String expectedError) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigate();
        loginPage.login(username, password);

        if (expectSuccess) {
            assertTrue(loginPage.isInventoryPageDisplayed(),
                    "User '" + username + "' should land on inventory page after login");
        } else {
            assertFalse(loginPage.isInventoryPageDisplayed(),
                    "User '" + username + "' should not reach inventory page");
            assertEquals(loginPage.getErrorMessage().trim(), expectedError,
                    "Error message mismatch for user: " + username);
        }
    }
}
