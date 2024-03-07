
package common.modules;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import com.relevantcodes.extentreports.ExtentTest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.codoid.products.exception.FilloException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;


public class CommonLibrary 
{

	public AndroidDriver driver ; 
	public String locatorFileName;
	public static Logger Log;
	public Properties configData;
	public Properties globalConfigData;
	public WebDriverWait wait;
	public SoftAssert softAssert;
	public JavaUtil javaUtil;
	public ReportUtil reportUtil;
	public ExcelJsonUtil exJsonUtil;


	public CommonLibrary() 
	{	
		this.javaUtil=new JavaUtil(this);
		this.reportUtil=new ReportUtil(this);
		this.exJsonUtil=new ExcelJsonUtil(this,javaUtil);
		
	}

	
	public void setDriver(AndroidDriver driver) {
		this.driver = driver;
	}
	
	public AndroidDriver getDriver() {
		return driver;
	}
	
	public void launchAndroidDriver(UiAutomator2Options options) throws MalformedURLException {
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
	}
	
	
	/*
				 ============================================================================================================================
				 Function Name    : createReportFolder
				 Description      : To create a testreport folder
				 Arguments        : NA
				 Return value     : Returns folder location in String format
				 Example		  : createReportFolder()					
				============================================================================================================================
	 */
	public String createReportFolder() {
		return reportUtil.createReportFolder();
	}

	/*
				 ============================================================================================================================
				 Function Name    : startTest
				 Description      : To start the extent test case
				 Arguments        : NA
				 Return value     : ExtentTest report object
				 Example		  : startTest(Test)					
				============================================================================================================================
	 */
	public ExtentTest startTest(String TestCaseName) {
		return reportUtil.startTest(TestCaseName);
	}

	/*
				 ============================================================================================================================
				 Function Name    : endTest
				 Description      : To end the extent test report
				 Arguments        : NA
				 Return value     : NA
				 Example		  : endTest(Test)					
				============================================================================================================================
	 */
	public void endTest(ExtentTest test) {
		reportUtil.endTest(test);
	}

	/*
				 ============================================================================================================================
				 Function Name    : closeTestCaseExecution
				 Description      : To close the test case execution
				 Arguments        : NA
				 Return value     : NA
				 Example		  : closeTestCaseExecution()					
				============================================================================================================================
	 */
	public void closeTestCaseExecution() throws IOException {
		reportUtil.closeTestCaseExecution();
	}

	/*
				 ============================================================================================================================
				 Function Name    : endTest
				 Description      : To end the extent test report
				 Arguments        : NA
				 Return value     : NA
				 Example		  : endTest()					
				============================================================================================================================
	 */
	public void endTest() {

		reportUtil.endTest();
	}

	/*
				 ============================================================================================================================
				 Function Name    : closeExtent
				 Description      : To close the extent report
				 Arguments        : NA
				 Return value     : NA
				 Example		  : closeExtent()					
				============================================================================================================================
	 */
	public void closeExtent() {
		reportUtil.closeExtent();
	}

	/*
				 ============================================================================================================================
				 Function Name    : initializeReports
				 Description      : To initialize extent report object
				 Arguments        : reportName
				 Return value     : ExtentReport object
				 Example		  : addBuildNumberToReport("1313")					
				============================================================================================================================
	 */
	public ExtentReports initializeReports(String reportName) {

		return reportUtil.initializeReports(reportName);
	}


	/*
				 ============================================================================================================================
				 Function Name    : initializeReports
				 Description      : To initialize extent report object with testcase ID and Reportname
				 Arguments        : testcaseId, reportName
				 Return value     : ExtentReport object
				 Example		  : initializeReports("1313","testReport")					
				============================================================================================================================
	 */
	public ExtentReports initializeReports(String testcaseId,String reportName) {

		return reportUtil.initializeReports(testcaseId,reportName);
	}


	/*
				 ============================================================================================================================
				 Function Name    : addBuildNumberToReport
				 Description      : To add build number to the report
				 Arguments        : buildNumber
				 Return value     : NA
				 Example		  : addBuildNumberToReport("1313")					
				============================================================================================================================
	 */
	public void addBuildNumberToReport(String buildNumber) {

		reportUtil.addBuildNumberToReport(buildNumber);
	}

	/*
				 ============================================================================================================================
				 Function Name    : log
				 Description      : To log result to the extent report
				 Arguments        : LogStatus, details
				 Return value     : NA
				 Example		  : log(LogStatus.PASS, "Test step passed")					
				============================================================================================================================
	 */
	public void log(LogStatus LogStatus, String details) {

		reportUtil.log(LogStatus, details);
	}

	/*
				 ============================================================================================================================
				 Function Name    : getExtent
				 Description      : To get the ExtentReport object for reporting
				 Arguments        : NA
				 Return value     : Returns ExtentReports object 
				 Example		  : getExtent()					
				============================================================================================================================
	 */
	public ExtentReports getExtent() {
		return reportUtil.getExtent();
	}

	/*
				 ============================================================================================================================
				 Function Name    : setExtent
				 Description      : To set the ExtentReport object for reporting
				 Arguments        : extent
				 Return value     : NA
				 Example		  : setExtent(ExtentReport)					
				============================================================================================================================
	 */
	public void setExtent(ExtentReports extent) {
		reportUtil.setExtent(extent);
	}

	/*
				 ============================================================================================================================
				 Function Name    : getTest
				 Description      : To get the ExtentTest object
				 Arguments        : NA
				 Return value     : Returns ExtentTest object
				 Example		  : getTest()					
				============================================================================================================================
	 */
	public ExtentTest getTest() {
		return reportUtil.getTest();
	}

