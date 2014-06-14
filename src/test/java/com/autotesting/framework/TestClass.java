package com.autotesting.framework;
import java.io.IOException;
import java.util.Date;
import com.autotesting.pages.gmail.GmailPage;
import com.autotesting.pages.tutby.TutByMainPage;
import com.autotesting.service.ServiceLayer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestClass {
    protected static ServiceLayer service = ServiceLayer.getInstance();
    private static TutByMainPage tutByMainPage;
    private static GmailPage gmailPage;

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
        service.info("BeforeClass - createAndStartService");
        tutByMainPage = new TutByMainPage();
        gmailPage = new GmailPage();
    }

    @Test
    public void tutByMailLoginTest() throws InterruptedException {
        service.info("tutByMailLoginTest - try login with correct data");
        tutByMainPage.login(TUTBY_LOGIN_NAME, TUTBY_LOGIN_PASSWORD);
        Assert.assertTrue(tutByMainPage.isLoggedIn(), "Login failed - loggedin link not found");
    }

    @Test(dependsOnMethods = {"tutByMailLoginTest"})
    public void tutByGeomessageCloserTest() throws InterruptedException {
        service.info("tutByGeomessageCloserTest - check geomessage window");
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
        service.info("tutByMailLinkRedirectToGmailTest - try to open mail page");
        tutByMainPage.openMail();
        String currentUrl = tutByMainPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("mail.google.com"), "Mail page didn't use gmail");
    }

    @Test(dependsOnMethods = {"tutByMailLinkRedirectToGmailTest"})
    public void gmailNewMessageButtonExistsTest() throws InterruptedException {
        Assert.assertTrue(gmailPage.isCreateMessageButtonExists(), "New message button not found");
    }

    @Test(dependsOnMethods = {"gmailNewMessageButtonExistsTest"})
    public void gmailNewMessageWindowOpenedTest() throws InterruptedException {
        service.info("gmailNewMessageWindowOpenedTest - try to open nerw message window");
        gmailPage.openNewMessageWindow();
        Assert.assertTrue(gmailPage.isNewMessageWindowExists(), "New message box wasn't opened");
        Assert.assertTrue(gmailPage.isSendMessageButtonExists(), "Send button wasn't found");
    }

    @Test(dependsOnMethods = {"gmailNewMessageWindowOpenedTest"})
    public void gmailCheckEmptyMessageAlertTest() throws InterruptedException {
        service.info("gmailCheckEmptyMessageAlertTest - try to send empty message");
        gmailPage.sendNewMessage("", "", "");
        Assert.assertTrue(gmailPage.isAlertBoxExists(), "Alert already opened");
        gmailPage.closeAlert();
        Assert.assertFalse(gmailPage.isAlertBoxExists(), "Alert box still opened");
        gmailPage.closeNewMEssageWindow();
        Assert.assertFalse(gmailPage.isNewMessageWindowExists(), "New message windows wasn't close");
    }

    @Test(dependsOnMethods = {"gmailCheckEmptyMessageAlertTest"})
    public void gmailSendMessageTest() throws InterruptedException {
        service.info("gmailSendMessageTest - try to send correct message");
        gmailPage.sendNewMessage(TUTBY_SELF_MAIL, uniqueSubject, uniqueSubject);
        Assert.assertTrue(!gmailPage.isAlertBoxExists(), "Assertive massage wasn't found");
    }

    @Test(dependsOnMethods = {"gmailSendMessageTest"})
    public void gmailCheckInboxEmailTest() throws InterruptedException {
        service.info("gmailCheckInboxEmailTest - check if sended message exists at inbox folder");
        Assert.assertTrue(gmailPage.isMailExists(uniqueSubject), "Just sended message wasn't found");
    }

    @Test(dependsOnMethods = {"gmailCheckInboxEmailTest"})
    public void gmailRemoveEmailsTest() throws InterruptedException {
        service.info("gmailRemoveEmailsTest - try to remove message");
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
        service.stopService();
    }
}