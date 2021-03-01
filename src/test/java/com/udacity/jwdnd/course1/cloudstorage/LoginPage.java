package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id="inputUsername")
    private WebElement inputUsername;
    @FindBy(id = "inputPassword")
    private WebElement inputPassword;
    @FindBy(id = "submitButton")
    private WebElement submitButton;

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", inputUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", inputPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }
}
