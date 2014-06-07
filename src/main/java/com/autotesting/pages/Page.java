package com.autotesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Page {
    protected WebDriver driver;

    public Page(WebDriver drv){
        driver = drv;
    }

    public Boolean elementExists(By by){
        return driver.findElements(by).size() > 0;
    }
}
