package com.seleniumproject.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.seleniumproject.qa.base.Base;
import com.seleniumproject.qa.tests.Utilities;

public class Register extends Base {

	public Register() {
		super();
	}

	WebDriver driver;

	@AfterMethod
	public void teadDown() {

		System.out.println("After Method- Close Browser");

		driver.quit();

	}

	@BeforeMethod
	public void setUp() {

		loadPropertiesFile();
		driver = initializeBrowserAndOpenURL(prop.getProperty("browserName"));
		driver.findElement(By.linkText("My Account")).click();
		driver.findElement(By.linkText("Register")).click();

	}

	@Test(priority = 1)
	public void VerifyUserRegistrationWithMandatoryFields() throws InterruptedException {

		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailTimestamp());
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.id("input-confirm")).sendKeys(dataProp.getProperty("confirmPassword"));
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		Thread.sleep(4000);

	}

	@Test(priority = 2)
	public void VerifyUserRegisstrationWithSubscription() throws InterruptedException {

		driver.findElement(By.id("input-firstname")).sendKeys(dataProp.getProperty("firstName"));
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(dataProp.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailTimestamp());
		driver.findElement(By.id("input-telephone")).sendKeys(dataProp.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(dataProp.getProperty("password"));
		driver.findElement(By.id("input-confirm")).sendKeys(dataProp.getProperty("confirmPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessMessage = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		String expectedSuccessMessage = dataProp.getProperty("accountSuccessfullyCreatedMessage");

		Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage);

		Thread.sleep(4000);

	}

	@Test(priority = 3)
	public void VerifyUserRegistrationWithAlreadyRegisteredEmail() throws InterruptedException {

		driver.findElement(By.id("input-firstname")).sendKeys("demouser1234");
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("demolname");
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("validEmail"));
		driver.findElement(By.id("input-telephone")).sendKeys("8979879879");
		driver.findElement(By.id("input-password")).sendKeys("123456");
		driver.findElement(By.id("input-confirm")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.xpath("//input[@name='agree']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualWarning = driver.findElement(By.xpath("//div[contains(@class, 'alert-dismissible')]")).getText();
		String expectedWarningMessage = dataProp.getProperty("duplicateEmailWarning");

		Assert.assertTrue(actualWarning.contains(expectedWarningMessage));

		Thread.sleep(4000);

	}

	@Test(priority = 4)
	public void VerifyUserRegistsrationValidations() throws InterruptedException {

		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualPrivacyPolicy = driver.findElement(By.xpath("//div[contains(@class, 'alert-dismissible')]"))
				.getText();
		String expectedPrivacyPolicy = dataProp.getProperty("privacyPolicyWarning");

		Assert.assertTrue(actualPrivacyPolicy.contains(expectedPrivacyPolicy));

		String actualFirstNameMessage = driver
				.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText();
		String expectedFirstNameMessage = dataProp.getProperty("firstNameWarning");

		Assert.assertEquals(actualFirstNameMessage, expectedFirstNameMessage);

		Thread.sleep(2000);

	}

}
