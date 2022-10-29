package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.Logger;

import com.qa.opencart.constants.AppConstants;

import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private static final Logger LOG = Logger.getLogger(LoginPage.class);
	
	//1. By locator
	
	private By emailID = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By logo = By.cssSelector("img.img-responsive");
	private By forgotPswdLink = By.cssSelector("div[class='form-group'] a");
	private By registerLink = By.linkText("Register");
	
	//2. Page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. Page Actions
	
	@Step("Waiting for the title in the Login page and fetching it and returning the same...")
	public String getLoginPageTitle() {
		
		String title= eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE);
		System.out.println("Login Page title: " + title);	
		LOG.info("Login Page title: " + title);
		return title;
	}
	
	@Step("Waiting for login page url and fetching it and returning the same...")
	public boolean getLoginPageUrl() {
		String url = eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_PARAM);
		System.out.println("Login page url: "+url);		
		if(url.contains(AppConstants.LOGIN_PAGE_URL_PARAM)) {
			return true;
		}
		return false;
	}
	
	@Step("Checking forgot password link is displayed in the login page or not...")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doEleIsDisplayed(forgotPswdLink);
		
	}
	
	@Step("Login with username : {0} and password : {1} ")
	public AccountPage doLogin(String userName, String pwd) {
		
		System.out.println("User login credentials are: "+ userName + ":" + pwd);
		
		eleUtil.doSendKeysWithWait(emailID, AppConstants.DEFAULT_LARGE_TIME_OUT, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountPage(driver);
	}
	
	@Step("Checking if logo is displayed...")
	public boolean isLogoDisplayed() {
		return eleUtil.doEleIsDisplayed(logo);
	}
	
	@Step("Navigating to Register Page...")
	public RegisterPage navigateToRegisterPage() {
		System.out.println("Navigating to Register Page");
		eleUtil.doClick(registerLink);
		
		return new RegisterPage(driver);
	}
	

}
