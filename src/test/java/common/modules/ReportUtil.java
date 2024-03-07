package common.modules;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class ReportUtil {

	public CommonLibrary commonlib;
	public ExtentReports extent;
	public ExtentTest  test;
	public String reportFolderLocation;
	public String reportFilePath;
	private static Logger Log=LogManager.getLogger(JavaUtil.class.getName());


	public ReportUtil(CommonLibrary commonlib) {
		this.commonlib=commonlib;
	}

	/*
	========================================================================================================================
	 Function Name    : getExtent
	 Description      : To get the ExtentReport object for reporting
	 Arguments        : NA
	 Return value     : Returns ExtentReports object 
	 Example		  : getExtent()					
	============================================================================================================================
	 */
	public ExtentReports getExtent() {
		return extent;
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
		this.extent = extent;
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
		return test;
	}

	/*
	 ============================================================================================================================
	 Function Name    : setTest
	 Description      : To initialize logger for specific class
	 Arguments        : testCaseName
	 Return value     : NA
	 Example		  : setTest("Test_Login")					
	============================================================================================================================
	 */
	public void setTest(String testCaseName) {
		this.test = startTest(testCaseName);
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

		String folderLocation = null;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(Calendar.getInstance().getTime());
		System.out.println("Timestamp:********"+timeStamp);
		String folderName = "Report-" + timeStamp+commonlib.getRandomNumber(4);
		folderLocation = System.getProperty("user.dir") + "/"+"reports" + "/"+folderName;
		File dir = new File(folderLocation);

		// attempt to create the Report directory here
		boolean successful = dir.mkdir();

		if (successful) {
			// creating the directory succeeded
			System.out.println("directory was created successfully");
		} else {
			// creating the directory failed
			System.out.println("failed trying to create the directory");
		}
		reportFolderLocation = folderLocation;
		return folderLocation;
	}


	/*
	 ============================================================================================================================
	 Function Name    : createReportFolder
	 Description      : To create a testreport folder appending testcaseid at the end 
	 Arguments        : NA
	 Return value     : Returns folder location in String format
	 Example		  : createReportFolder("3113")					
	============================================================================================================================
	 */
	public String createReportFolder(String testcaseId) {

		String folderLocation = null;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		System.out.println("Timestamp:********"+timeStamp);
		String folderName = "Report-" + timeStamp+commonlib.getRandomNumber(4)+"_"+testcaseId;
		folderLocation = System.getProperty("user.dir") + "/"+"reports" + "/"+folderName;
		File dir = new File(folderLocation);

		// attempt to create the Report directory here
		boolean successful = dir.mkdir();

		if (successful) {
			// creating the directory succeeded
			System.out.println("directory was created successfully");
		} else {
			// creating the directory failed
			System.out.println("failed trying to create the directory");
		}
		reportFolderLocation = folderLocation;
		return folderLocation;
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
		Log.info("extent="+extent);
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$                 " + TestCaseName + "       $$$$$$$$");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		test = extent.startTest(TestCaseName);
//		commonlib.syso("Value of test is " + test);
		return test;
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

		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXX             -E---N---D-             XXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		if(extent!=null)
		{
			extent.flush();
			extent.endTest(test);
		}

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
		if(extent!=null)
		{
			extent.flush();
		}
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
		if(extent!=null)
		{
			extent.flush();
			extent.endTest(test);
		}
	}

	/*
	 ============================================================================================================================
	 Function Name    : closeExtent
	 Description      : To end the extent test report
	 Arguments        : NA
	 Return value     : NA
	 Example		  : closeExtent()					
	============================================================================================================================
	 */
	public void closeExtent() {
		if(extent!=null)
		{
			extent.close();
		}
	}

	/*
	 ============================================================================================================================
	 Function Name    : initializeReports
	 Description      : To initialize extent report object
	 Arguments        : reportName
	 Return value     : ExtentReport object
	 Example		  : initializeReports("testReport")				
	============================================================================================================================
	 */
	public ExtentReports initializeReports(String reportName) {

		createReportFolder();
		reportFilePath = reportFolderLocation +"/"+ reportName + ".html";
		commonlib.syso("The FilePath for the Reports is: " + reportFilePath);
		ExtentReports extentLocal = new ExtentReports(reportFilePath, true);
		ClassLoader classLoader = this.getClass().getClassLoader();
		commonlib.syso(classLoader.getResource("config.xml").getFile());
		File file = new File("./src/main/resources/config.xml");
		extentLocal.loadConfig(file);
		setExtent(extentLocal);
		return extentLocal;
	}

	/*
	 ============================================================================================================================
	 Function Name    : initializeReports
	 Description      : To initialize extent report object with testcaseid and reportname
	 Arguments        : testcaseId, reportName
	 Return value     : ExtentReport object
	 Example		  : initializeReports("1313","testReport")					
	============================================================================================================================
	 */
	public ExtentReports initializeReports(String testcaseId, String reportName) {

		createReportFolder(testcaseId);
		reportFilePath = reportFolderLocation +"/"+ reportName + ".html";
//		commonlib.syso("The FilePath for the Reports is: " + reportFilePath);
		ExtentReports extentLocal = new ExtentReports(reportFilePath, true);
		ClassLoader classLoader = this.getClass().getClassLoader();
//		commonlib.syso(classLoader.getResource("config.xml").getFile());
		File file = new File("./src/main/resources/config.xml");
		extentLocal.loadConfig(file);
		setExtent(extentLocal);
		return extentLocal;
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
		extent.addSystemInfo("Build Number", "<B>" + buildNumber + "</B>");
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
	@SuppressWarnings("static-access")
	public void log(LogStatus LogStatus, String details) {
		//		syso(details);

		if(details.contains("_XPATH")) // GXIC -Team updated for more clear msg by removing _XPath in reports
		{
			details= details.replace("_XPATH", "");
		}

		test.log(LogStatus, details);
		//syso(details);

		if (LogStatus.equals(LogStatus.PASS) || LogStatus.equals(LogStatus.INFO)) {
			if(details.contains("Snapshot below"))
			{
				Log.info("Snapshot Captured here for the step");
			}
			else
			{
				Log.info(details);
			}
		}

		if (LogStatus.equals(LogStatus.FAIL) || LogStatus.equals(LogStatus.ERROR)) {
			Log.error(details);
			commonlib.getScreenshot();//GXIC Team updated this as if it fails , it takes screen shot 
			Assert.fail(details);

		}
		if (LogStatus.equals(LogStatus.WARNING)) {
			Log.warn(details);

		}
		if (LogStatus.equals(LogStatus.FATAL)) {
			Log.fatal(details);

		}
		if (LogStatus.equals(LogStatus.SKIP) || LogStatus.equals(LogStatus.UNKNOWN)) {
			Log.debug(details);
		}

	}

}
