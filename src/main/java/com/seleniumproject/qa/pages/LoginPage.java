package com.seleniumproject.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	
	@FindBy(id = "input-email")
	private WebElement emailAddressField;
	
	@FindBy(xpath ="//input[@name='password']")
	private WebElement passwordField;
	
	
	@FindBy(xpath = "//input[@class='btn btn-primary']")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterEmailAddress(String emailText) {
		
		emailAddressField.sendKeys(emailText);
	}
	
	public void enterPassword(String passwordText) {
		
		passwordField.sendKeys(passwordText);
	}
	
	public void clickOnLoginButton() {
		
		loginButton.click();
	}

}
