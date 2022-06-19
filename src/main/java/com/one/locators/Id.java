package com.one.locators;


import org.openqa.selenium.By;

import java.util.function.Supplier;

import static org.openqa.selenium.By.id;

public enum Id implements Supplier<By> {

    LOGIN("login-button"),
    USERNAME("user-name"),
    PASSWORD("password"),

    LOGOUT("logout_sidebar_link"),
    INVENTORY_CONTAINER("inventory_container"),

    ADD_BACKPACK_TO_CART("add-to-cart-sauce-labs-backpack"),

    CHECKOUT("checkout"),

    CART_FIRST_NAME("first-name"),

    CART_LAST_NAME("last-name"),

    CART_POST_CODE("postal-code"),

    CONTINUE_THROUGH_CHECKOUT("continue"),

    FINISH("finish")
    ;

    private final By by;

    Id(String id) {
        this.by = id(id);
    }

    @Override
    public By get() {
        return by;
    }

    @Override
    public String toString() {
        return by.toString();
    }

}