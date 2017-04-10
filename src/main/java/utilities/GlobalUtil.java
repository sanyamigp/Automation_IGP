package utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * This class will get date and time and it will rename the file with date and time
 *
 */
public class GlobalUtil {
	private static TestData testData;
	private static TestResults testResult;
	private static TestConfig testConfig = new TestConfig();
	private static CommonSettings commonSettings = new CommonSettings();
	private static int totalSuites = 0;
	private static boolean suitesRunStarted = false;
	private static int lastRunId = 0;
	private static Exception testException;
	private static String currentBrowser;
	private static String currentSuiteName;
	private static String currentUserEmail;
	private static String currentUserType;
	static  String currentUserFirstName;
	static String currentUserLastName;
	public static String currentUserFullName;
	public static Map<String, String> propertyCurrentRecord = new HashMap<>();
	public static HashMap<String, String> propertyDeletedRecord = new HashMap<>();
	public static HashMap<String, String> updatesScheduleRecord = new HashMap<>();
	public static HashMap<String, String> propertyRestoredRecord = new HashMap<>();
	public static ArrayList<String> listOfClients = new ArrayList<>();
	public static ArrayList<String> listOfProperties = new ArrayList<>();
	public static HashMap<String, String> client = new HashMap<>();
	static String clientFullName = "FullName";
	static String clientEmail = "Email";

	// For HashMap
	public static final String PROPERTYADDRESSKEY = "Address";
	public static final String PROPERTYDATETIMEKEY = "DateTime";
	public static final String PROPERTYCLIENTKEY = "Client";
	public static final String PROPERTYAGENTKEY = "Agent";
	public static final String PROPERTYNOTEKEY = "Note";
	

	protected static final HashMap<String, String> popupCurrentData = new HashMap<String, String>();
	
	
	/** 
	 *Get data and time stamp
	 */
	public static String getDateTime() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateOfExecution = dateFormat.format(date);
		return dateOfExecution;
	}
	/** 
	 *Update filename with time stamp
	 */
	public static void renameFile() {


		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		String timeStamp = dateFormat.format(date);

		try {

			File oldFile = new File(System.getProperty("user.dir") + ConfigReader.getValue("testResultExcelPath"));
		
			String newFilePath = oldFile.getAbsolutePath().replace(oldFile.getName(), "") + "\\ReportHistory\\"
					+ timeStamp + "-TestResult.xls";

			File newFile = new File(newFilePath);

			FileUtils.copyFile(oldFile, newFile);
			LogUtil.infoLog(GlobalUtil.class, "History File created successfully");

		} catch (IOException e) {
			LogUtil.errorLog(GlobalUtil.class, "Exception caught", e);
		}
	}
	//=======================
	/**
	 * @return
	 */
	public static TestResults getTestResult() {
		return testResult;
	}
	/**
	 * @param testResult
	 */
	public static void setTestResult(TestResults testResult) {
		GlobalUtil.testResult = testResult;
	}
	/**
	 * @return
	 */
	public static TestData getTestData() {
		return testData;
	}
	/**
	 * @param testData
	 */
	public static void setTestData(TestData testData) {
		GlobalUtil.testData = testData;
	}
	
	public static TestConfig getTestConfig() {
		return testConfig;
	}
	/**
	 * @param testConfig
	 */
	public static void setTestConfig(TestConfig testConfig) {
		GlobalUtil.testConfig = testConfig;
	}
	/**
	 * @return
	 */
	public static CommonSettings getCommonSettings() {
		return commonSettings;
	}
	/**
	 * @param commonSettings
	 */
	public static void setCommonSettings(CommonSettings commonSettings) {
		GlobalUtil.commonSettings = commonSettings;
	}
	/**
	 * @return
	 */
	public static int getTotalSuites() {
		return totalSuites;
	}
	/**
	 * @param totalSuites
	 */
	public static void setTotalSuites(int totalSuites) {
		GlobalUtil.totalSuites = totalSuites;
	}
	/**
	 * @return
	 */
	public static boolean isSuitesRunStarted() {
		return suitesRunStarted;
	}
	/**
	 * @param suitesRunStarted
	 */
	public static void setSuitesRunStarted(boolean suitesRunStarted) {
		GlobalUtil.suitesRunStarted = suitesRunStarted;
	}
	/**
	 * @return
	 */
	public static int getLastRunId() {
		return lastRunId;
	}
	/**
	 * @param lastRunId
	 */
	public static void setLastRunId(int lastRunId) {
		GlobalUtil.lastRunId = lastRunId;
	}
	/**
	 * @return
	 */
	public static Exception getTestException() {
		return testException;
	}
	/**
	 * @param testException
	 */
	public static void setTestException(Exception testException) {
		GlobalUtil.testException = testException;
	}
	/**
	 * @return the currentBrowser
	 */
	public static String getCurrentBrowser() {
		return currentBrowser;
	}
	/**
	 * @param currentBrowser the currentBrowser to set
	 */
	public static void setCurrentBrowser(String currentBrowser) {
		GlobalUtil.currentBrowser = currentBrowser;
	}
	/**
	 * @return the currentSuiteName
	 */
	public static String getCurrentSuiteName() {
		return currentSuiteName;
	}
	/**
	 * @param currentSuiteName the currentSuiteName to set
	 */
	public static void setCurrentSuiteName(String currentSuiteName) {
		GlobalUtil.currentSuiteName = currentSuiteName;
	}
	/**
	 * @return the currentUserEmail
	 */
	public static String getCurrentUserEmail() {
		return currentUserEmail;
	}
	/**
	 * @param currentUserEmail the currentUserEmail to set
	 */
	public static void setCurrentUserEmail(String currentUserEmail) {
		GlobalUtil.currentUserEmail = currentUserEmail;
	}
	/**
	 * @return the currentUserType
	 */
	public static String getCurrentUserType() {
		return currentUserType;
	}
	/**
	 * @param currentUserType the currentUserType to set
	 */
	public static void setCurrentUserType(String currentUserType) {
		GlobalUtil.currentUserType = currentUserType;
	}

}
