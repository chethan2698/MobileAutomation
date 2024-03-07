package Android.javaclient7;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;


public class AndroidRealDevice {

	AppiumDriver driver;
	@Test
	public void runSauceLabApk() throws IOException {
		
//		DesiredCapabilities cap = new DesiredCapabilities();
//		
//		cap.setCapability("deviceName", "Galaxy M51");
//		cap.setCapability("udid", "RZ8N92QZY7M");
//		cap.setCapability("platformName", "Android");
//		cap.setCapability("platformVersion", "12");
//		cap.setCapability("automationName", "UIAutomator2");
//		cap.setCapability("noReset", "true");
//		cap.setCapability("appPackage", "com.saucelabs.mydemoapp.android");
//		cap.setCapability("appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
//		
//		URL url = new URL("http://127.0.0.1:4723/");
////		URL url = new URL("http://10.195.104.138:4723/");
//		 
//		driver = new AppiumDriver(url, cap);
//		
////		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
//		
//		driver.findElement(MobileBy.AccessibilityId("Sauce Labs Bolt T-Shirt")).click();
//		Reporter.log("Pass : Clicked on Sauce Labs Bolt T-Shirt");
//		getScreenshot("T-Shirt");
//		getScreenShot();
//		
//		driver.findElement(MobileBy.AccessibilityId("Tap to add product to cart")).click();
//		Reporter.log("Pass : Clicked on add product to cart");
//		getScreenshot("AddToCart");
//		getScreenShot();
//		
//		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
//		Reporter.log("Pass : Clicked on cart");
//		getScreenshot("Cart");
//		getScreenShot();
		
	}
	
	public void getScreenshot(String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File scr = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File("./screenshot/"+screenShotName+".png");
//		FileUtils.copyFile(scr, trg);
	}
	
	public void getScreenShot() {
	        
	        // Capture screenshot and save it to a location
	        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        
	        // Embed the screenshot into the HTML report
	        String screenshotPath = "./screenshot.png"; // Provide the actual path
	        Reporter.log("<br><img src='" + screenshotPath + "' height='300' width='400'/><br>");
	   
	}
	
	
	
	public void getScreenshotParticularSession(String locator, String screenShotName) throws IOException {
		
		WebElement element = driver.findElement(AppiumBy.accessibilityId(locator));
		File scr = element.getScreenshotAs(OutputType.FILE);
		File trg = new File("./screenshot/"+screenShotName+".png");
//		FileUtils.copyFile(scr, trg);
	}
	
}
