package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exception.FrameworkException;

import org.apache.log4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;
	
	private static final Logger LOG = Logger.getLogger(DriverFactory.class);

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * this method is used to initialize the driver on the basis of given browser
	 * name
	 * 
	 * @param prop
	 * @return will return the driver instance
	 */

	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser").toLowerCase();

		System.out.println("Browser name: " + browserName);
		LOG.info("Browser name is: "+ browserName);

		highlight = prop.getProperty("highlight").trim();

		optionsManager = new OptionsManager(prop);

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		} else if (browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver());
		}

		else if (browserName.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver());
		} else {
			System.out.println("Please pass the right browser name");
			LOG.error("Please pass the right browser name" +browserName);
			throw new FrameworkException(AppError.BROWSER_NOT_FOUND);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is used to initialize the config properties
	 * 
	 * @return this will return properties
	 */

	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;;

		// mvn clean install -Denv="qa"
		
        //String envName = System.getenv("env");
		String envName = System.getProperty("env");
		System.out.println("--------> Running testcases on environment------>" + envName);
		LOG.info("--------> Running testcases on environment------>" + envName);

		if (envName == null) {
			System.out.println("No environment is given...hence running it on qa env");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {

			try {
				switch (envName) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;

				case "stg":
					ip = new FileInputStream("./src/test/resources/config/stg.config.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the correct environment"+envName);
					throw new FrameworkException(AppError.ENV_NOT_FOUND);
					//break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * this method is used to take screenshot
	 * @return
	 */
	
	public static String getScreenshot() {
		
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir") + "/screenshot/"+ System.currentTimeMillis() + ".png";
		
		File destination = new File(path);
		
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
