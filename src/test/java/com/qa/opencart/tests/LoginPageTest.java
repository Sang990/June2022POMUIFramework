package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic - 100: Open cart application login page design")
@Story("User story  - 101: Design login page features")
public class LoginPageTest extends BaseTest {
	
	@Description("login page title test...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void LoginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("login page url test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void loginPageUrlTest() {
		Assert.assertTrue(loginPage.getLoginPageUrl());
	}
	
	@Description("login page is displaying forgot password link.....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void isForgotPasswordLinkExistTest() {
		Assert.assertEquals(loginPage.isForgotPwdLinkExist(), true);
	}
	
	@Description("login page test with valid credentials.....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

}
