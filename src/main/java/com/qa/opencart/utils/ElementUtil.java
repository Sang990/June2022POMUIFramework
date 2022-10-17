package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private Select s;
	private Actions act;
	private JavascriptUtil jsUtil;
	
	private static final String ELEMENT_NOT_FOUND_ERROR = "Element is not avilable on the page: ";

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavascriptUtil(driver);
	}

	public WebElement getElement(By locator) {
		WebElement ele =  driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendKeys(By locator, String value) {
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrname) {
		return getElement(locator).getAttribute(attrname);
	}

	public boolean doEleIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean isSingleElementPresent(By locator) {

		List<WebElement> ele = getElements(locator);

		if (ele.size() == 1) {
			System.out.println("single element is present on the page");
			return true;
		} else {
			System.out.println("no search or multiple search field is present on page");
			return false;
		}
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public ArrayList<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);

		ArrayList<String> eleTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;

	}

	public void doUploadFile(By locator, String path) {
		getElement(locator).sendKeys(path);
	}

	// ******************************************Drop down Utils*******************************************************
	// only when html tag = select

	public void doSelectByIndex(By locator, int index) {
		s = new Select(getElement(locator));
		s.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String text) {
		s = new Select(getElement(locator));
		s.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String value) {
		s = new Select(getElement(locator));
		s.selectByValue(value);
	}

	public List<WebElement> getDropdownOptions(By locator) {
		s = new Select(getElement(locator));
		return s.getOptions();
	}

	public int getOptionsCount(By locator) {
		return getDropdownOptions(locator).size();
	}

	// without select
	public void doSelectValueFromDropdown(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equalsIgnoreCase(value)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, String searchKey, By suggLocator, String value) throws InterruptedException {
		getElement(searchLocator).sendKeys(searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(suggLocator);

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);

			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(String tagName, String text) {
		By suggLocator = By.xpath("//" + tagName + "[text()='" + text + "']");

		getElement(suggLocator).click();
	}

	/**
	 * this method is used to check if a field is mandatory
	 * 
	 * @param jsPath
	 * @return
	 */

	public boolean checkElementIsMandatory(String jsPath) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String mand_text = js.executeScript(jsPath).toString();

		System.out.println(mand_text);
		if (mand_text.contains("*")) {
			System.out.println("Element is a mandatory field");
			return true;
		} else {
			System.out.println("Element is not a mandatory field");
			return false;
		}

	}

	// ***************************************Action Utils***********************************************

	public void handleLevel1MenuItems(By parentMenu, By childMenu) throws InterruptedException {

		act.moveToElement(getElement(parentMenu)).build().perform();
		Thread.sleep(3000);

		doClick(childMenu);

	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).build().perform();

	}

	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).build().perform();

	}

	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
	}

	// ***************************************** Wait Utils*****************************************************************

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0. Default interval/polling time =
	 * 500ms
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public WebElement waitForElementVisible(By locator, int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible Customized polling time
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return
	 */

	public WebElement waitForElementToBeVisible(By locator, int timeOut, int pollingTime) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	public void doSendKeysWithWait(By locator, int timeOut, String value) {
		waitForElementPresence(locator, timeOut).sendKeys(value);
	}

	public void doClickWithWait(By locator, int timeOut) {
		waitForElementPresence(locator, timeOut).click();
	}

	public String getElementTextWithWait(By locator, int timeOut) {
		return waitForElementPresence(locator, timeOut).getText();
	}

	public boolean elementIsDisplayedWithWait(By locator, int timeOut) {
		return waitForElementPresence(locator, timeOut).isDisplayed();
	}

	public void clickWhenElementReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void doSendKeysWithVisible(By locator, int timeOut, String value) {
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}

	public void doClickWithVisible(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}

	public String getElementTextWithVisible(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut).getText();
	}

	public boolean elementIsDisplayedWithVisible(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut).isDisplayed();
	}

	public Alert waitForAlert(int timeOut) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {

		return waitForAlert(timeOut).getText();
	}

	public void acceptAlert(int timeOut) {

		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {

		waitForAlert(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {

		waitForAlert(timeOut).sendKeys(value);
	}

	public String waitForTitleContains(int timeOut, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		} else {
			return null;
		}
	}

	public String waitForTitleIs(int timeOut, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		if (wait.until(ExpectedConditions.titleIs(titleValue))) {
			return driver.getTitle();
		} else {
			return null;
		}
	}

	public String waitForUrlContains(int timeOut, String urlFraction) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			return driver.getCurrentUrl();
		} else {
			return null;
		}

	}

	public String waitForUrlIs(int timeOut, String urlValue) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			return driver.getCurrentUrl();
		} else {
			return null;
		}

	}

	public void waitForFrame(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	public void waitForFrame(int timeOut, String nameOrId) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));

	}

	public void waitForFrame(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public void waitForFrame(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public WebElement waitForElementToBeVisibleWithFluentWait(int timeOut, By locator, int pollingTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(ElementNotInteractableException.class)
				.withMessage(ElementUtil.ELEMENT_NOT_FOUND_ERROR + locator);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}
