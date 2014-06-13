package com.autotesting.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;

public class WPDriver {
    private static WebDriver driver;
    private static ChromeDriverService service;

    private static final String PATH_TO_CHROMEDRIVER = "resource//chromedriver.exe";

    public static WebDriver getWebDriver() {
        if (driver == null) {
            service = new ChromeDriverService.Builder()
                    .usingChromeDriverExecutable(new File(PATH_TO_CHROMEDRIVER))
                    .usingAnyFreePort()
                    .build();
            try {
                service.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver = new ChromeDriver(service);
        }
        return driver;
    }

    public static void stopDriver(){
        if(driver != null){
            driver.quit();
            service.stop();
        }
        driver = null;
    }
}
