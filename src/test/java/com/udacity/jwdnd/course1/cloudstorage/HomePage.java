package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    //    @FindBy(id = "fileUpload")
//    private WebElement fileUpload;
    //
//
//

//

//
//    @FindBy(id = "editCredentialButton")
//    private WebElement editCredentialButton;
//
//    @FindBy(id = "deleteCredentialButton")
//    private WebElement deleteCredentialButton;
//
//
//    @FindBy(id = "credentialUsernameOnDisplay")
//    private WebElement credentialUsernameOnDisplay;
//
//    @FindBy(id = "credentialPasswordOnDisplay")
//    private WebElement credentialPasswordOnDisplay;
    private final WebDriver driver;
    private WebDriverWait wait;
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
    }
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;
    @FindBy(id = "add-Note-Button")
    private WebElement addNoteButton;
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "saveChangesNoteButton")
    private WebElement saveChangesNoteButton;
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;
    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;
//    @FindBy(id="credential-password")
//    private WebElement credentialPasswordViewable;
    @FindBy(id = "credential-key")
    private WebElement credentialKey;
    @FindBy(id = "credentialUsernameOnDisplay")
    private WebElement credentialUsernameOnDisplay;
    @FindBy(id = "credentialPasswordOnDisplay")
    private WebElement credentialPasswordOnDisplay;
    @FindBy(id = "saveChangesCredButton")
    private WebElement saveChangesCredButton;
    @FindBy(id = "noteTitleOnDisplay")
    private WebElement noteTitleOnDisplay;
    @FindBy(id = "credentialUrlOnDisplay")
    private WebElement credentialUrlOnDisplay;
    @FindBy(id = "noteDescriptionOnDisplay")
    private WebElement noteDescriptionOnDisplay;
    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;
    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;
    @FindBy(id="credential-url")
    private WebElement credentialUrl;
    @FindBy(id="credential-username")
    private WebElement credentialUsername;
    @FindBy(id="credential-password")
    private WebElement credentialPassword;
    @FindBy(id="editCredentialButton")
    private WebElement editCredentialButton;
    @FindBy(id="deleteCredentialButton")
    private WebElement deleteCredentialButton;
    @FindBy(id="closeButtonCred")
    private WebElement closeButtonCred;

    public void addNewNote(String title, String description)  {
        clickNoteTab();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNoteButton);
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveChangesNoteButton);
    }
    public void editNote(String title, String description)  {
        clickNoteTab();
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editNoteButton);
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveChangesNoteButton);
    }
    public void deleteNote(){
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteNoteButton);
    }
    public String getFirstNoteTitle(){
        return noteTitleOnDisplay.getAttribute("innerHTML");
    }
    public String getFirstNoteDescription(){
        return wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionOnDisplay)).getAttribute("innerHTML");
    }
    public void clickNoteTab( ) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.notesTab);
    }

    public void clickCredentialTab( ) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialsTab);
    }
    public String getFirstCredentialUrl(){
        return credentialUrlOnDisplay.getAttribute("innerHTML");
    }
    public String getFirstCredentialUsername(){
        return credentialUsernameOnDisplay.getAttribute("innerHTML");
    }
    public String getFirstCredentialPassword(){
        return credentialPasswordOnDisplay.getAttribute("innerHTML");
    }
    public String getFirstCredentialKey(){
        return credentialKey.getAttribute("innerHTML");
    }
    public WebElement getViewablePassword(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editCredentialButton);
        WebElement we = credentialPassword;
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButtonCred);
        return we;
    }
    public void addNewCredential(String url, String username, String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCredentialButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", credentialUrl);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", credentialPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveChangesCredButton);
    }
    public void editCredential(String username, String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editCredentialButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", credentialPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveChangesCredButton);
    }
    public void deleteCredential(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteCredentialButton);
    }



}