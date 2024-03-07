package common.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.LogStatus;


/**
 * @author sanjayd7
 *
 */
public class ExcelJsonUtil 
{
	public CommonLibrary commonlib;
	public JavaUtil javaUtil;
	private String workSheet;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private java.sql.Connection conn;
	private String tCaseName;
	final String DELIMITER = "=";
	private JSONArray baseJsonArray;

	private static Logger Log=LogManager.getLogger(JavaUtil.class.getName());;

	public ExcelJsonUtil(CommonLibrary commonlib,JavaUtil util) {

		// TODO Auto-generated constructor stub
		this.commonlib=commonlib;
		this.javaUtil=util;

	}

	/*
	 ============================================================================================================================
	 Function Name    : setJsonObject
	 Description      : To create instance of JSONObject
	 Arguments        : NA
	 Return value     : NA
	 Example		  : setJsonObject()					
	============================================================================================================================
	 */
	public void setJsonObject() {
		jsonObject = new JSONObject();  
	}

	/*
	 ============================================================================================================================
	 Function Name    : getJsonObject
	 Description      : To get the current jsonobject
	 Arguments        : NA
	 Return value     : Returns the current JSONObject
	 Example		  : getJsonObject()					
	============================================================================================================================
	 */
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	/*
	 ============================================================================================================================
	 Function Name    : setWorkSheet
	 Description      : To initialize worksheet object
	 Arguments        : worksheet
	 Return value     : NA 
	 Example		  : setWorkSheet("test.xls")					
	============================================================================================================================
	 */
	public void setWorkSheet(String workSheet) {
		this.workSheet = System.getProperty("user.dir") +"/" + commonlib.getGlobalConfigurationValue("ExcelDataFolder") +"/"+workSheet;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getWorkSheet
	 Description      : To get the current worksheet
	 Arguments        : NA
	 Return value     : Returns String worksheet object
	 Example		  : getWorkSheet()					
	============================================================================================================================
	 */
	public String getWorkSheet() {
		return workSheet;
	}

	/*
	 ============================================================================================================================
	 Function Name    : setJsonArray
	 Description      : To initialize jsonArray object
	 Arguments        : jsonArrayFromDriver
	 Return value     : NA
	 Example		  : setJsonArray(JsonArray)					
	============================================================================================================================
	 */
	public void setJsonArray(JSONArray jsonArrayFromDriver){
		this.jsonArray = jsonArrayFromDriver;
	}

	/*
	 ============================================================================================================================
	 Function Name    : setCurrentTestCaseName
	 Description      : To set the current testcase name
	 Arguments        : testCaseName
	 Return value     : NA
	 Example		  : setCurrentTestCaseName("Testcase")					
	============================================================================================================================
	 */
	public void setCurrentTestCaseName(String testCaseName){
		tCaseName = testCaseName;
	}

	@Deprecated
	public String getExcelData(String columnName, int rowNumber, String sheetName, String urlSheet)
			throws FilloException {
		// commonlib.syso("Accessing "+sheetName +" (sheetName) located at "+urlSheet);
		String data = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(urlSheet);
		String strQuery = "Select * from " + sheetName + " where SNO=" + rowNumber;
		// commonlib.syso("Query being executed is : " + strQuery);
		Recordset recordset = connection.executeQuery(strQuery);
		while (recordset.next()) {
			data = recordset.getField(columnName);
			commonlib.syso("Output of Query is : " + data);
		}
		recordset.close();
		connection.close();
		return data;
	}

	/*
	 ============================================================================================================================
	 Function Name    : executeCustomizedQuery
	 Description      : To get data from the excel in hashmap object for a given query
	 Arguments        : sheetName,query
	 Return value     : Returns data from the excel in the hashmap object 
	 Example		  : executeCustomizedQuery("Login","Select * from login"); 					
	============================================================================================================================
	 */
	public HashMap<String, String> executeCustomizedQuery(String sheetName, String query) throws FilloException {
		System.out.println(query);
		HashMap<String, String> result = new HashMap<String, String>();

		commonlib.syso("Accessing " + sheetName + " (sheetName) located at " + getWorkSheet());
		commonlib.syso("Getting Count for : " + getWorkSheet());

		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(getWorkSheet());
		String strQuery = query;
		Recordset recordset = connection.executeQuery(strQuery);
		while (recordset.next()) {
			ArrayList<String> row = recordset.getFieldNames();
			commonlib.syso("Output of Query is : " + row);
			if (!row.isEmpty()) {
				for (int i = 0; i < row.size(); i++) {

					result.put(row.get(i), recordset.getField(row.get(i)));
				}

			}

		}
		commonlib.syso("Data required for testcase is : " + result);
		recordset.close();
		connection.close();

		return result;

	}

	@Deprecated
	public String getSerialNumber(int counter, String sheetName, String urlSheet) throws FilloException {
		ArrayList<?> serialNo = getSerialNo("SNO", sheetName, urlSheet);
		serialNo.toArray();
		String rowNum = (String) serialNo.get(counter);
		return rowNum;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getSerialNumber
	 Description      : To get data from SNO column for a specific row
	 Arguments        : counter, sheetName
	 Return value     : Returns String data from the SNO column for a specific row
	 Example		  : getSerialNumber(3,"Test"); 					
	============================================================================================================================
	 */
	public String getSerialNumber(int counter, String sheetName){
		String rowNum = null;
		try
		{

			ArrayList<?> serialNo = getSerialNo("SNO", sheetName);
			serialNo.toArray();
			rowNum = (String) serialNo.get(counter);
		}
		catch(Exception e){
			Log.error("Unable to getSerailNumber from Excel file"+ e);
		}
		return rowNum;
	}

	@Deprecated
	public ArrayList<String> getSerialNo(String colName, String sheetName, String urlSheet) throws FilloException {
		commonlib.syso("Accessing " + sheetName + " (sheetName) located at " + urlSheet);
		String data = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(urlSheet);
		String strQuery = "Select * from " + sheetName;
		commonlib.syso("Query being executed is: " + strQuery);
		ArrayList<String> arrSerialNoData = new ArrayList<String>();
		Recordset recordset = connection.executeQuery(strQuery);
		while (recordset.next()) {
			data = recordset.getField(colName);
			arrSerialNoData.add(data);
			commonlib.syso("Output of Query is: " + data);
		}
		recordset.close();
		connection.close();
		return arrSerialNoData;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getSerialNo
	 Description      : To get all data of a specific column 
	 Arguments        : colName, sheetName
	 Return value     : Returns data for a specific column in a list
	 Example		  : getSerialNumber("3","Test"); 					
	============================================================================================================================
	 */
	public ArrayList<String> getSerialNo(String colName, String sheetName) throws FilloException {

		commonlib.syso("Accessing " + sheetName + " (sheetName) located at " + workSheet);
		String data = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(workSheet);
		String strQuery = "Select * from " + sheetName;
		commonlib.syso("Query being executed is: " + strQuery);
		ArrayList<String> arrSerialNoData = new ArrayList<String>();
		Recordset recordset = connection.executeQuery(strQuery);
		while (recordset.next()) {
			data = recordset.getField(colName);
			arrSerialNoData.add(data);
			commonlib.syso("Output of Query is: " + data);
		}
		recordset.close();
		connection.close();
		return arrSerialNoData;
	}

	/*
	 ============================================================================================================================
	 Function Name    : appendJson
	 Description      : To add key value to the JSON array
	 Arguments        : keyValuePair
	 Return value     : NA
	 Example		  : appendJson("{usename:"test"}"); 					
	============================================================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void appendJson(String keyValuePair){
		jsonArray.add(keyValuePair);
		jsonObject.put(tCaseName, jsonArray);
	}	

	/*
	 ============================================================================================================================
	 Function Name    : setTestCase
	 Description      : To set test case name with number of steps
	 Arguments        : testCaseName, numberOfTestSteps
	 Return value     : NA
	 Example		  : setTestCase("test", 3); 					
	============================================================================================================================
	 */
	public void setTestCase(String testCaseName, int numberOfTestSteps ) throws FilloException
	{	
		setCurrentTestCaseName(testCaseName);
		String rowNumber = getExcelSNOFromTestData(testCaseName, "ScriptName", "Mapping");
		String teamProject = getExcelData("teamProject", javaUtil.convertStringToInteger(rowNumber), "Mapping");
		String testPlanNumber = getExcelData("testPlanNumber", javaUtil.convertStringToInteger(rowNumber), "Mapping");
		String testSuiteNumber = getExcelData("testSuiteNumber", javaUtil.convertStringToInteger(rowNumber), "Mapping");
		String testCaseNumber = getExcelData("testCaseNumber", javaUtil.convertStringToInteger(rowNumber), "Mapping");
		appendJson("teamProject"+DELIMITER+teamProject);
		appendJson("testPlanNumber"+DELIMITER+testPlanNumber);
		appendJson("testSuiteNumber"+DELIMITER+testSuiteNumber);
		appendJson("testCaseNumber"+DELIMITER+testCaseNumber);
		appendJson("numberOfSteps"+DELIMITER+Integer.toString(numberOfTestSteps));
	}

	/*
	 ============================================================================================================================
	 Function Name    : getExcelSNOFromTestData
	 Description      : To get SNO from testdata sheet
	 Arguments        : testData, columnName, sheetName
	 Return value     : Returns SNO from the testdata
	 Example		  : getExcelSNOFromTestData("columnname, test", "login"); 					
	============================================================================================================================
	 */
	public String getExcelSNOFromTestData(String testData, String columnName, String sheetName) throws FilloException {

		// commonlib.syso("Accessing "+sheetName +" (sheetName) located at "+urlSheet);

		String data = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(workSheet);
		String strQuery = "Select SNO from " + sheetName + " where "+columnName+" ='" + testData+"'";
		commonlib.syso("Query being executed is : " + strQuery);
		Recordset recordset = connection.executeQuery(strQuery);
		while (recordset.next()) {
			data = recordset.getField("SNO");
			commonlib.syso("Output of Query is : " + data);
		}
		recordset.close();
		connection.close();
		if(data==null){
			System.err.println("There is no test Data present in this sheet");
		}
		return data;

	}

	/*
	 ============================================================================================================================
	 Function Name    : writeJsonToFile
	 Description      : To write json data to a file
	 Arguments        : applicationName
	 Return value     : NA
	 Example		  : writeJsonToFile("Test"); 					
	============================================================================================================================
	 */
	public void writeJsonToFile(String applicationName) throws IOException {
		//		Attach the final report

		appendJson("attachmentName"+DELIMITER);

		JSONObject obj = jsonObject;
		String fileName = System.getProperty("user.dir") +"/"+commonlib.getGlobalConfigurationValue("CSharpResultsDataFolder")+"/"
				+ commonlib.getConfigValue("jsonFileName")+"-"+applicationName+".json";
		try (FileWriter file = new FileWriter(fileName)) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}

	/*
	 ============================================================================================================================
	 Function Name    : setBaseJsonArray
	 Description      : To set the base Json array value
	 Arguments        : NA
	 Return value     : NA
	 Example		  : setBaseJsonArray(); 					
	============================================================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void setBaseJsonArray(){
		baseJsonArray = new JSONArray();

		//Mention the initial Information

		JsonObject runInfo = new JsonObject();
		runInfo.addProperty("teamProject", "BigDataCloud");
		runInfo.addProperty("testPlanNumber", 12312);
		runInfo.addProperty("testSuiteNumber", 39249);
		runInfo.addProperty("buildNumber", "Build Version");

		baseJsonArray.add(runInfo);
		commonlib.syso(baseJsonArray.toJSONString());

	}

	/*
	 ============================================================================================================================
	 Function Name    : executeQuery
	 Description      : To execute the query to fetch data from excel 
	 Arguments        : query
	 Return value     : ResultSet
	 Example		  : executeQuery("select * from loginpage"); 					
	============================================================================================================================
	 */
	public ResultSet executeQuery(String query) throws FilloException {
		ResultSet rs = null;

		Log.error("<U>Execute DB Query:</U> " + query);

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			if (conn != null) {
				Statement st = conn.createStatement();
				try {
					rs = st.executeQuery(query);

					// log(LogStatus.INFO, "Total rows in Resultset are :" +
					// rowCount);
				} catch (NullPointerException err) {
					System.out.println("No Records obtained for this specific query");
					err.printStackTrace();
				}
			}
		} catch (Exception ex) {
			System.out.println("Unable to execute the query");
			ex.printStackTrace();
		}
		return rs;
	}

	/*
	 ============================================================================================================================
	 Function Name    : fetchColumnList
	 Description      : To fetch the column from the testdata sheet 
	 Arguments        : query
	 Return value     : Returns column data in a list
	 Example		  : fetchColumnList("select * from loginpage"); 					
	============================================================================================================================
	 */
	public List<String> fetchColumnList(String query) throws FilloException {
		List<String> resultList = new ArrayList<String>();

		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			if (conn != null) {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					resultList.add(rsmd.getColumnName(i));
				}
				// log(LogStatus.INFO, "Total rows in Resultset are :" +
				// rowCount);

			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return resultList;
	}


	/*
	 ============================================================================================================================
	 Function Name    : executeSelectQuery
	 Description      : To execute select query and fetch data
	 Arguments        : query
	 Return value     : Returns select result in a list
	 Example		  : executeSelectQuery("select * from loginpage"); 					
	============================================================================================================================
	 */
	public List<String> executeSelectQuery(String query) {

		List<String> resultList = new ArrayList<String>();
		int rowCount = 0;

		Log.info("Execute DB Query: " + query);

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			if (conn != null) {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);

				ResultSetMetaData rsmd = rs.getMetaData();
				int colNumber = rsmd.getColumnCount();
				try {
					while (rs.next()) {
						for (int i = 1; i <= colNumber; i++) {
							resultList.add(rs.getString(i));
							rowCount++;
						}
					}
					commonlib.syso("Total rows in Resultset are :" + rowCount);
				} catch (NullPointerException err) {
					System.out.println("No Records obtained for this specific query");
					err.printStackTrace();
				}
			}
		} catch (Exception ex) {
			System.out.println("Unable to execute Query");
			ex.printStackTrace();
		}
		return resultList;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getUrlSheet
	 Description      : To get the testdata sheet path to fetch data
	 Arguments        : worksheet
	 Return value     : Returns testdata file path
	 Example		  : getUrlSheet("Test");			
	============================================================================================================================
	 */
	public String getUrlSheet(String workSheet) {
		return System.getProperty("user.dir") +"/" + commonlib.getGlobalConfigurationValue("ExcelDataFolder") + workSheet;
	}

	public String valueFilename(String elementName) {
		String localFileName = null;
		if (elementName.contains("SN_")) {
			// localFileName= "ServiceNowLocators.properties";
		} else if (elementName.contains("CO_")) {
			localFileName = "ConnectValues.properties";
		} else if (elementName.contains("gcs_")) {
			// localFileName= "GcsLocators.properties";
		} else {
			commonlib.syso("Please specify a FileName for Values");
		}
		return System.getProperty("user.dir")+"/"+commonlib.getGlobalConfigurationValue("ValueFolder") + localFileName;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getValue
	 Description      : To get value of a specified key from the properties file
	 Arguments        : requiredKey
	 Return value     : Returns String value of the specified key
	 Example		  : getValue("Username");			
	============================================================================================================================
	 */
	public String getValue(String requiredKey) {
		String fileName = null;
		fileName = valueFilename(requiredKey);

		// commonlib.syso("Locator Filename is " + fileName);
		// commonlib.syso("Key whose value is being fetched is: " + requiredKey);
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
		// commonlib.syso("Value for the required key "+requiredKey+ " is: "+ valueData);
		return valueData;

	}

	/*
	 ============================================================================================================================
	 Function Name    : getExcelData
	 Description      : To get data from a specific row and column number
	 Arguments        : columnName,rowNumber,sheetName
	 Return value     : Returns String data from a specific row and column number
	 Example		  : getExcelData("Username",2,3,"Testdata");			
	============================================================================================================================
	 */
	public String getExcelData(String columnName, int rowNumber, String sheetName){
		// commonlib.syso("Accessing "+sheetName +" (sheetName) located at "+urlSheet);
		String data = null;
		Fillo fillo = new Fillo();
		try{
			Connection connection = fillo.getConnection(workSheet);
			String strQuery = "Select * from " + sheetName + " where SNO=" + rowNumber;
			// commonlib.syso("Query being executed is : " + strQuery);
			Recordset recordset = connection.executeQuery(strQuery);
			while (recordset.next()) {
				data = recordset.getField(columnName);
				commonlib.syso("Output of Query is : " + data);
			}
			recordset.close();
			connection.close();
		}
		catch(Exception e){
			Log.error("Error occured while fetching the Data from Excel "+e);
		}
		return data;
	}

	@Deprecated
	public void updateSheet(Object updateParameter, String colName, int rowNumber, String sheetName, String urlSheet)
			throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(urlSheet);

		String strQuery = "Update " + sheetName + " Set " + colName + "=" + "'" + updateParameter + "' where SNO="
				+ rowNumber + "";
		connection.executeUpdate(strQuery);
		commonlib.syso("Successfully updated excel sheet with " + updateParameter + " at column: " + colName);
		connection.close();
	}

	/*
	 ============================================================================================================================
	 Function Name    : updateSheet
	 Description      : To update data to the specific row and column number 
	 Arguments        : updateParameter, colName, rowNumber, sheetName
	 Return value     : NA
	 Example		  : updateSheet("Test",2,3,"Testdata");			
	============================================================================================================================
	 */
	public void updateSheet(Object updateParameter, String colName, int rowNumber, String sheetName)
			throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(workSheet);

		String strQuery = "Update " + sheetName + " Set " + colName + "=" + "'" + updateParameter + "' where SNO="
				+ rowNumber + "";
		connection.executeUpdate(strQuery);
		commonlib.syso("Successfully updated excel sheet with " + updateParameter + " at column: " + colName);
		connection.close();
	}

