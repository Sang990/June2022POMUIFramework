package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstname = By.id("input-firstname");
	private By lastname = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By cnfrmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value='1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value='0']");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By agreeCheckbox = By.name("agree");
	private By registerSuccMsg = By.cssSelector("div#content h1");
	private By logoutlink = By.linkText("Logout");
	private By registerlink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	/**
	 * this is user registration method
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param telephone
	 * @param password
	 * @param subscribe
	 * @return 
	 */
	public String userRegister(String firstname, String lastname, String email, String telephone, String password, String subscribe) {
		
		eleUtil.doSendKeysWithVisible(this.firstname, AppConstants.DEFAULT_LARGE_TIME_OUT, firstname);
		eleUtil.doSendKeys(this.lastname, lastname);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.cnfrmPassword, password);
		
		if(subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(this.agreeCheckbox);
		eleUtil.doClick(this.continueBtn);
		
		String successMsg = eleUtil.waitForElementVisible(registerSuccMsg, AppConstants.DEFAULT_LARGE_TIME_OUT).getText();
		System.out.println("Success Message: "+ successMsg);
		
		eleUtil.doClick(logoutlink);
		eleUtil.doClick(registerlink);
		
		return successMsg;
	}

}
