package com.seleniumproject.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	//Objects
	
	@FindBy(linkText = "My Account")
	WebElement MyAccountDropdownMenu;
	
	@FindBy(linkText = "Login")
	WebElement loginOption;
	
	public HomePage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//Actions
	
	public void clickOnMyAccount() {
		
		MyAccountDropdownMenu.click();
	}
	
	public void selectLoginOption() {
		
		loginOption.click();
	}

}
