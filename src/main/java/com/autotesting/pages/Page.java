package com.autotesting.pages;

import com.autotesting.service.ServiceLayer;
import org.openqa.selenium.By;

public class Page {
    protected ServiceLayer service;

    public Page(){
        service = ServiceLayer.getInstance();
    }

    public Boolean elementExists(By by){
        return service.elementExists(by);
    }

    public String getCurrentUrl() {
        return service.getCurrentUrl();
    }
}
