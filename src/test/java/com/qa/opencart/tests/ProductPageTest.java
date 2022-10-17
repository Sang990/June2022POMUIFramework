package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class ProductPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {

		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductHeaderData(){
		return new Object[][] {
			{"Macbook" , "MacBook Air"},
			{"iMac" , "iMac"},
			{"Samsung" , "Samsung SyncMaster 941BW"},
		};
	}

	@Test(dataProvider="getProductHeaderData")
	public void productHeaderTest(String searchKey, String mainProductHeader) {
		srchPage = accPage.performSearch(searchKey);
		prdctInfoPage = srchPage.selectProduct(mainProductHeader);
		String actProductHeader = prdctInfoPage.getProductHeader(mainProductHeader);
		Assert.assertEquals(actProductHeader, mainProductHeader);
	}
	
	@DataProvider
	public Object[][] getProductInfoData() {
		
		return new Object[][] {
			{"Macbook" , "MacBook Pro", AppConstants.MACBOOK_PRO_IMAGES_COUNT},
			{"Macbook" , "MacBook Air" , AppConstants.MACBOOK_AIR_IMAGES_COUNT},
			{"iMac" , "iMac", AppConstants.IMAC_IMAGES_COUNT}
		};
	}

	@Test(dataProvider= "getProductInfoData")
	public void productImagesCountTest(String searchKey, String mainProductName, int imagesCount) {
		srchPage = accPage.performSearch(searchKey);
		prdctInfoPage = srchPage.selectProduct(mainProductName);
		int actProductImagesCount = prdctInfoPage.getProductImagesCount();
		System.out.println("Actual Product Images Count: " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, imagesCount);
	}
	
	@DataProvider
	public Object[][] getProductMetaData() {
		
		return new Object[][] {
			{"Macbook" , "MacBook Pro"}
//			{"Macbook" , "MacBook Air"},
//			{"iMac" , "iMac"}
		};
	}
	
	@Test(dataProvider="getProductMetaData")
	public void productMetaDataTest(String searchKey, String mainProduct) {
		srchPage = accPage.performSearch(searchKey);
		prdctInfoPage = srchPage.selectProduct(mainProduct);
		Map<String, String> actualMetaData = prdctInfoPage.getProductMetaData();
		Assert.assertEquals(actualMetaData.get("Brand"), "Apple");
		Assert.assertEquals(actualMetaData.get("Product Code"), "Product 18");
		Assert.assertEquals(actualMetaData.get("Reward Points"), "800");
		Assert.assertEquals(actualMetaData.get("Availability"), "In Stock");
	}

}
