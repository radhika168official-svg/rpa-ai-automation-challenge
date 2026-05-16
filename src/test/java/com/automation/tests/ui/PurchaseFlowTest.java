package com.automation.tests.ui;

import com.automation.base.BaseTest;
import com.automation.config.ConfigReader;
import com.automation.pages.*;
import com.automation.retry.RetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class PurchaseFlowTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void completePurchaseFlow() {
        String username = ConfigReader.get("sauce.standard.user");
        String password = ConfigReader.get("sauce.password");
        String firstName = ConfigReader.get("checkout.first.name");
        String lastName = ConfigReader.get("checkout.last.name");
        String zip = ConfigReader.get("checkout.zip");

        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigate();
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(getPage());
        assertTrue(inventoryPage.isInventoryPageDisplayed(), "Inventory page should be displayed after login");

        inventoryPage.sortByPriceLowToHigh();
        assertTrue(inventoryPage.isFirstPriceLowest(), "First product price should be the lowest after sorting");

        List<String> selectedProducts = inventoryPage.addFirstTwoProductsToCart();
        assertEquals(inventoryPage.getCartBadgeCount(), "2", "Cart badge should show 2 items");

        inventoryPage.goToCart();
	
		
        CartPage cartPage = new CartPage(getPage());
        List<String> cartProducts = cartPage.getCartProductNames();
        assertEquals(cartProducts.size(), selectedProducts.size());
        assertTrue(cartProducts.containsAll(selectedProducts), "Cart items should match selected products");

        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getPage());
        checkoutPage.fillCheckoutInformation(firstName, lastName, zip);
        checkoutPage.continueToOverview();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(getPage());
        assertTrue(overviewPage.isTotalCalculationCorrect(),
                "Item Total + Tax should equal Grand Total");

        overviewPage.completeOrder();

        OrderConfirmationPage confirmationPage = new OrderConfirmationPage(getPage());
        assertTrue(confirmationPage.isOrderSuccessful(), "Order confirmation page should be displayed");
        assertTrue(confirmationPage.getSuccessMessage().contains("Thank you for your order"),
                "Success message should contain 'Thank you for your order'");

        inventoryPage.logout();
        assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed after logout");
    }
}