	@Deprecated
	public int getCount(String sheetName, String urlSheet) throws FilloException {
		commonlib.syso("Accessing " + sheetName + " (sheetName) located at " + urlSheet);
		commonlib.syso("Getting Count for : " + urlSheet);
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(urlSheet);
		String strQuery = "Select * from " + sheetName;
		Recordset recordset = connection.executeQuery(strQuery);
		int count = recordset.getCount();
		recordset.close();
		connection.close();
		return count;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getCount
	 Description      : To get total number of rows in excel till where the data exists
	 Arguments        : sheetName
	 Return value     : Returns no of rows from excel 
	 Example		  : getCount("Testdata");			
	============================================================================================================================
	 */
	public int getCount(String sheetName) {

		int count= 0;
		try{
			commonlib.syso("Accessing " + sheetName + " (sheetName) located at " + workSheet);
			commonlib.syso("Getting Count for : " + workSheet);
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(workSheet);
			String strQuery = "Select * from " + sheetName;
			Recordset recordset = connection.executeQuery(strQuery);
			count = recordset.getCount();
			recordset.close();
			connection.close();
		}
		catch(Exception e){
			Log.error("Unable to fetch count from the excel file "+ e);
		}
		return count;
	}

	/*
	 ============================================================================================================================
	 Function Name    : readAllTestCaseData
	 Description      : It reads all test data from excel sheet for a particular Testcase without fetching exact row number for the testcase
	 Arguments        : String fileName , String sheetName , String testCaseId, int columnTestcaseId
	 Return value     : Returns data of a particular testcase in a Map object
	 Example	 	  : Map<Integer, List<String>> Record_Type_value_data=readAllTestData(String fileName , String sheetName , String testCaseId, int columnTestcaseId);

	============================================================================================================================
	 */	

	public synchronized Map<Integer, List<String>> readAllTestCaseData(String fileName , String sheetName , String testCaseId, int columnTestcaseId)
	{
		String p = System.getProperty("user.dir")+"/"+commonlib.getGlobalConfigurationValue("ExcelDataFolder")+"/"+fileName;
		Map<Integer, List<String>> userData = new HashMap<Integer, List<String>>();
		try
		{
			int counter=0;
			XSSFWorkbook workbook = new XSSFWorkbook(p);
			XSSFSheet Sheet = workbook.getSheet(sheetName);		
			int rowno=Sheet.getLastRowNum();
			for(int i=1; i<=rowno; i++)
			{
				XSSFRow Row=Sheet.getRow(i);
				int lastCell=Row.getLastCellNum();
				String tcId=Row.getCell(columnTestcaseId).getStringCellValue();
				List<String> rowData=new ArrayList<>();
				if(tcId.trim().equals(testCaseId.trim()))
				{

					for(int j=0; j<lastCell;j++)
					{ 
						if(Row.getCell(j)!=null)
						{
							switch (Row.getCell(j).getCellType()) {

							case Cell.CELL_TYPE_STRING:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_BLANK:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(Row.getCell(j))) {
									SimpleDateFormat df=new SimpleDateFormat();
									rowData.add(df.format(Row.getCell(j).getDateCellValue()));
								}
								else
								{
									long d=(long)Row.getCell(j).getNumericCellValue();
									String data=String.valueOf(d);
									rowData.add(data);
								}
								break;

							default:
								break;
							}
						}
						else
						{
							rowData.add("");
						}
					}

					userData.put(++counter, rowData);
				}
			}


		} catch (Exception e) {

			e.printStackTrace();
			commonlib.log(LogStatus.FAIL, e.getMessage());
		}
		if(userData.size()>0)
		{

			return userData;

		}
		else
		{
			commonlib.log(LogStatus.FAIL, "No Data found for given test case "+testCaseId);
			return userData;
		}


	}

