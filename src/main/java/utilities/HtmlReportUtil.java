/**
 * @author SUKHJINDER
 *
 * 	About 
 * =====================
 * This class is responsible for creating HTML report for test run. It will create two different file.
 * One of them is overwritten every time when user run the tests.
 * Second keep the history.  
 */

/* We can use the following HTML code to modify the logs.O
 * <span class='success label'>Success</span> 
<span class='fail label'>Fail</span> 
<span class='warning label'>Warning</span> 
<span class='info label'>Info</span> 
<span class='skip label'>Skip</span>
 * */

package utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author TestLink
 *
 */
public class HtmlReportUtil {
	private static ExtentReports extentNoHistory = null;
	private static ExtentReports extentPreserverHistory = null;
	private static ExtentTest test = null;
	private static ExtentTest testHist = null;
	private static String warninglbl = "<span class='warning label'>Error</span><span class='fatal'><b>";
	private static String span = "</span>";
	private static String span1 = "</b></span>";
	private static boolean  reportInt=false;
	private static final String ERROR_MESSAGE = "Report is not initialized! Please use init Method before calling method";

	private HtmlReportUtil() {

	}

	// Initialize reporter
	private static synchronized void init() {
		
		if (extentNoHistory == null) {
			extentNoHistory = new ExtentReports(ConfigReader.getValue("HtmlReport") + "\\TestReport.html", true,
					DisplayOrder.NEWEST_FIRST);
			extentNoHistory.loadConfig(new File(ConfigReader.getValue("HtmlReportConfigFile")));
		}
		if (extentPreserverHistory == null) {
			extentPreserverHistory = new ExtentReports(System.getProperty("user.dir")+"\\TestReportHistory.html",
					false, DisplayOrder.NEWEST_FIRST);
			extentPreserverHistory.loadConfig(new File(ConfigReader.getValue("HtmlReportConfigFile")));
		}
		reportInt=true;
	}// End init()

