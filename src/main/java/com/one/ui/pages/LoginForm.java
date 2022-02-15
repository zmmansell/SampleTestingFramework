package com.one.ui.pages;

import com.one.framework.Constants;
//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.net.URI;

import static com.one.framework.Constants.PLACEHOLDER;
import static com.one.locators.ClassName.LOGO;
import static com.one.locators.ClassName.PRODUCT_LABEL;
import static com.one.locators.Id.*;
import static com.one.locators.XPathSelector.*;

@ContextConfiguration(classes = {com.one.framework.WebDriverConfig.class, com.one.framework.Browser.class})
public class LoginForm {
    //private Logger logger = LoggerFactory.getLogger(LoginForm.class);

    @Inject
    private WebDriver driver;

    @Inject
    private URI baseUrl;
    @Inject
    private com.one.framework.Browser browser;


    public void openPage() {
        driver.get(baseUrl.toString());
        driver.manage().window().maximize();
    }

    public void loginAs(String email, String password) {

        openPage();

        browser.await(USERNAME).sendKeys(email);
        browser.await(PASSWORD).sendKeys(password);

        browser.awaitClickable(LOGIN);
        browser.click(LOGIN);
    }

    public String getPlaceholderTextUsername(){ return browser.getElementAttribute(USERNAME,PLACEHOLDER); }

    public String getPlaceholderTextPassword(){ return browser.getElementAttribute(PASSWORD,PLACEHOLDER); }


    public String getLoginErrorMessage(){ return browser.getText(lOGIN_BOX); }

    public boolean isLoginErrorMessagePresent() {
        return browser.elementIsDisplayed(LOGIN_ERROR_MESSAGE);
    }

    public String getLoginButtonText(){ return browser.getText(LOGIN); }

    public boolean isLogoDisplayed() { return browser.elementIsDisplayed(LOGO);  }

    public boolean isLoginDisplayed() { return browser.elementIsDisplayed(LOGIN);  }

}