	/*
	 ============================================================================================================================
	 Function Name    : readAllTestCaseDataWithRowNum
	 Description      : It reads all test data from excel sheet for a particular Testcase along with exact row number from the sheet
	 Arguments        : String fileName , String sheetName , String testCaseId, int columnTestcaseId
	 Return value     : Returns data of a particular testcase in a Map object
	 Example	 	  : Map<Integer, List<String>> Record_Type_value_data=readAllTestData(String fileName , String sheetName , String testCaseId, int columnTestcaseId);

	============================================================================================================================
	 */	
	public synchronized Map<Integer, List<String>> readAllTestCaseDataWithRowNum(String fileName , String sheetName , String testCaseId, int columnTestcaseId)
	{
		String p = System.getProperty("user.dir")+"/"+commonlib.getGlobalConfigurationValue("ExcelDataFolder")+"/"+fileName;
		Map<Integer, List<String>> userData = new HashMap<Integer, List<String>>();
		try
		{
			XSSFWorkbook workbook = new XSSFWorkbook(p);
			XSSFSheet Sheet = workbook.getSheet(sheetName);		
			int rowno=Sheet.getLastRowNum();

			for(int i=1; i<=rowno; i++)
			{
				XSSFRow Row=Sheet.getRow(i);
				int lastCell=Row.getLastCellNum();
				String tcId=Row.getCell(columnTestcaseId).getStringCellValue();
				List<String> rowData=new ArrayList<>();
				if(tcId.trim().equals(testCaseId.trim()))
				{

					for(int j=0; j<lastCell;j++)
					{ 
						if(Row.getCell(j)!=null)
						{
							switch (Row.getCell(j).getCellType()) {

							case Cell.CELL_TYPE_STRING:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_BLANK:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								long d=(long)Row.getCell(j).getNumericCellValue();
								String data=String.valueOf(d);
								rowData.add(data);
								break;
							default:
								break;
							}
						}
						else
						{
							rowData.add("");
						}
					}

					userData.put(i, rowData);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			commonlib.log(LogStatus.FAIL, e.getMessage());
		}
		if(userData.size()>0)
		{

			return userData;

		}
		else
		{
			commonlib.log(LogStatus.FAIL, "No Data found for given test case "+testCaseId);
			return userData;
		}
	}

	/*
	 ============================================================================================================================
	 Function Name    : writeToCell
	 Description      : It writes data to the specific row and column number  
	 Arguments        : fileName , sheetName , rowNum, colNum, Value
	 Return value     : NA
	 Example	 	  : writeToCell("Test.xls" , "testdata", 1, 2, "test");

	============================================================================================================================
	 */	
	public synchronized void writeToCell(String fileName , String sheetName ,int rowNum , int colNum, String Value) throws IOException {

		try {
			String loc = System.getProperty("user.dir")+"\\"+commonlib.getGlobalConfigurationValue("ExcelDataFolder")+"\\"+fileName;
			FileInputStream fis= new FileInputStream(loc);

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet Sheet = workbook.getSheet(sheetName);

			XSSFRow Row = Sheet.getRow(rowNum);
			Cell cell =Row.getCell(colNum);
			if( cell==null){
				cell=Row.createCell(colNum);
			}
			if(cell != null){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(Value);
			}
			fis.close();
			FileOutputStream out = new FileOutputStream(loc);
			out.flush();
			workbook.write(out);
			out.close();
			workbook.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/*
	 ============================================================================================================================
	 Function Name    : read_Test_Data
	 Description      : It is used to read testdata from a given row and column range
	 Arguments        : fileName , sheetName , fromRow , toRow , fromCol , toCol
	 Return value     : returns all data in a  List<String>
	 Example	 	  : read_Test_Data("Test.xls" , "testdata", 1, 2, "test");

	============================================================================================================================
	 */	
	public synchronized List<String> read_Test_Data(String fileName ,  String sheetName ,int fromRow , int toRow , int fromCol , int toCol){
		String p = System.getProperty("user.dir")+"/testdata/"+fileName;
		List<String> userData = new ArrayList<String>();

		try {

			ZipSecureFile.setMinInflateRatio(-1.0d);
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(p));
			XSSFSheet Sheet = workbook.getSheet(sheetName);
			for(int i=fromRow ;i<=toRow;i++){
				XSSFRow Row = Sheet.getRow(i);
				if(Row==null){
					continue;
				}

				for(int k =fromCol;k<=toCol;k++){

					Cell cell = Row.getCell(k);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String CellValue = cell.toString();
					System.out.println(CellValue);
					userData.add(cell.toString());
				}
			}
			workbook.close();
		} catch (IOException e) {

			e.printStackTrace();
			commonlib.log(LogStatus.FAIL, e.getMessage());
		}
		return userData;

	}


	/*
	 ============================================================================================================================
	 Function Name    : getHeaderRow
	 Description      : It gets the header row of the sheet
	 Arguments        : String fileName , String sheetName
	 Return value     : List<String>
	============================================================================================================================
	 */

	public synchronized List<String> getHeaderRow(String fileName , String sheetName) throws Exception
	{
		String p = System.getProperty("user.dir")+"/testdata/"+fileName;
		List<String> rowData=new ArrayList<>();
		try {
			ZipSecureFile.setMinInflateRatio(-1.0d);
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(p));
			XSSFSheet Sheet = workbook.getSheet(sheetName);		
			int cellCount=Sheet.getRow(0).getLastCellNum();

			for (int i=0; i<cellCount; i++)
			{
				rowData.add(Sheet.getRow(0).getCell(i).getStringCellValue());
			}
			workbook.close();

		} catch (IOException e) {

			e.printStackTrace();
			commonlib.log(LogStatus.FAIL, e.getMessage());
		}
		return rowData;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getColumnNumberFromList
	 Description      : It gets the column index from the list
	 Arguments        :List<String> lstData, String columnName
	 Return value     : int column number
	============================================================================================================================
	 */

	public int getColumnNumberFromList(List<String> lstData, String columnName) throws Exception
	{

		int count=0;
		boolean found=false;
		for (int i=0; i<lstData.size(); i++)
		{
			if(lstData.get(i).equals(columnName))
			{
				found=true;
				count=i;
				return count;
			}
		}
		if(!found)
		{

			throw new Exception("Error column "+columnName+" not found");	

		}

		return count;
	}

	/*
	 ============================================================================================================================
	 Function Name    : updateResult
	 Description      : To update testcase result in the summary sheet
	 Arguments        : workbookname, sheetName, testcaseID, Map<Integer, List<String>> testresult
	 Return value     : NA
	============================================================================================================================
	 */
	public synchronized void updateResult(String workbookname, String sheetName, Map<Integer, List<String>> testresult)
			throws IOException {

		String filename = System.getProperty("user.dir") + "/"
				+ commonlib.getGlobalConfigurationValue("ExcelDataFolder") + "/" + workbookname+".xlsx";
		FileInputStream file = new FileInputStream(new File(filename));
		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheet(sheetName);
		// count number of active tows
		int totalRow = sheet.getLastRowNum();
		// count number of active columns in row
		int UpdateResultCol = 4;
		// Column to update for result

		int counter=0;

		for (int rowkey : testresult.keySet()) {

			boolean found=false;
			List<String> data = testresult.get(rowkey);
			String testcaseID=data.get(0);
			String result=data.get(1);

			// Column to update for result
			for (int i = 1; i <= totalRow; i++) {

				if(sheet.getRow(i)!=null)
				{
					XSSFRow r = sheet.getRow(i);
					if(r.getCell(1)!=null)
					{
						String ce = r.getCell(1).getStringCellValue();
						if (ce.trim().equals(testcaseID)) {

							commonlib.syso("Test case ID \""+testcaseID+"\" already exists in the summary sheet");

							if(r.getCell(3)!=null && r.getCell(UpdateResultCol)!=null)
							{
								r.getCell(3).setCellValue(commonlib.add_Minus_Date(0, "dd/MM/yyyy"));
								r.getCell(UpdateResultCol).setCellValue(result);
							}
							else
							{
								r.createCell(3).setCellValue(commonlib.add_Minus_Date(0, "dd/MM/yyyy"));
								r.createCell(UpdateResultCol).setCellValue(result);
							}
							found=true;
							commonlib.syso("Result updated for Test case ID \""+testcaseID+"\"");
							break;
						}
					}

				}
			}
			if(!found)
			{
				counter=counter+1;
				commonlib.syso("Adding Test case ID \""+testcaseID+"\" to the summary sheet as it's not available");
				XSSFRow r = sheet.createRow(totalRow+counter);
				r.createCell(0).setCellValue(totalRow+counter);
				r.createCell(1).setCellValue(testcaseID);
				r.createCell(3).setCellValue(commonlib.add_Minus_Date(0, "dd/MM/yyyy"));
				r.createCell(UpdateResultCol).setCellValue(result);		
				commonlib.syso("Result updated for Test case ID \""+testcaseID+"\"");	

			}

		}
		file.close();
		FileOutputStream outFile = new FileOutputStream(filename);
		outFile.flush();
		workbook.write(outFile);
		outFile.close();
		workbook.close();	
	}

	/*
			 ============================================================================================================================
			 Function Name    : verifyTCPresenceInSummarySheet
			 Description      : To verify presence of TC in the summary sheet, if not it will add to the summary sheet
			 Arguments        : workbookname, sheetName, testcaseID
			 Return value     : NA
			============================================================================================================================
	 */

	public synchronized void verifyPresenceofSummarySheet(String workbookname, String sheetName)
			throws Exception {


		String filename = System.getProperty("user.dir") + "/"
				+ commonlib.getGlobalConfigurationValue("ExcelDataFolder") + "/" + workbookname+".xlsx";
		FileInputStream file = new FileInputStream(new File(filename));
		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheet(sheetName);
		// count number of active tows
		if(sheet.getRow(0)!=null)
		{
			commonlib.syso("Summary sheet "+workbookname+" present in "+commonlib.getGlobalConfigurationValue("ExcelDataFolder")+" folder");
		}
		else
		{
			file.close();
			workbook.close();	
			throw new Exception("Summary sheet "+workbookname+" not present in "+commonlib.getGlobalConfigurationValue("ExcelDataFolder")+" folder");
		}

		file.close();
		workbook.close();		

	}
}

