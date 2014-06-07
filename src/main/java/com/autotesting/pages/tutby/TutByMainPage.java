package com.autotesting.pages.tutby;

import com.autotesting.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TutByMainPage extends Page {
    private static final String TUTBY_MAIN_PAGE = "http://www.tut.by/";

    public TutByMainPage(WebDriver drv) {
        super(drv);
    }

    public void login(String userName, String password) throws InterruptedException {
        driver.get(TUTBY_MAIN_PAGE);
        driver.findElement(By.cssSelector("a[data-target-popup=\"authorize-form\"]")).click();
        driver.findElement(By.cssSelector("input[name=login]")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector(".b-auth-f input[type=submit]")).click();
        Thread.sleep(2000);
    }

    public Boolean isLoggedIn() {
        return driver.findElements(By.cssSelector("a.logedin")).size() > 0;
    }

    public void closeGeoMessage() throws InterruptedException {
        List<WebElement> closeModalPopUp = driver.findElements(By.cssSelector("#geomessage .close"));
        if (closeModalPopUp.size() > 0) {
            closeModalPopUp.get(0).click();
            Thread.sleep(2000);
        }
    }

    public void openLoggedMenu() throws InterruptedException {
        WebElement loginLink = driver.findElement(By.cssSelector("a.enter.logedin"));
        if (!loginLink.getAttribute("class").contains("active")) {
            loginLink.click();
            Thread.sleep(500);
        }
    }

    public void openMail() throws InterruptedException {
        openLoggedMenu();
        Set<String> oldWindowsSet = driver.getWindowHandles();
        driver.findElements(By.cssSelector("a[href*=\"http://profile.tut.by/mail.html?mu\"]")).get(0).click();
        Set<String> newWindowsSet = driver.getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        driver.switchTo().window(newWindowHandle);
    }

    public Boolean isGeomessageExists(){
        return elementExists(By.cssSelector("#geomessage .close"));
    }


    public Boolean isLoggedinExists(){
        return elementExists(By.cssSelector("a.logedin"));
    }

    public Boolean isMailLinkExists(){
        return elementExists(By.cssSelector("a[href*=\"http://profile.tut.by/mail.html?mu\"]"));
    }
}
