package com.one.ui.pages;

import com.one.framework.Browser;
import com.one.locators.Id;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import static com.one.locators.ClassName.PRODUCT_LABEL;
import static com.one.locators.ClassName.SHOPPING_CART_LINK;
import static com.one.locators.Id.ADD_BACKPACK_TO_CART;
import static com.one.locators.XPathSelector.SHOPPING_CART;

public class Inventory {


        private Browser browser;

        public Inventory(Browser browser) {
            this.browser = browser;
        }

        public boolean isProductLabelPresent() {
            return browser.elementIsDisplayed(PRODUCT_LABEL);
        }

        public String getProductLabel() {
            return browser.getText(PRODUCT_LABEL);
        }

        public void addBackpackToCart() {
            browser.click(ADD_BACKPACK_TO_CART);
        }

        public String getProductsInCart() {
            return browser.getText(SHOPPING_CART);
        }

        public void openShoppingCart() {
            browser.click(SHOPPING_CART_LINK);
        }

}