	/*
				 ============================================================================================================================
				 Function Name    : setTest
				 Description      : To initialize logger for specific class
				 Arguments        : NA
				 Return value     : NA
				 Example		  : setLog("Test_Login")					
				============================================================================================================================
	 */
	public void setTest(String testCaseName) {
		reportUtil.setTest(testCaseName);
	}

	/*
				 ============================================================================================================================
				 Function Name    : updateResult
				 Description      : To update testcase result in the summary sheet
				 Arguments        : workbookname, sheetName, testcaseID, testStatus
				 Return value     : NA
				============================================================================================================================
	 */
	public void updateResult(String workbookname, String sheetName, Map<Integer, List<String>> testresult) throws Exception{

		exJsonUtil.updateResult(workbookname, sheetName,testresult);
	}

	/*
				 ============================================================================================================================
				 Function Name    : verifyTCPresenceInSummarySheet
				 Description      : To verify presence of TC in the summary sheet, if not it will add to the summary sheet
				 Arguments        : workbookname, sheetName, testcaseID
				 Return value     : NA
				============================================================================================================================
	 */
	public void verifyPresenceofSummarySheet(String workbookname, String sheetName) throws Exception{
		exJsonUtil.verifyPresenceofSummarySheet(workbookname, sheetName);
	}
	
	public String getRandomString(String alphabets) {
		return javaUtil.getRandomString(alphabets);
	}
	
	public String getGlobalConfigurationValue(String requiredKey) {
		return globalConfigData.getProperty(requiredKey);
	}	
	
	public  void syso(String printText) {
		Log.info(printText);

	}
	
	public String getConfigValue(String inputKey) {
		return configData.getProperty(inputKey);
	}
	
	public String add_Minus_Date(int i)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, i);
		return dateFormat.format(cal.getTime());


	}
	
	public String add_Minus_Date(int i, String format)
	{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
		String currentTimeZone = cal.getTimeZone().getDisplayName();
		cal.add(Calendar.DATE, i);
		return dateFormat.format(cal.getTime());


	}
	
	public String getRandomNumber(int digCount) {
		return javaUtil.getRandomNumber(digCount);
	}
	
	public void getScreenshot() {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String screenshotName = "Screenshot-" + timeStamp + ".png";
			String screenshotPathToSave = reportUtil.reportFolderLocation + "\\"
			+ "Screenshots\\" + screenshotName;
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File f = new File(screenshotPathToSave);
			FileUtils.copyFile(scrFile, f);
			
				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(f);
				byte byteArray[] = new byte[(int) f.length()];
				fis.read(byteArray);
				String imageString = Base64.encodeBase64String(byteArray);
				reportUtil.log(LogStatus.INFO, "Snapshot below: " + screenshotName
						+ reportUtil.test.addBase64ScreenShot("data:image/png;base64," + imageString));
				//test.addBase64ScreenShot("data:image/png;base64," + imageString);

		}
		catch (Exception e) {
			reportUtil.log(LogStatus.FAIL,"Unable to take screenshot"+e);
		}
	}
	
	public void setLocatorFileName(String locatorKey) {
		locatorFileName = getConfigValue(locatorKey);
	}
	
	public void setGlobalConfigurationValue(String configfolder,String fileNamewithoutExt) 
	{
		//String fileName = "global-config.properties";
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(System.getProperty("user.dir") +"/"+configfolder +"/"+fileNamewithoutExt+".properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();		}

		globalConfigData = new Properties();
		try {
			globalConfigData.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setConfigurationFile(String configFileName) {

		/*	projectFilePath = System.getProperty("user.dir") + getGlobalConfigurationValue("projectCommonPath")
									+ projectName;*/
		String filePath = System.getProperty("user.dir") +"/"+"config" +"/"+configFileName+".properties";

		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			configData = new Properties();
			configData.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getLocatorValue(String requiredKey) {

		String fileName = null;

		// New Path is as follows
		fileName =System.getProperty("user.dir")+"/"+"locators" +"/"+"android.properties";

		//		syso("Locator Filepath is " + fileName);
		// syso("Key whose value is being fetched is: " + requiredKey);
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties pro = new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String valueData = pro.getProperty(requiredKey);
		// syso("Value for the required key "+requiredKey+ " is: "+ valueData);

		//GXIC -Team update if the locator name is not available in locators file, It will display log message in report and also Log file
		if(valueData==null)
		{
			syso("Please check the  given field name: '"+ requiredKey +"' is not available in locators file="+fileName );
			reportUtil.log(LogStatus.FAIL, "Please check the  given field name: '"+ requiredKey +"' is not available in locators file="+fileName );
		}		
		return valueData;

	}
	
	public WebElement findElement(String field) {
	//	syso("Finding an element: " + field);
		//		syso("driver value is :" + driver);
		WebElement localElement = null;

		if (field.contains("_XPATH")) {
			localElement = driver.findElement(By.xpath(getLocatorValue(field)));
		} else if (field.contains("_ID")) {
			localElement = driver.findElement(By.id(getLocatorValue(field)));
		} else if (field.contains("_NAME")) {
			localElement = driver.findElement(By.name(getLocatorValue(field)));
		} else {
			log(LogStatus.FAIL,"Locator "+field+" syntax is not correct. Please enter a valid Locator");
		}
		return localElement;
	}
	
	public void quitDriver() {
		driver.quit();
	}
	
	
}   
