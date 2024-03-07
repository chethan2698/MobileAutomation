package Android.javaclient8;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.relevantcodes.extentreports.LogStatus;

import common.modules.CommonLibrary;
import common.modules.MobileDriver;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;


public class UiAutomatorOptions extends MobileDriver{
	CommonLibrary commonLib;
	MobileActions mobileAction;
	
	public UiAutomatorOptions() {
		this.commonLib = new CommonLibrary();
		this.mobileAction = new MobileActions();
	}

	
	@Test
	public void LaunchSuceApkRealDevice() throws Exception {

//		XCUITestOptions opt = new XCUITestOptions(); // ios
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("com.saucelabs.mydemoapp.android");		
		options.setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
		options.getUiautomator2ServerLaunchTimeout();
		
		
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Sauce Labs Bolt T-Shirt"))));	
		driver.findElement(AppiumBy.accessibilityId("Sauce Labs Bolt T-Shirt")).click();
		Reporter.log("Pass : Clicked on Sauce Labs Bolt T-Shirt");
		
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart"))));
		driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart")).click();
		Reporter.log("Pass : Clicked on add product to cart");
		
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV"))));
		driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
		Reporter.log("Pass : Clicked on cart");
		
//		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout"))));
		driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout")).click();
		Reporter.log("Pass : Clicked on CheckOut");
		
	}
	
	@Test
	public void LaunchChromeBrowser(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("LaunchChromeBrowser");
		
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.withBrowserName("Chrome");
		options.getUiautomator2ServerLaunchTimeout();
		
		
//		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
		commonLib.launchAndroidDriver(options);
		
		AndroidDriver driver = commonLib.getDriver();

		driver.get("https://test.salesforce.com/");
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		commonLib.log(LogStatus.PASS, "salesforce opened");
		
		String xpath = "//input[@id=\"%s\"]";
		//username	
		commonLib.log(LogStatus.PASS, "Salesforce login page opened");
		commonLib.getScreenshot();
		driver.findElement(AppiumBy.xpath(String.format(xpath, "username"))).sendKeys("harshitha.ul@stryker.com.stage");
		driver.findElement(AppiumBy.xpath(String.format(xpath, "password"))).sendKeys("stryker123");
		driver.findElement(AppiumBy.xpath(String.format(xpath, "Login"))).click();
		
		Reporter.log(driver.getTitle());
		commonLib.log(LogStatus.PASS, "Title is " + driver.getTitle());
		commonLib.getScreenshot();

		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://www.flipkart.com/");
		commonLib.log(LogStatus.PASS, "flipkart opened");
		commonLib.getScreenshot();
		
		
		commonLib.endTest();
	}
		
	@Test
	public void Tap(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("Tap");

		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("com.saucelabs.mydemoapp.android");		
		options.setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
		options.getUiautomator2ServerLaunchTimeout();
		
		
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();

	
		WebElement element = driver.findElement(AppiumBy.accessibilityId("Sauce Labs Bolt T-Shirt"));
		
//		mobileAction.Tap(element, driver);
		
		// UiAutomator BackEnd
		mobileAction.TapGesture(element, driver);
		
	}
		
	@Test
	public void DoubleTap(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("DoubleTap");

//		XCUITestOptions opt = new XCUITestOptions(); // ios
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("com.saucelabs.mydemoapp.android");		
		options.setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
		options.getUiautomator2ServerLaunchTimeout();
		
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();
	
		WebElement element = driver.findElement(AppiumBy.accessibilityId("View menu"));	
		
//		mobileAction.doubleTap(element, driver);
		
		mobileAction.DoubleClickGesture(element, driver);
		
	}
	
	@Test
	public void longPress(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("DoubleTap");

		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("io.appium.android.apis");		
		options.setAppActivity(".ApiDemos");
		options.getUiautomator2ServerLaunchTimeout();
				
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();
	
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();		
		driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
		WebElement element = driver.findElement(AppiumBy.xpath("//*[@text=\"People Names\"]"));
		
		//using Sequence class
//		mobileAction.longPress(element, driver);
		
		//using Actions class
//		Actions act = new Actions(driver);
//		act.clickAndHold(element).perform();
		
		mobileAction.longPressGesture(element, driver);
		
	}
	
	@Test
	public void ScrollDown(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("DoubleTap");

		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("io.appium.android.apis");		
		options.setAppActivity(".ApiDemos");
		options.getUiautomator2ServerLaunchTimeout();
				
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();

	
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		
//		mobileAction.ScrollDown(driver);
				
		// javascript executor
//		mobileAction.ScrollGesture(driver, "WebView");		
//		mobileAction.ScrollGesture(driver, "Animation");
		
		
		WebElement ele = driver.findElement(AppiumBy.accessibilityId("ImageView"));
		mobileAction.Scroll(driver, "down", 4.0);
		
		mobileAction.Scroll(driver, "up", 4.0);
				
	}
	
