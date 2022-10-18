package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private static final Logger LOG = Logger.getLogger(AccountPage.class);

	private By logOutLink = By.linkText("Logout");
	private By search = By.name("search");
	private By searchBtn = By.cssSelector("div#search button");
	private By accountSectionHeaders = By.cssSelector("div#content h2");

	public AccountPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("Waiting for the title in the Account page and fetching it and returning the same...")	
	public String getAccPageTitle() {

		String title = eleUtil.waitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACC_PAGE_TITLE);
		System.out.println("Account Page title: " + title);
		LOG.info("Account Page title: " + title);
		return title;
	}

	@Step("Waiting for the Account page url and fetching it and returning the same...")
	public boolean getAccPageUrl() {

		String url = eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACC_PAGE_URL_PARAM);
		if (url.contains(AppConstants.ACC_PAGE_URL_PARAM)) {
			return true;
		}
		return false;
	}

	@Step("Checking is Logout link is displayed or not...")
	public boolean isLogoutLinkExist() {

		return eleUtil.doEleIsDisplayed(logOutLink);

	}

	@Step("Checking if Search is displayed or not...")
	public boolean isSearchExist() {

		return eleUtil.doEleIsDisplayed(search);

	}
	
	@Step("Perform Product Search : {0}")
	public SearchResultPage performSearch(String productKey) {
		System.out.println("Product name is: "+ productKey);
		
		if(isSearchExist()) {
			
			eleUtil.doSendKeys(search, productKey);
			eleUtil.doClick(searchBtn);
			
			return new SearchResultPage(driver);
		}
		else {
			System.out.println("Search field is not present in the Account page");
		}
		return null;
	}

	@Step("Get account section headers")
	public ArrayList<String> getAccSectionHeaders() {

		List<WebElement> sectionList = eleUtil.waitForElementsToBeVisible(accountSectionHeaders,
				AppConstants.DEFAULT_LARGE_TIME_OUT);

		System.out.println("total section headers: " + sectionList.size());

		ArrayList<String> accSecTextList = new ArrayList<String>();

		for (WebElement e : sectionList) {
			String text = e.getText();
			accSecTextList.add(text);
		}
		return accSecTextList;
	}

}
