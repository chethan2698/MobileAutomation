package Android.javaclient7;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.errorprone.annotations.Var;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class BSAppiumMyDemoApp {

//	private AndroidDriver driver;
//	WebDriver driver;
	
	AppiumDriver driver = null;

	public BSAppiumMyDemoApp() {

	}

	public static final String UserName = "gowsalyams_XVidwB";
	public static final String AccessKey = "Rp9iHz1E1M4Dxm8gq38o";
	public static final String URL = "https://" + UserName + ":" + AccessKey + "@hub-cloud.browserstack.com/";

	@Test
	public void RunSuceApk() throws Exception {

		DesiredCapabilities caps = new DesiredCapabilities();

		// Set your BrowserStack capabilities
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "14.0");
		caps.setCapability("deviceName", "Google Pixel 8");
		caps.setCapability("automationName", "UIAutomator2");
		caps.setCapability("app", "bs://e70ac3d1db6aa3e3c354ec26504e42274416f193");//
		caps.setCapability("browserstack.idleTimeout", "300");

		// Set BrowserStack specific capabilities
		caps.setCapability("browserstack.user", "chethanr_BGLVdX");
		caps.setCapability("browserstack.key", "6xsYz93eqBJPudyNVqSq");
		driver = new AppiumDriver(
				new URL("http://hub-cloud.browserstack.com/wd/hub"), caps);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(MobileBy.AccessibilityId("Sauce Labs Bolt T-Shirt")).click();
		Reporter.log("Pass : Clicked on Sauce Labs Bolt T-Shirt");
		getScreenShot();
		Thread.sleep(5000);
		driver.findElement(MobileBy.AccessibilityId("Tap to add product to cart")).click();
		Reporter.log("Pass : Clicked on add product to cart");
		getScreenShot();

		Thread.sleep(5000);
		driver.findElement(MobileBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
		Reporter.log("Pass : Clicked on cart");
		getScreenShot();

		Thread.sleep(5000);
		driver.findElement(MobileBy.AccessibilityId("Confirms products for checkout")).click();
		Reporter.log("Pass : Clicked on CheckOut");
		getScreenShot();
	}

	public void getScreenShot() {

		// Capture screenshot and save it to a location
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Embed the screenshot into the HTML report
		String screenshotPath = "./screenshot.png"; // Provide the actual path
		Reporter.log("<br><img src='" + screenshotPath + "' height='300' width='400'/><br>");

	}

}
