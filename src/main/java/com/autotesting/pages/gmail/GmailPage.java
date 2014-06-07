package com.autotesting.pages.gmail;

import com.autotesting.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class GmailPage extends Page {

    public GmailPage(WebDriver drv) {
        super(drv);
    }



    public void openNewMessageWindow() {
        if (!isNewMessageWindowExists()) {
            List<WebElement> newMessageButton = driver.findElements(By.cssSelector("[gh=cm]"));
            newMessageButton.get(0).click();
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
        driver.findElement(By.cssSelector(".Ha")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendNewMessage(String to, String subj, String body) {
        openNewMessageWindow();
        WebElement mailTo = driver.findElement(By.cssSelector("textarea[name=to]"));
        WebElement subject = driver.findElement(By.cssSelector("input[name=subjectbox]"));
        WebElement text = driver.findElement(By.cssSelector("div[g_editable][role=textbox]"));

        mailTo.sendKeys(to);
        subject.sendKeys(subj);
        text.sendKeys(body);

        driver.findElements(By.cssSelector(".gU [data-tooltip]")).get(0).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean isAlertBoxExists() {
        List<WebElement> alertDialog = driver.findElements(By.cssSelector("[role=alertdialog]"));
        return alertDialog.size() > 0;
    }

    public void closeAlert() throws InterruptedException {
        if (isAlertBoxExists()) {
            WebElement okButton = driver.findElement(By.cssSelector("[role=alertdialog] [name=ok]"));
            okButton.click();
            Thread.sleep(2000);
        }
    }

    public Boolean isMailExists(String subj) {
        driver.navigate().refresh();

        List<WebElement> mailList = driver.findElements(By.cssSelector("td[id] div[role=link] span[id] b"));
        Assert.assertTrue(mailList.size() > 0, "No letters found");

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
        List<WebElement> mailList = driver.findElements(By.cssSelector("td[id] div[role=link] span[id] b"));
        return mailList.size();
    }

    public Integer getCheckedMailsCount() {
        List<WebElement> checkedMails = driver.findElements(By.cssSelector("[aria-labelledby][aria-checked=true]"));
        return checkedMails.size();
    }

    public void selectAllMessages() {
        List<WebElement> selector = driver.findElements(By.cssSelector("[aria-label=Выбрать] [role=checkbox]"));
        selector.get(0).click();
    }

    public void removeMessages() {
        List<WebElement> remover = driver.findElements(By.cssSelector("[aria-label=Удалить]"));
        Assert.assertTrue(remover.size() > 0, "Remove buttons wasn't found");
        remover.get(0).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean isCreateMessageButtonExists(){
        return elementExists(By.cssSelector("[gh=cm]"));
    }

    public Boolean isNewMessageWindowExists() {
        return elementExists(By.cssSelector("[role=dialog]"));
    }

    public Boolean isSendMessageButtonExists(){
        return elementExists(By.cssSelector(".gU [data-tooltip]"));
    }


}
