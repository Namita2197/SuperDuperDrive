package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
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
//    @FindBy(id = "editNoteButton")
//    private WebElement editNoteButton;
//
//    @FindBy(id = "deleteNoteButton")
//    private WebElement deleteNoteButton;
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
    private WebDriverWait wait;
    public HomePage(WebDriver driver) {
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

    @FindBy(id = "credential-id")
    private WebElement credentialId;

    @FindBy(id = "credential-key")
    private WebElement credentialKey;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "saveChangesCredButton")
    private WebElement saveChangesCredButton;

    @FindBy(id = "noteTitleOnDisplay")
    private WebElement noteTitleOnDisplay;

    @FindBy(id = "credentialUrlOnDisplay")
    private WebElement credentialUrlOnDisplay;

    @FindBy(id = "noteDescriptionOnDisplay")
    private WebElement noteDescriptionOnDisplay;

//    public void goToNotes(){
//        this.notesTab.click();
//    }
    public void addNewNote(String title, String description) throws InterruptedException {

        Thread.sleep(1000);
        System.out.println("1");
//        this.notesTab.click();
        clickNoteTab();
        System.out.println("2");
//        Thread.sleep(4000);
//        this.addNoteButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
//        System.out.println("3");
//        Thread.sleep(4000);
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(title);
//        this.noteTitle.sendKeys(title);
//        System.out.println("4");
//        Thread.sleep(4000);
//        this.noteDescription.sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(description);
//        System.out.println("5");
//        Thread.sleep(4000);
//        this.saveChangesNoteButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesNoteButton)).click();
//        Thread.sleep(4000);
//        System.out.println("6");
        System.out.println("New note added");
    }

    public void addNewCredential(String url, String username, String password){
        this.credentialsTab.click();
        this.addCredentialButton.click();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.saveChangesCredButton.click();
    }

    public String getFirstNoteTitle(){
        return wait.until(ExpectedConditions.elementToBeClickable(noteTitleOnDisplay)).getText();
    }
    public String getFirstNoteDescription(){
        return wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionOnDisplay)).getText();
    }
    public String getFirstCredentialUrl(){
        return this.credentialUrlOnDisplay.getText();
    }

    public void clickNoteTab( ) throws InterruptedException {
//        wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
        System.out.println("Sleeping");
        Thread.sleep(3000);
        this.notesTab.click();
        System.out.println("Clicked");
    }





}
