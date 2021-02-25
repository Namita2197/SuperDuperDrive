package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id="successmessage")
    private WebElement successMessage;

    private WebDriverWait wait;
    public ResultPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
    }
    public void clickHere(){
        System.out.println("Inside result: Going to click");
        wait.until(ExpectedConditions.elementToBeClickable(successMessage)).click();
        System.out.println("Clicked result link: going to home page");
    }

}
