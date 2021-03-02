package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.sound.midi.ShortMessage;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	CredentialService credentialService;

	@LocalServerPort
	private int port;

	private WebDriver driver;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}

	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testUnauthorizedAccess(){
		driver.get(baseURL+"/home");
		assertEquals("Login", driver.getTitle());
		assertNotEquals("Home",driver.getTitle() );
	}

	@Test
	@Order(3)
	public void testUserSignupLogin(){
		signupUser();
		assertEquals("Login",driver.getTitle());

		loginUser();
		assertEquals("Home",driver.getTitle());

		driver.findElement(By.id("logoutId")).submit();
		assertNotEquals("Home",driver.getTitle());

		driver.get(baseURL+"/home");
		assertNotEquals("Home",driver.getTitle());
	}

	public void loginUser(){
		String username = "nami";
		String password = "qwerty";
		driver.get(baseURL+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}
	public void signupUser(){
		String username = "nami";
		String password = "qwerty";
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Namita", "Raghuvanshi", username, password);
	}

	@Test
	@Order(4)
	public void testDuplicateSignup(){
		String username = "nami";
		String password1 = "qwerty1";
		String password2 = "qwerty2";


		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Namita", "Raghuvanshi", username, password1);
		driver.get(baseURL + "/signup");
		signupPage.signup("Namita2", "Raghuvanshi2", username, password2);
		assertEquals("This username already exits! Please try signing up with another username",driver.findElement(By.id("error-msg")).getText());
	}

	@Test
	@Order(5)
	public void testCreateNote(){
		String title = "testNote";
		String description = "testDescription";

		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.addNewNote(title, description);
		resultPage.clickHere();
		homePage.clickNoteTab();
		String titleFetched = homePage.getFirstNoteTitle();
		String desFetched = homePage.getFirstNoteDescription();
		homePage.logoutUser();
		assertEquals(title, titleFetched);
		assertEquals(description, desFetched);

	}

	@Test
	@Order(6)
	public void testEditNote(){
		String title = "testNote";
		String description2 = "changed description";

		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.editNote(title, description2);
		resultPage.clickHere();
		homePage.clickNoteTab();
		String titleFetched2 = homePage.getFirstNoteTitle();
		String desFetched2 = homePage.getFirstNoteDescription();
		homePage.logoutUser();

		assertEquals(title, titleFetched2);
		assertEquals(description2, desFetched2);

	}

	@Test
	@Order(7)
	public void testDeleteNote(){
		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.clickNoteTab();
		homePage.deleteNote();
		resultPage.clickHere();
		homePage.clickNoteTab();
		homePage.logoutUser();
		assertThrows(NoSuchElementException.class, () -> {homePage.getFirstNoteTitle();});
	}
	@Test
	@Order(8)
	public void createCredentialTest(){
		String url = "www.udacity.com";
		String credusername = "nammu";
		String credpassword = "qwer**";

		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.addNewCredential(url, credusername, credpassword);
		resultPage.clickHere();
		homePage.clickCredentialTab();

		String urlFetched = homePage.getFirstCredentialUrl();
		String usernameFetched = homePage.getFirstCredentialUsername();
		String encryptedPassword = homePage.getFirstCredentialPassword();
		String decryptedPassword = encryptionService.decryptValue(encryptedPassword, credentialService.getCredentialById(1).getKey());

		WebElement shouldBeDecrypted = homePage.getViewablePassword();
		String shouldBeDecryptedString = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", shouldBeDecrypted);
		homePage.logoutUser();

		assertEquals(url, urlFetched);
		assertEquals(credusername, usernameFetched);
		assertNotEquals(encryptedPassword, credpassword);
		assertEquals(credpassword, shouldBeDecryptedString);
		assertEquals(credpassword, decryptedPassword);

	}
	@Test
	@Order(9)
	public void editCredentialTest(){
		String credusername = "nammu";
		String credpassword2 = "qwerty**";

		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.editCredential(credusername, credpassword2);
		resultPage.clickHere();
		homePage.clickCredentialTab();
		String encryptedPassword2 = homePage.getFirstCredentialPassword();
		String changedPassword = encryptionService.decryptValue(encryptedPassword2, credentialService.getCredentialById(1).getKey());
		homePage.logoutUser();

		assertEquals(credpassword2, changedPassword);

	}
	@Test
	@Order(10)
	public void deleteCredentialTest(){
		loginUser();

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.deleteCredential();
		resultPage.clickHere();
		homePage.clickNoteTab();
		assertThrows(NoSuchElementException.class, () -> {homePage.getFirstCredentialUrl();});

	}


//	@Test
//	@Order(8)
//	public void credentialTest(){
//		String url = "www.udacity.com";
//		String credusername = "nammu";
//		String credpassword = "qwer**";
//		String credpassword2 = "qwerty**";
//		signupUser();
//		loginUser();
//		HomePage homePage = new HomePage(driver);
//		ResultPage resultPage = new ResultPage(driver);
//
//		homePage.addNewCredential(url, credusername, credpassword);
//		resultPage.clickHere();
//		homePage.clickCredentialTab();
//		String urlFetched = homePage.getFirstCredentialUrl();
//
//		String usernameFetched = homePage.getFirstCredentialUsername();
//		String encryptedPassword = homePage.getFirstCredentialPassword();
//		String decryptedPassword = encryptionService.decryptValue(encryptedPassword, credentialService.getCredentialById(1).getKey());
//
//		WebElement shouldBeDecrypted = homePage.getViewablePassword();
//		String shouldBeDecryptedString = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", shouldBeDecrypted);
//
//		homePage.editCredential(credusername, credpassword2);
//		resultPage.clickHere();
//		homePage.clickCredentialTab();
//		String encryptedPassword2 = homePage.getFirstCredentialPassword();
//		String changedPassword = encryptionService.decryptValue(encryptedPassword2, credentialService.getCredentialById(1).getKey());
//
//		homePage.deleteCredential();
//		resultPage.clickHere();
//		homePage.clickNoteTab();
//		assertThrows(NoSuchElementException.class, () -> {homePage.getFirstCredentialUrl();});
//
//		assertEquals(url, urlFetched);
//		assertEquals(credusername, usernameFetched);
//		assertNotEquals(encryptedPassword, credpassword);
//		assertEquals(credpassword, shouldBeDecryptedString);
//		assertEquals(credpassword, decryptedPassword);
//		assertEquals(credpassword2, changedPassword);
//	}

}
