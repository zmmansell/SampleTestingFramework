package com.one.ui.pages;

import com.one.framework.Browser;

import static com.one.locators.ClassName.CHECKOUT_COMPLETE_HEADER;
import static com.one.locators.ClassName.CHECKOUT_LABEL;

public class CheckoutComplete {

    private Browser browser;

    public CheckoutComplete(Browser browser) {
        this.browser = browser;
    }

    public boolean isCheckoutCompleteLabelPresent() {
        return browser.elementIsDisplayed(CHECKOUT_LABEL);
    }

    public String getCheckoutCompleteLabel() {
        return browser.getText(CHECKOUT_LABEL);
    }

    public String getCheckoutCompleteHeader() {
        return browser.getText(CHECKOUT_COMPLETE_HEADER);
    }
}
