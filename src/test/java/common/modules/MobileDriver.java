package common.modules;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.cli.Commandline.Argument;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;


public class MobileDriver {

	public CommonLibrary commonLib;
	public static final String configFileName = "config";
	public static final String locatorFileName = "LocatorFileName";
	public static final String ReportFileName = "Mobile";
	public static final String testcasePassResult="Passed";
	public static final String testcaseFailedResult="Failed";
	public SoftAssert softAssert;
	static Map<Integer, List<String>> summaryResult = new HashMap<>();
	static int rowcount = 0;
	AppiumDriverLocalService service;

	
	public MobileDriver() {

		commonLib = new CommonLibrary();
		
	}

	@BeforeSuite
	public void startExtent() throws Exception {

		commonLib.setConfigurationFile(configFileName);
	//	commonLib.verifyPresenceofSummarySheet(commonLib.getConfigValue("Summary_WorkbookName"), commonLib.getConfigValue("Summary_SheetName"));
	
	}

	@BeforeMethod
	public void setup(Method name) throws Exception {

		commonLib.setConfigurationFile(configFileName);
		commonLib.initializeReports(name.getName(),ReportFileName+"_"+name.getName());
		commonLib.setLocatorFileName(locatorFileName);

		service = new AppiumServiceBuilder()
		       .withIPAddress("127.0.0.1")
		       .usingPort(4723)
		       .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
               .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
		       .withArgument(GeneralServerFlag.USE_PLUGINS, "element-wait")
		       .build();		
		service.start();
		
	}
	
	@AfterMethod
	public void CloseSetupAfterMethod(ITestResult result) throws Exception {
		commonLib.endTest();
//		commonLib.quitDriver();


	}


	@AfterSuite(alwaysRun=true)
	public void Close() throws Exception {
		service.stop();
//		commonLib.quitDriver();
	}
}
