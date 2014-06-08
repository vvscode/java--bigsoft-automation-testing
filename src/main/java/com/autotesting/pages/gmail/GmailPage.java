package com.autotesting.pages.gmail;

import com.autotesting.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GmailPage extends Page {

    /* LOCATORS */
    protected static By LOC_NEW_MESSAGE_BUTTON = By.cssSelector("[gh=cm]");
    protected static By LOC_NEW_MESSAGE_WINDOW_CLOSER = By.cssSelector(".Ha");
    protected static By LOC_NEW_MESSAGE_WINDOW = By.cssSelector("[role=dialog]");
    protected static By LOC_MESSAGE_TO_INPUT = By.cssSelector("textarea[name=to]");
    protected static By LOC_MESSAGE_SUBJECT_INPUT = By.cssSelector("input[name=subjectbox]");
    protected static By LOC_MESSAGE_BODY_INPUT = By.cssSelector("div[g_editable][role=textbox]");
    protected static By LOC_MESSAGE_SEND_BUTTON = By.cssSelector(".gU [data-tooltip]");
    protected static By LOC_ALERT_DIALOG = By.cssSelector("[role=alertdialog]");
    protected static By LOC_ALERT_DIALOG_OK_BUTTON = By.cssSelector("[role=alertdialog] [name=ok]");
    protected static By LOC_MAIL_GRID_ITEM = By.cssSelector("td[id] div[role=link] span[id] b");
    protected static By LOC_MAIL_GRID_CHECKED_ITEM = By.cssSelector("[aria-labelledby][aria-checked=true]");
    protected static By LOC_MAIL_GRID_SELECT_ALL = By.cssSelector("[aria-label=Выбрать] [role=checkbox]");
    protected static By LOC_MAIL_GRID_REMOVER = By.cssSelector("[aria-label=Удалить]");

    public GmailPage(WebDriver drv) {
        super(drv);
    }

    public void openNewMessageWindow() {
        if (!isNewMessageWindowExists()) {
            WebElement newMessageButton = driver.findElement(LOC_NEW_MESSAGE_BUTTON);
            newMessageButton.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            closeNewMEssageWindow();
            openNewMessageWindow();
        }
    }

    public void closeNewMEssageWindow() {
        driver.findElement(LOC_NEW_MESSAGE_WINDOW_CLOSER).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendNewMessage(String to, String subj, String body) {
        openNewMessageWindow();
        WebElement mailTo = driver.findElement(LOC_MESSAGE_TO_INPUT);
        WebElement subject = driver.findElement(LOC_MESSAGE_SUBJECT_INPUT);
        WebElement text = driver.findElement(LOC_MESSAGE_BODY_INPUT);

        mailTo.sendKeys(to);
        subject.sendKeys(subj);
        text.sendKeys(body);

        driver.findElement(LOC_MESSAGE_SEND_BUTTON).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean isAlertBoxExists() {
        List<WebElement> alertDialog = driver.findElements(LOC_ALERT_DIALOG);
        return alertDialog.size() > 0;
    }

    public void closeAlert() throws InterruptedException {
        if (isAlertBoxExists()) {
            WebElement okButton = driver.findElement(LOC_ALERT_DIALOG_OK_BUTTON);
            okButton.click();
            Thread.sleep(2000);
        }
    }

    public Boolean isMailExists(String subj) {
        driver.navigate().refresh();

        List<WebElement> mailList = driver.findElements(LOC_MAIL_GRID_ITEM);

        Boolean mailExistsFlag = false;
        Boolean checkResult;
        String text;
        for (WebElement webElement : mailList) {
            text = webElement.getText();
            checkResult = text.equals(subj);
            if (checkResult) {
                mailExistsFlag = true;
                break;
            }
        }

        return mailExistsFlag;
    }

    public Integer getMailsCount() {
        List<WebElement> mailList = driver.findElements(LOC_MAIL_GRID_ITEM);
        return mailList.size();
    }

    public Integer getCheckedMailsCount() {
        List<WebElement> checkedMails = driver.findElements(LOC_MAIL_GRID_CHECKED_ITEM);
        return checkedMails.size();
    }

    public void selectAllMessages() {
        WebElement selector = driver.findElement(LOC_MAIL_GRID_SELECT_ALL);
        selector.click();
    }

    public void removeMessages() {
        WebElement remover = driver.findElement(LOC_MAIL_GRID_REMOVER);
        remover.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean isCreateMessageButtonExists() {
        return elementExists(LOC_NEW_MESSAGE_BUTTON);
    }

    public Boolean isNewMessageWindowExists() {
        return elementExists(LOC_NEW_MESSAGE_WINDOW);
    }

    public Boolean isSendMessageButtonExists() {
        return elementExists(LOC_MESSAGE_SEND_BUTTON);
    }


}
