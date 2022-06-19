package com.one.ui.pages;

import com.one.framework.Browser;

import static com.one.locators.ClassName.CART_QUANTITY;
import static com.one.locators.ClassName.INVENTORY_ITEM_NAME;
import static com.one.locators.Id.FINISH;

public class CheckoutOverview {

    private Browser browser;

    public CheckoutOverview(Browser browser) {
        this.browser = browser;
    }

    public String getInventoryItemName() {
        return browser.getText(INVENTORY_ITEM_NAME);
    }

    public String getCartQuantity() {
        return browser.getText(CART_QUANTITY);
    }

    public void finishCheckout() {
        browser.click(FINISH);
    }
}