	@Test
	public void DragAndDrop(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("DoubleTap");

//		XCUITestOptions opt = new XCUITestOptions(); // ios
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("io.appium.android.apis");		
		options.setAppActivity(".ApiDemos");
		options.getUiautomator2ServerLaunchTimeout();
		
		
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();

	
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
		
		WebElement source = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
		WebElement target = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_2"));
		
		Point sourceElementCenter = mobileAction.getCenterOfElement(source.getLocation(), source.getSize());
		Point targetElementCenter = mobileAction.getCenterOfElement(target.getLocation(), target.getSize());
				
		mobileAction.DragAndDrop(sourceElementCenter, targetElementCenter, driver);
		
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"io.appium.android.apis:id/drag_result_text\"]"));
	
	}
	
	@Test
	public void Zoom(Method name) throws Exception {
		
		commonLib.initializeReports(name.getName(),"MobileAndroid"+"_"+name.getName());
		commonLib.startTest("LaunchChromeBrowser");
		
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.withBrowserName("Chrome");
		options.getUiautomator2ServerLaunchTimeout();
		
		
//		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
		commonLib.launchAndroidDriver(options);
		
		AndroidDriver driver = commonLib.getDriver();

		driver.get("https://test.salesforce.com/");
		
		String xpath = "//input[@id=\"%s\"]";
		WebElement element = driver.findElement(AppiumBy.xpath(String.format(xpath, "username")));

		Point centerOfElement = mobileAction.getCenterOfElement(element.getLocation(), element.getSize());
		mobileAction.zoom(centerOfElement, driver);
		
		Thread.sleep(5000);
		
		commonLib.endTest();
	}
		
	@Test
	public void Swipe(Method name) throws Exception {
		
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("io.appium.android.apis");		
		options.setAppActivity(".ApiDemos");
		options.getUiautomator2ServerLaunchTimeout();
		
		
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();

	
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
		driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();
		
		WebElement element = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@class=\"android.widget.ImageView\"])[1]"));
		
//		mobileAction.swipeLeft(element, driver);		
//		mobileAction.swipeRight(element, driver);
		
		
		mobileAction.SwipeGesture(element, driver, "left", 0.9);
				
		WebElement element8 = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@class=\"android.widget.ImageView\"])[3]"));
		mobileAction.SwipeGesture(element8, driver, "right", 0.9);
	}
		
	@Test
	public void DropDown(Method name) throws Exception {
		
		UiAutomator2Options options = new UiAutomator2Options(); // Android
		options.setDeviceName("Galaxy M51");
		options.setPlatformVersion("14.0");
		options.setUdid("RZ8N92QZY7M");
		options.setNoReset(false);
		options.setAppPackage("com.androidsample.generalstore");		
		options.setAppActivity(".MainActivity");
		options.getUiautomator2ServerLaunchTimeout();
		
		
		commonLib.launchAndroidDriver(options);		
		AndroidDriver driver = commonLib.getDriver();
		
		driver.executeScript("plugin: setWaitPluginProperties", ImmutableMap.of("timeout", 30000));
		
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
		
		mobileAction.ScrollGesture(driver, "Canada");
		
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Canada\"]")).click();

		
	}
			
	@Test
	public void RunSuceApkBrowserStack() throws Exception {
		
		DesiredCapabilities caps = new DesiredCapabilities();

		// Set your BrowserStack capabilities
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "12.0");
		caps.setCapability("deviceName", "Pixel 6 Pro");
		caps.setCapability("automationName", "UIAutomator2");
	
		// Set BrowserStack specific capabilities
		caps.setCapability("browserstack.user", "chethan_YJoU83");
		caps.setCapability("browserstack.key", "gRxzxHiM7c7VpYYWVYAw");
		caps.setCapability("app", "bs://8f96af53ac9e3b944df7add211f50c6dded47b38");
		caps.setCapability("browserstack.idleTimeout", "300");
		AppiumDriver driver = new AppiumDriver(
				new URL("http://hub-cloud.browserstack.com/"), caps);
	
	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Sauce Labs Bolt T-Shirt"))));
		driver.findElement(AppiumBy.accessibilityId("Sauce Labs Bolt T-Shirt")).click();
		Reporter.log("Pass : Clicked on Sauce Labs Bolt T-Shirt");
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart"))));
		driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart")).click();
		Reporter.log("Pass : Clicked on add product to cart");
		

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV"))));
		driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
		Reporter.log("Pass : Clicked on cart");
		

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout"))));
		driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout")).click();
		Reporter.log("Pass : Clicked on CheckOut");
		
	}
}
