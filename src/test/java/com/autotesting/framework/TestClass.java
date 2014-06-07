package com.autotesting.framework;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import com.autotesting.pages.gmail.GmailPage;
import com.autotesting.pages.tutby.TutByMainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestClass {
    private static ChromeDriverService service;
    private static WebDriver driver;
    private static TutByMainPage tutByMainPage;
    private static GmailPage gmailPage;

    private static final String PATH_TO_CHROMEDRIVER = "resource//chromedriver.exe";
    private static final String TUTBY_LOGIN_NAME = "autotest1";
    private static final String TUTBY_LOGIN_PASSWORD = "autotest12";

    private static final String TUTBY_SELF_MAIL = TUTBY_LOGIN_NAME + "@tut.by";

    private String uniqueMarker;
    private String uniqueSubject;

    public TestClass() {
        uniqueMarker = (new Date()).toString();
        uniqueSubject = "Test mail for " + TUTBY_SELF_MAIL + " U_KEY:" + uniqueMarker;
    }

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingChromeDriverExecutable(new File(PATH_TO_CHROMEDRIVER))
                .usingAnyFreePort()
                .build();
        service.start();
        driver = new ChromeDriver(service);
        tutByMainPage = new TutByMainPage(driver);
        gmailPage = new GmailPage(driver);
    }

    @Test
    public void tutByMailLoginTest() throws InterruptedException {
        tutByMainPage.login(TUTBY_LOGIN_NAME, TUTBY_LOGIN_PASSWORD);
        Assert.assertTrue(tutByMainPage.isLoggedIn(), "Login failed - loggedin link not found");
    }

    @Test(dependsOnMethods = {"tutByMailLoginTest"})
    public void tutByGeomessageCloserTest() throws InterruptedException {
        tutByMainPage.closeGeoMessage();
        Assert.assertFalse(tutByMainPage.isGeomessageExists(), "Close modal popup failed.");
    }

    @Test(dependsOnMethods = {"tutByGeomessageCloserTest"})
    public void tutByMailLinkExistsTest() throws InterruptedException {
        Assert.assertTrue(tutByMainPage.isLoggedinExists(), "Loggedin link not found");
        tutByMainPage.openLoggedMenu();
        Assert.assertTrue(tutByMainPage.isMailLinkExists(), "Link to mailbox not found");
    }

    @Test(dependsOnMethods = {"tutByMailLinkExistsTest"})
    public void tutByMailLinkRedirectToGmailTest() throws InterruptedException {
        tutByMainPage.openMail();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("mail.google.com"), "Mail page didn't use gmail");
    }

    @Test(dependsOnMethods = {"tutByMailLinkRedirectToGmailTest"})
    public void gmailNewMessageButtonExistsTest() throws InterruptedException {
        Assert.assertTrue(gmailPage.isCreateMessageButtonExists(), "New message button not found");
    }

    @Test(dependsOnMethods = {"gmailNewMessageButtonExistsTest"})
    public void gmailNewMessageWindowOpenedTest() throws InterruptedException {
        gmailPage.openNewMessageWindow();
        Assert.assertTrue(gmailPage.isNewMessageWindowExists(), "New message box wasn't opened");
        Assert.assertTrue(gmailPage.isSendMessageButtonExists(), "Send button wasn't found");
    }

    @Test(dependsOnMethods = {"gmailNewMessageWindowOpenedTest"})
    public void gmailCheckEmptyMessageAlertTest() throws InterruptedException {
        gmailPage.sendNewMessage("", "", "");
        Assert.assertTrue(gmailPage.isAlertBoxExists(), "Alert already opened");
        gmailPage.closeAlert();
        Assert.assertFalse(gmailPage.isAlertBoxExists(), "Alert box still opened");
        gmailPage.closeNewMEssageWindow();
        Assert.assertFalse(gmailPage.isNewMessageWindowExists(), "New message windows wasn't close");
    }

    @Test(dependsOnMethods = {"gmailCheckEmptyMessageAlertTest"})
    public void gmailSendMessageTest() throws InterruptedException {
        gmailPage.sendNewMessage(TUTBY_SELF_MAIL, uniqueSubject, uniqueSubject);
        Assert.assertTrue(!gmailPage.isAlertBoxExists(), "Assertive massage wasn't found");
    }

    @Test(dependsOnMethods = {"gmailSendMessageTest"})
    public void gmailCheckInboxEmailTest() throws InterruptedException {
        Assert.assertTrue(gmailPage.isMailExists(uniqueSubject), "Just sended message wasn't found");
    }

    @Test(dependsOnMethods = {"gmailCheckInboxEmailTest"})
    public void gmailRemoveEmailsTest() throws InterruptedException {
        Assert.assertTrue(gmailPage.getMailsCount() > 0, "No messages found");
        Assert.assertTrue(gmailPage.getCheckedMailsCount() == 0, "Some messages already checked");

        gmailPage.selectAllMessages();
        Assert.assertTrue(gmailPage.getCheckedMailsCount() > 0, "No messages was checked");

        gmailPage.removeMessages();
        Assert.assertTrue(gmailPage.getCheckedMailsCount() == 0, "Some mails still checked");
        Assert.assertTrue(gmailPage.getMailsCount() == 0, "There are some more messages");
    }

    @AfterClass
    public static void createAndStopService() {
        driver.quit();
        service.stop();
    }
}