package com.autotesting.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServiceLayer {
    private static ServiceLayer _instance;
    private WebDriver driver;

    private  ServiceLayer(){
        driver = WPDriver.getWebDriver();
    }

    public static ServiceLayer getInstance(){
        if(_instance == null){
            _instance = new ServiceLayer();
        }
        return _instance;
    }

    public ServiceLayer stopService(){
        WPDriver.stopDriver();
        _instance = null;
        return this;
    }

    public Boolean elementExists(By by){
        return driver.findElements(by).size() > 0;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by){
        return driver.findElements(by);
    }

    public ServiceLayer refreshPage(){
        driver.navigate().refresh();
        return this;
    }

    public ServiceLayer get(String url){
        driver.get(url);
        return this;
    }

    public ServiceLayer switchToLastWindow(){
        Set<String> windowsSet = driver.getWindowHandles();
        String newWindowHandle = null;
        Iterator<String> iterator = windowsSet.iterator();
        while(iterator.hasNext()){
            newWindowHandle = iterator.next();
            System.out.println(newWindowHandle);
        }
        driver.switchTo().window(newWindowHandle);
        return this;
    }
}

