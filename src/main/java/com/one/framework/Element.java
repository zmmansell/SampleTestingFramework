package com.one.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Element extends DelegatingWebElement implements FormElements {

    public Element(WebElement delegate) {
        super(delegate);
    }

    @Override
    public String toString() {
        String tagName = delegate.getTagName();
        return "" + (tagName.equals("input") ?
                delegate.getAttribute("value") : tagName.equals("img") ?
                delegate.getAttribute("src") : delegate.getText()) + "";
    }
}
