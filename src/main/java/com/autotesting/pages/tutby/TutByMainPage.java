package com.autotesting.pages.tutby;

import com.autotesting.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Set;

public class TutByMainPage extends Page {
    private static final String TUTBY_MAIN_PAGE = "http://www.tut.by/";

    /* LOCATORS */
    protected static By LOC_OPEN_LOGIN_FORM = By.cssSelector("a[data-target-popup=\"authorize-form\"]");
    protected static By LOC_LOGIN_INPUT = By.cssSelector("input[name=login]");
    protected static By LOC_PASSWORD_INPUT = By.cssSelector("input[name=password]");
    protected static By LOC_SUBMIT_LOGIN_FORM_BUTTON = By.cssSelector(".b-auth-f input[type=submit]");
    protected static By LOC_LOGGED_MENU_LINK = By.cssSelector("a.enter.logedin");
    protected static By LOC_GEOMESSAGE_WINDOW_CLOSER = By.cssSelector("#geomessage .close");
    protected static By LOC_MAIL_LINK = By.cssSelector("a[href*=\"http://profile.tut.by/mail.html?mu\"]");

    public void login(String userName, String password) throws InterruptedException {
        service.get(TUTBY_MAIN_PAGE);
        service.findElement(LOC_OPEN_LOGIN_FORM).click();
        service.findElement(LOC_LOGIN_INPUT).sendKeys(userName);
        service.findElement(LOC_PASSWORD_INPUT).sendKeys(password);
        service.findElement(LOC_SUBMIT_LOGIN_FORM_BUTTON).click();
        service.sleep(2);
    }

    public Boolean isLoggedIn() {
        return service.findElements(LOC_LOGGED_MENU_LINK).size() > 0;
    }

    public void closeGeoMessage() throws InterruptedException {
        List<WebElement> closeModalPopUp = service.findElements(LOC_GEOMESSAGE_WINDOW_CLOSER);
        if (closeModalPopUp.size() > 0) {
            closeModalPopUp.get(0).click();
            service.sleep(2);
        }
    }

    public void openLoggedMenu() throws InterruptedException {
        WebElement loginLink = service.findElement(LOC_LOGGED_MENU_LINK);
        if (!loginLink.getAttribute("class").contains("active")) {
            loginLink.click();
            service.sleep(1);
        }
    }

    public void openMail() throws InterruptedException {
        openLoggedMenu();
        service.findElement(LOC_MAIL_LINK).click();
        service.switchToLastWindow().sleep(5);
    }

    public Boolean isGeomessageExists(){
        return elementExists(LOC_GEOMESSAGE_WINDOW_CLOSER);
    }


    public Boolean isLoggedinExists(){
        return elementExists(LOC_LOGGED_MENU_LINK);
    }

    public Boolean isMailLinkExists(){
        return elementExists(LOC_MAIL_LINK);
    }
}