	// This function is used to start report when a new test is started.
	/**
	 * @param testName
	 * @param testInfo
	 * @param category
	 */
	public static synchronized void startTest(String testName, String testInfo, String category) {
		//Test will only start if Report is initialized
		if(reportInt)
		{test = extentNoHistory.startTest(testName, testInfo);
		testHist = extentPreserverHistory.startTest(testName, testInfo);
		test.assignCategory(category);
		testHist.assignCategory(category);
		}
		else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":startTest ");
		}
	}

	public static synchronized void startHtmlReport() {
		init();

	}
 
	/**
	 * <H1>Test PASSED AND ENDED</h1>
	 * <p> Use at the end of test based on pass. 
	 * Best place to use in Listeners</p>
	 * @param status
	 * @param stepName
	 */

	public static synchronized void testPass(String stepName) {

		if(reportInt){
			String html = "<span class='success label'>PASS</span> <span style='color:green;margin-left: 10px'>"
					+ stepName + span;
			test.log(LogStatus.PASS, html);
			testHist.log(LogStatus.PASS, html);

			// End the current test
			extentNoHistory.endTest(test);
			extentPreserverHistory.endTest(testHist);

			// Write the information of the current test in HTML file.
			extentNoHistory.flush();
			extentPreserverHistory.flush();
			
			}else{
				LogUtil.infoLog("StartTest", ERROR_MESSAGE +":testPass");
			}
		
		
	}// End endReport()

	/**
	 * <H1>Test FAILED AND ENDED</h1>
	 * <p> Use at the end of test based on pass. 
	 * Best place to use in Listeners</p>
	 * @param status
	 * @param stepName
	 */
	public static synchronized void testFail(String stepName) {
		if(reportInt){

			String html = "<span class='fail label'>Fail</span> <span style='color:red;margin-left: 10px'>" + stepName
					+ span;
			test.log(LogStatus.FAIL, html);
			testHist.log(LogStatus.FAIL, html);

			// End the current test
			extentNoHistory.endTest(test);
			extentPreserverHistory.endTest(testHist);

			// Write the information of the current test in HTML file.
			extentNoHistory.flush();
			extentPreserverHistory.flush();
			
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":testFail");
		}
		
	}// End endReport()

	public static synchronized void endReport() {
		
		// Write the information of the current test in HTML file.
		if(reportInt){
		extentNoHistory.flush();
		extentPreserverHistory.flush();
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":endReport");
		}
	}// End endReport()

	/**
	 * @param stepName
	 */
	public static synchronized void stepPass(String stepName) {
		if(reportInt){
		String html = "<span class='success label'>Success</span> <span style='color:green;margin-left: 10px'>"
				+ stepName + span;
		test.log(LogStatus.PASS, html);
		testHist.log(LogStatus.PASS, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepPass");
		}
		
	}

	/**
	 * @param stepName
	 */
	public static synchronized void stepFail(String stepName) {
		if(reportInt){
		String html = "<span class='fail label'>Fail</span> <span style='color:red;margin-left: 10px'>" + stepName
				+ span;
		test.log(LogStatus.INFO, html);
		testHist.log(LogStatus.INFO, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepFail");
		}

	}

	/**
	 * @param stepName
	 */
	public static synchronized void stepInfo(String stepName) {
		if(reportInt){
		test.log(LogStatus.INFO, stepName);
		testHist.log(LogStatus.INFO, stepName);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepInfo");
		}
	}
	public static synchronized void stepInfoIdented(String stepName) {
		if(reportInt){
			
			String html = " <span style='margin-left: 20px'>" + stepName
					+ span;
			test.log(LogStatus.INFO, html);
			testHist.log(LogStatus.INFO, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepInfo");
		}
	}
	
	
	
	public static synchronized void stepInfo(String stepName,String stepDetail) {
		if(reportInt){
		test.log(LogStatus.INFO, stepName,stepDetail);
		testHist.log(LogStatus.INFO, stepName,stepDetail);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepInfo");
		}
	}

	/**
	 * @param stepName
	 */
	public static synchronized void stepInfoWithError(String stepName) {
		if(reportInt){
		String html = warninglbl + stepName + span1;
		test.log(LogStatus.INFO, html);
		testHist.log(LogStatus.INFO, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepInfoWithError");
		}
		
	}

	/**
	 * @param stepName
	 */
	public static synchronized void stepError(String stepName) {
		if(reportInt){
		String html = warninglbl + stepName + span1;
		test.log(LogStatus.ERROR, html);
		testHist.log(LogStatus.ERROR, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepError");
		}
	}

	/**
	 * @param stepName
	 */
	public static synchronized void stepWarning(String stepName) {
		if(reportInt){
		String html = warninglbl + stepName + span1;
		test.log(LogStatus.WARNING, html);
		testHist.log(LogStatus.WARNING, html);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepWarning");
		}
	}

	/**
	 * @param stepName
	 * @param t
	 */
	public static synchronized void stepError(String stepName, Throwable t) {
		if(reportInt){
		String html = "<span class='warning label'>Error</span><span class='fatal'>" + stepName + span;
		test.log(LogStatus.ERROR, html, t);
		testHist.log(LogStatus.ERROR, html, t);
		}
		else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":stepError");
		}
	}

	/**
	 * @param stepName
	 * @param t
	 */
	public static synchronized void testSkipped(String stepName, Throwable t) {
		if(reportInt){
		
		String html = "<span style='color:#e59127'>" + stepName + span;
		test.log(LogStatus.SKIP, html, t);
		testHist.log(LogStatus.SKIP, html, t);

		extentNoHistory.endTest(test);
		extentPreserverHistory.endTest(testHist);

		extentNoHistory.flush();
		extentPreserverHistory.flush();
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":testSkipped");
		}
		
	}

	// Attach screenshot to the report
	/**
	 * @param imagePath
	 */
	public static synchronized void attachScreenshot(String imagePath) {
		if(reportInt){
		String image = test.addScreenCapture(imagePath);
		test.log(LogStatus.FAIL, "ScreenShot : " + GlobalUtil.getTestException(), image);
		testHist.log(LogStatus.FAIL, "ScreenShot :" + GlobalUtil.getTestException(), image);
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":attachScreenshot");
		}
	}

	// End Report
	/**
	 * 
	 */
	public static synchronized void tearDownReport() {
		
		if(reportInt){
			extentNoHistory.flush();
			extentPreserverHistory.flush();
			extentNoHistory.close();
			extentNoHistory = null;
			extentPreserverHistory.close();
			extentPreserverHistory = null;
			
		}else{
			LogUtil.infoLog("StartTest", ERROR_MESSAGE +":tearDownReport");
		}
	}

	/**
	 * @param imagePath
	 * @param flag
	 */
	public static void attachScreenshot(String imagePath, Boolean flag) {

		String image = test.addScreenCapture(imagePath);
		test.getTest().getName();

		if (flag) {
			test.log(LogStatus.FAIL, "ScreenShot: " + GlobalUtil.getTestException(), image);
			testHist.log(LogStatus.FAIL, "ScreenShot:" + GlobalUtil.getTestException(), image);

		} else {

			test.log(LogStatus.INFO, "ScreenShot: " + GlobalUtil.getTestException(), image);
			testHist.log(LogStatus.INFO, "ScreenShot:" + GlobalUtil.getTestException(), image);

		}

	}
	
	
	public static void attachScreenshotForInfo(String imagePath) {
		String image = test.addScreenCapture(imagePath);
		test.log(LogStatus.INFO,"Screen Shot below" );
		testHist.log(LogStatus.INFO,"Screen Shot below" );
		test.log(LogStatus.INFO, "ScreenShot: " , image);
		testHist.log(LogStatus.INFO, "ScreenShot:", image);
		
	}


}
