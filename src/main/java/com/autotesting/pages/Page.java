package com.autotesting.pages;

import com.autotesting.service.ServiceLayer;
import org.openqa.selenium.By;

public class Page {
    protected ServiceLayer service = ServiceLayer.getInstance();

    public Page(){

    }

    public Boolean elementExists(By by){
        Boolean result = service.elementExists(by);
        service.debug(String.format("elementExists: El:[%s] Result[%b]", by.toString(), result));
        return result;
    }

    public String getCurrentUrl() {
        String url = service.getCurrentUrl();
        service.debug(String.format("getCurrentUrl: %s", url));
        return url;
    }
}
