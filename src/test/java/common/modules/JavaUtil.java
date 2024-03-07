package common.modules;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class JavaUtil {
	
	CommonLibrary commonlib;
	
	private static Logger Log=LogManager.getLogger(JavaUtil.class.getName());;
	
	public JavaUtil(CommonLibrary commonlib) {
		this.commonlib=commonlib;
	}
	

	/*
	 ============================================================================================================================
	 Function Name    : flattenStringList
	 Description      : To get flatten String list from list object
	 Arguments        : listOfStrings
	 Return value     : Returns flatten String list from list object
	 Example		  : flattenStringList(listOfStrings)					
	============================================================================================================================
	 */
	public List<String> flattenStringList(List<String> listOfStrings) {
		List<String> finalList = new ArrayList<>();

		String stringList = listOfStrings.toString();

		// Replace the [] brackets
		stringList = stringList.replace("[", "");
		stringList = stringList.replace("]", "");
		String[] temp = stringList.split(", ");

		for (String each : temp) {
			finalList.add(each);
		}

		Collections.sort(finalList);

		return finalList;
	}

	/*
	 ============================================================================================================================
	 Function Name    : flattenIntegerList
	 Description      : To get flatten integer list from a list object
	 Arguments        : integerList
	 Return value     : Returns flatten integer list from a list object
	 Example		  : flattenIntegerList(lstInteger)					
	============================================================================================================================
	 */
	public List<Integer> flattenIntegerList(List<Integer> integerList) {
		List<Integer> finalList = new ArrayList<>();

		String stringList = integerList.toString();

		// Replace the [] brackets
		stringList = stringList.replace("[", "");
		stringList = stringList.replace("]", "");
		String[] temp = stringList.split(", ");

		for (String each : temp) {
			// Convert into Integer

			Integer element = convertStringToInteger(each);
			finalList.add(element);
		}

		Collections.sort(finalList);

		return finalList;
	}	
	
	/*
	 ============================================================================================================================
	 Function Name    : convertStringToInteger
	 Description      : To convert String to an integer value
	 Arguments        : elementToConvert
	 Return value     : Returns converted integer value
	 Example		  : convertStringToInteger("31313")					
	============================================================================================================================
	 */
	public int convertStringToInteger(String elementToConvert) {
		int localVariable = Integer.valueOf(elementToConvert);
		return localVariable;
	}	

	/*
	 ============================================================================================================================
	 Function Name    : getCurrentTime
	 Description      : To get current date and time in yyyyMMdd_HHmmss format
	 Arguments        : NA
	 Return value     : Returns Current date and time
	 Example		  : getCurrentTime()					
	============================================================================================================================
	 */
	public String getCurrentTime(){
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}
	
	/*
	 ============================================================================================================================
	 Function Name    : getRandomMobile
	 Description      : To get random String from a given alphabets
	 Arguments        : alphabets
	 Return value     : Returns random String from given alphabets
	 Example		  : getRandomNumber("test")					
	============================================================================================================================
	 */
	public String getRandomString(String alphabets) {

		char[] chars = alphabets.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;

	}

	/*
	 ============================================================================================================================
	 Function Name    : getRandomMobile
	 Description      : To get random mobile number
	 Arguments        : NA
	 Return value     : Returns String random mobile number
	 Example		  : getRandomNumber()					
	============================================================================================================================
	 */
	public String getRandomMobile() 
	{
		String mobileNo = getRandomNumber(12);
		Log.info("initial mobile no::::" + mobileNo);
		String firstDigit = mobileNo.substring(0, 1);
		if (firstDigit.matches("0")) {
			mobileNo = mobileNo.replaceFirst(".{1}", "5");

		}
		Log.info("final mobile no::::" + mobileNo);
		return mobileNo;
	}

	/*
	 ============================================================================================================================
	 Function Name    : getRandomNumber
	 Description      : To get random number of specific digit 
	 Arguments        : digCount
	 Return value     : Returns String random number
	 Example		  : getRandomNumber(4)					
	============================================================================================================================
	 */
	public String getRandomNumber(int digCount) {
		Random rnd = new Random();
		String finalNumber = null;
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		finalNumber = sb.toString();
		String firstDigit = finalNumber.substring(0, 1);
		if (firstDigit.matches("0")) {
			finalNumber = sb.toString().replaceFirst(".{1}", "5");

		}
		return finalNumber;
	}

	/*
	 ============================================================================================================================
	 Function Name    : randomInteger
	 Description      : To get random Integer based on min and max value 
	 Arguments        : min, max
	 Return value     : Returns random integer value
	 Example		  : randomInteger(20,30)					
	============================================================================================================================
	 */
	public int randomInteger(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/*
	 ============================================================================================================================
	 Function Name    : threadSleep
	 Description      : This allow execution to halt for specied time
	 Arguments        : timeoutTime, reason
	 Return value     : NA
	 Example		  : threadSleep(2000,"due to wait not working")					
	============================================================================================================================
	 */
	public void threadSleep(long timeoutTime, String reason) throws InterruptedException {
		System.err.println(reason);
		Thread.sleep(timeoutTime);
	}
	
}
