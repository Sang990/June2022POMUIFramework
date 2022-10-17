package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100: Open cart application Register Page design")
@Story("User story  - 103: Design Register page features")
public class RegistrationTest extends BaseTest {
	
	@BeforeClass
	public void registrationSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegistrationTestData() {
		
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	public String getRandomEmail() {
		
		Random random = new Random();
		String email = "automationtest"+random.nextInt(10000)+"@gmail.com";
		return email;
	}
	
	@Description("Register page registration test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="getRegistrationTestData")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		String actSuccMsg = registerPage.userRegister(firstName, lastName, getRandomEmail(), telephone, password, subscribe);
		Assert.assertEquals(actSuccMsg, AppConstants.ACC_CREATE_SUCCESS_MSG);
	}

}
