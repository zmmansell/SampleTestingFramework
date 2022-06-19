package com.one;

import com.one.framework.Browser;
import com.one.framework.WebDriverConfig;
import com.one.ui.domains.Product;
import com.one.ui.pages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.Reporter;

import javax.inject.Inject;

import static org.testng.Assert.assertTrue;


@ContextConfiguration(classes = {LoginForm.class, WebDriverConfig.class, Browser.class})
public class MainPageTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private LoginForm loginForm;

    @Inject
    private Browser browser;

    private ProductsContent productsContent;
    private Product product;

    private Inventory inventory;
    private Cart cart;

    private CheckOutYourInformation checkOutYourInformation;

    private CheckoutOverview checkoutOverview;

    private CheckoutComplete checkoutComplete;


    @DataProvider(name = "defaultOrder")
    public Object[][] createDataTest() {
        return new Product[][]{
                { new Product(0,"Sauce Labs Backpack",
                        "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.",
                        "$29.99",
                        "ADD TO CARD") },
                { new Product(1, "Sauce Labs Bike Light",
                        "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.",
                        "$9.99",
                        "ADD TO CARD") },
                { new Product(2,"Sauce Labs Bolt T-Shirt",
                        "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.",
                        "$15.99",
                        "ADD TO CARD") },
                {new Product(3, "Sauce Labs Fleece Jacket",
                        "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.",
                        "$49.99",
                        "ADD TO CARD")},
                {new Product(4, "Sauce Labs Onesie",
                        "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.",
                        "$7.99",
                        "ADD TO CARD")}};
    }

    @DataProvider(name = "sortedOrder")
    public Object[][] createSortedDataTest() {
        return new Product[][]{
                {new Product(0, "Sauce Labs Onesie",
                        "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.",
                        "$7.99",
                        "ADD TO CARD")},
                { new Product(1, "Sauce Labs Bike Light",
                        "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.",
                        "$9.99",
                        "ADD TO CARD") },
                { new Product(2,"Sauce Labs Bolt T-Shirt",
                        "Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.",
                        "$15.99",
                        "ADD TO CARD") },
                { new Product(3,"Test\\.allTheThings\\(\\) T-Shirt \\(Red\\)",
                        "This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.",
                        "$15.99",
                        "ADD TO CARD") },
                { new Product(4,"Sauce Labs Backpack",
                        "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.",
                        "$29.99",
                        "ADD TO CARD") },
                {new Product(5, "Sauce Labs Fleece Jacket",
                        "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.",
                        "$49.99",
                        "ADD TO CARD")},
                };
    }


    @Test(dataProvider = "defaultOrder", priority = 1)
    public void verifyProducts_defaultOrder(Product p) {
        Reporter.log("Verify that the fifth product in the list - " + p.getName() + " -  is displayed correctly");
        product = productsContent.getProductFromPosition(p.getPosition());

        Reporter.log("Verify that the product " + p.getPosition() +" in the list - " + p.getName() + " -  is displayed correctly");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(product.getName().matches(p.getName()), "The name " + product.getName() +" for product on position " + p.getPosition() +" is not the expected one");
        softAssert.assertTrue(product.getDescription().contains(p.getDescription()), "The description is not the expected one");
        softAssert.assertTrue(product.getPrice().contains(p.getPrice()), "The price is not the expected one");
        softAssert.assertAll();
    }


    //TODO - add tests for sorting
    @Test(dataProvider = "sortedOrder", priority = 2)
    public void verifyProducts_sortByPrice(Product p) {
        productsContent.sortBy("Price (low to high)");

        product = productsContent.getProductFromPosition(p.getPosition());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(product.getName().matches(p.getName()), "The name is not the expected one");
        softAssert.assertTrue(product.getDescription().contains(p.getDescription()), "The description is not the expected one");
        softAssert.assertTrue(product.getPrice().contains(p.getPrice()), "The price is not the expected one");
        softAssert.assertAll();
    }

    @Test (priority = 3)
    public void addProductToCartAndCheckout() {
        inventory = new Inventory(browser);
        inventory.addBackpackToCart();

        assertTrue(inventory.getProductsInCart().equals("1"), "No item was added to the cart");
        inventory.openShoppingCart();

        cart = new Cart(browser);
        assertTrue(cart.getInventoryItemName().matches("Sauce Labs Backpack"));
        cart.checkOutYourInformation();

        checkOutYourInformation = new CheckOutYourInformation(browser);
        checkOutYourInformation.fillInInformation();
        checkOutYourInformation.continueThroughCartCheckout();

        checkoutOverview = new CheckoutOverview(browser);
        assertTrue(checkoutOverview.getInventoryItemName().matches("Sauce Labs Backpack"));
        assertTrue(checkoutOverview.getCartQuantity().matches("1"), "Incorrect number of items in cart overview");
        checkoutOverview.finishCheckout();

        checkoutComplete = new CheckoutComplete(browser);
        assertTrue(checkoutComplete.isCheckoutCompleteLabelPresent(), "Checkout complete label not displayed");
        assertTrue(checkoutComplete.getCheckoutCompleteLabel().matches("CHECKOUT: COMPLETE!"));
        assertTrue(checkoutComplete.getCheckoutCompleteHeader().contains("THANK YOU FOR YOUR ORDER"));
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"username", "password"})
    public void beforeTestMethod(String username, String password) {
        productsContent = new ProductsContent(browser);
        loginForm.loginAs(username, password);
    }
}
