package com.qa.opencart.tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic - 100: Open cart application Account Page design")
@Story("User story  - 102: Design Account page features")
public class AccountPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("Account page title test...Dev Name @Naveen")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void accPageTitleTest() {
		String actualtitle = accPage.getAccPageTitle();
		Assert.assertEquals(actualtitle, AppConstants.ACC_PAGE_TITLE);
	}
	
	@Description("Account page url test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void accPageUrlTest() {
		Assert.assertTrue(accPage.getAccPageUrl());
	}
	
	@Description("Account page Search test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void isSearchExistTest() {
		Assert.assertEquals(accPage.isSearchExist(), true);
	}
	
	@Description("Account page logout link test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=4)
	public void islogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Description("Account page header test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=5)
	public void accHeadersTest() {
		ArrayList<String> actualHeadersList = accPage.getAccSectionHeaders();
		System.out.println("Page Headers: "+ actualHeadersList);
		Assert.assertEquals(actualHeadersList, AppConstants.ACC_PAGE_SECTION_HEADER_LIST);
	}
	
	@DataProvider
	public Object[][] getProductKey() {
		
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"}
		};
	}
	
	@Description("Account page check Product search test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getProductKey" , priority=6)
	public void searchCheckTest(String productKey) {
		srchPage = accPage.performSearch(productKey);
		Assert.assertTrue(srchPage.isSearchSuccessful());
	}
	
	@DataProvider
	public Object[][] getProductData() {
		
		return new Object[][] {
			{"Macbook" , "MacBook Pro"},
			{"Macbook" , "MacBook Air"},
			{"iMac" , "iMac"},
			{"Samsung" , "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"}
			
		};
	}
	
	@Description("Account page Product search test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="getProductData", priority=7)
	public void searchTest(String searchKey, String mainProductName) {
		srchPage = accPage.performSearch(searchKey);
		if(srchPage.isSearchSuccessful()) {
			prdctInfoPage = srchPage.selectProduct(mainProductName);
			String actualProductHeader = prdctInfoPage.getProductHeader(mainProductName);
			Assert.assertEquals(actualProductHeader, mainProductName);
		}
	}

}
