package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedAccess(){
		driver.get(baseURL+"/home");
		assertEquals("Login", driver.getTitle());
		assertNotEquals("Home",driver.getTitle() );
	}

	@Test
	public void testUserSignupLogin() throws InterruptedException {
		String username = "nami";
		String password = "qwerty";


		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Namita", "Raghuvanshi", username, password);
		assertEquals("You successfully signed up! Please continue to the login page.",driver.findElement(By.id("success-msg")).getText());

		driver.get(baseURL+"/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		assertEquals("Home",driver.getTitle());

		driver.findElement(By.id("logoutId")).submit();
		assertNotEquals("Home",driver.getTitle());

		driver.get(baseURL+"/home");
		assertNotEquals("Home",driver.getTitle());
	}

	@Test
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
	public void login(){

	}

	@Test
	public void addNoteTest() throws InterruptedException {

		String username = "nami";
		String password = "qwerty";
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Namita", "Raghuvanshi", username, password);

		driver.get(baseURL+"/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		String title = "testNote";
		String description = "testDescription";
		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);
		homePage.addNewNote(title, description);

		resultPage.clickHere();
		System.out.println("Routing to home page to open note tab");
//		WebDriverWait wait = new WebDriverWait(driver, 20);
		homePage.clickNoteTab();
		System.out.println("getting value from note tab");
		String titleFetched = homePage.getFirstNoteTitle();
		String desFetched = homePage.getFirstNoteDescription();
		assertEquals(title, titleFetched);
		assertEquals(description, desFetched);

	}



}
