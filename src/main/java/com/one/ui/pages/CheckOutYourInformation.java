package com.one.ui.pages;

import com.one.framework.Browser;

import static com.one.locators.Id.*;

public class CheckOutYourInformation {

    private Browser browser;

    public CheckOutYourInformation(Browser browser) {
        this.browser = browser;
    }

    public void fillInInformation() {
        browser.await(CART_FIRST_NAME).sendKeys("First name");
        browser.await(CART_LAST_NAME).sendKeys("Last name");
        browser.await(CART_POST_CODE).sendKeys("1010");
    }

    public void continueThroughCartCheckout() {
        browser.click(CONTINUE_THROUGH_CHECKOUT);
    }


}
