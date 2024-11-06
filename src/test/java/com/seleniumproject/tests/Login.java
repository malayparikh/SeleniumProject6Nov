package com.seleniumproject.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.seleniumproject.qa.base.Base;
import com.seleniumproject.qa.pages.HomePage;
import com.seleniumproject.qa.pages.LoginPage;
import com.seleniumproject.qa.tests.Utilities;

//import com.seleniumproject.qa.tests.Utilities;

public class Login extends Base {

	ExtentReports extent; // Instance for the entire class
	ExtentTest test; // Instance for each test case
	WebDriver driver;

	public Login() {

		super();
	}

	@BeforeClass
	public void setupExtent() {
		// Initialize extent reports once before any test
		ExtentSparkReporter spark = new ExtentSparkReporter("target/LoginTests.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);

	}

	@AfterClass
	public void flushExtent() {

		if (extent != null) {
			extent.flush();
		}

	}

	@AfterMethod
	public void teadDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test Skipper: " + result.getThrowable());
		}

		System.out.println("After Method- Close Browser");

		driver.quit();

	}

	@BeforeMethod
	public void setUp() {

		loadPropertiesFile();
		driver = initializeBrowserAndOpenURL(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		homePage.selectLoginOption();
	
		}

	@Test(priority = 1, dataProvider = "validCredentialSupplier")
	public void verifyLoginWithValidCredentials(String email, String password) throws InterruptedException {

		test = extent.createTest("verify the Login With Valid ;Credentials");
		
		LoginPage loginPage = new LoginPage(driver);

		loginPage.enterEmailAddress(email);
		loginPage.enterPassword(password);
		loginPage.clickOnLoginButton();
		
	
	
		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed());
		System.out.println("Test Method-1");

	}

	@DataProvider(name = "validCredentialSupplier")
	public Object[][] supplyTestData() {

		Object[][] data = Utilities.getTestDataFromExcel("Login");
		return data;

	}

	@Test(priority = 2)
	public void VerifyLoginWithValidEmailInvalidPassword() throws InterruptedException {

		test = extent.createTest("Verify Login With Valid Email Invalid Password");

		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailTimestamp());
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(dataProp.getProperty("invalidPassword"));
		driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();
		String actualMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert')]")).getText();
		String expectedMessage = dataProp.getProperty("emailPasswordNotMatchingWarning");
		Assert.assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("Test Method-2");

	}

	@Test(priority = 3)
	public void VerifyLoginWithInValidEmaiValidPassword() throws InterruptedException {

		test = extent.createTest("Verify Login With InValid Emai and Valid Password");

		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailTimestamp());
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234556789");
		driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();
		String actualMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert')]")).getText();
		String expectedMessage = dataProp.getProperty("emailPasswordNotMatchingWarning");
		Assert.assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("Test Method-3");

	}

	@Test(priority = 4)
	public void VerifyLoginWithInValidEmaiInValidPassword() throws InterruptedException {

		test = extent.createTest("Verify Login With InValid Emai and InValid Password");

		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailTimestamp());
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234556789");
		driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();
		String actualMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert')]")).getText();
		String expectedMessage = dataProp.getProperty("emailPasswordNotMatchingWarning");
		Assert.assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("Test Method-4");

	}

	@Test(priority = 5)
	public void VerifyLoginWithBlankEmailAndPassword() throws InterruptedException {

		test = extent.createTest("Verify Login With Blank Email And Password");

		driver.findElement(By.id("input-email")).sendKeys("");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("");
		driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();
		String actualMessage = driver.findElement(By.xpath("//div[contains(@class, 'alert')]")).getText();
		String expectedMessage = dataProp.getProperty("emailPasswordNotMatchingWarning");
		Assert.assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("Test Method-5");

	}

}
