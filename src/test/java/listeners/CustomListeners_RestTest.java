/*			About CustomListner
 * ====================================
 * This class is a central point to handle test events.
 * It responsible to handle all the common events that are generated while test is started, running 
 * and going to stop.
 * Class handles all the basic setup and tear down functionalities as well as handle the updating test results
 * into HTML, Excel, Database and logs.
 * 
 * */

package listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utilities.DriverUtil;
import utilities.DropBoxUtil;
import utilities.ExcelDataUtil;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.LogUtil;
import utilities.ReportFactoryDB;
import utilities.SendMail;
import utilities.TestData;
import utilities.TestResults;
import utilities.Utility;

/**
 * @author TX
 *
 */
/**
 * @author TestLink
 *
 */
public class CustomListeners_RestTest extends Utility implements ISuiteListener, IInvokedMethodListener2, ITestListener {
	LocalDateTime startTime, endTime;
	String testCaseID;

	// Before Suite start. Once suite is going to start this function will
	// trigger.
	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onStart(org.testng.ISuite)
	 */
	public void onStart(ISuite suite) {
		// Get Driver
		// Start base URL
		try {
			// Get all the common setting from excel file that are required for
			// reports.
			GlobalUtil.setCommonSettings(ExcelDataUtil.getCommonSettings());

			// Current suite name extracted from the xml file.
			GlobalUtil.setCurrentSuiteName(suite.getName());
			
			// This browser coming from XML File from the defined parameter name = Browser
			// Parameter with name Browser
			String browser = "";
			browser = suite.getParameter("Browser");

			// If parameter is not found in xml then default browser will be picked from config file
			if (browser == null) {
				browser = "REST";
				GlobalUtil.getTestConfig().setBrowserName(browser);
			}
			
			LogUtil.infoLog(getClass(),
					"\n\n+============================================================================================================================+");
			LogUtil.infoLog(getClass(), GlobalUtil.getCurrentSuiteName() + " suite started" + " at " + new Date());
			LogUtil.infoLog(getClass(), "Suite Browser: " + browser);
			// Set browser name for reporting purpose.
			GlobalUtil.setCurrentBrowser(browser);
			
			//Start ExtentHtmlReport
			HtmlReportUtil.startHtmlReport();
			// Switch on database reporting
			ReportFactoryDB.switchOn();


			// Local flat file sqlite database
			ReportFactoryDB.initSqlite(Paths.get("").toAbsolutePath().toString() + "\\ExecutionReports\\DB");

			// Get Last Run Id from DB for reporting purpose.
			// Get run id only one time during all the run
			if (GlobalUtil.isSuitesRunStarted() == false) {
				GlobalUtil.setLastRunId((ReportFactoryDB.getLastRunID() + 1));
			}
			GlobalUtil.setSuitesRunStarted(true);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// End onStart()

	// After Suite. Once suite run has finished this function will trigger.
	/* (non-Javadoc)
	 * @see org.testng.ISuiteListener#onFinish(org.testng.ISuite)
	 */
	public void onFinish(ISuite suite) {
		
		GlobalUtil.setTotalSuites(GlobalUtil.getTotalSuites() - 1);
		LogUtil.infoLog(getClass(), GlobalUtil.getCurrentSuiteName() + " suite finished" + " at " + new Date());
		try {
			// ReportFactoryDB.getComparisonReport(GlobalUtil.testConfig.getProjectName());
			ReportFactoryDB.getComparisonReport(GlobalUtil.getCommonSettings().getProjectName());

			// Teardown only perform when all suites has finished
			if (GlobalUtil.getTotalSuites() <= 0) {
				HtmlReportUtil.tearDownReport();
			}
			GlobalUtil.renameFile();
			ReportFactoryDB.closeDBConnection();


			String dbxCof = "N";

			if (dbxCof.equalsIgnoreCase("Y")) {
				DropBoxUtil.switchOn();
				DropBoxUtil.init(Utility.getValue("dropBox_AccessToken"));
				DropBoxUtil.uploadFile("C:\\Users\\SUKHJINDER\\Desktop\\SmartShowExecutionReports1.zip");
			}

			if (GlobalUtil.getCommonSettings().getEmailOutput().equalsIgnoreCase("Y"))
				SendMail.sendEmailToClient();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			String htmlReportFile = System.getProperty("user.dir") + "\\" + Utility.getValue("HtmlReportFullPath");
			File f = new File(htmlReportFile);
			if (f.exists()) {

				try {
					Process p = Runtime.getRuntime()
							.exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"" + htmlReportFile
									+ "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// Run before Test start belong to IInvokedMethodListener2
	/* (non-Javadoc)
	 * @see org.testng.IInvokedMethodListener2#beforeInvocation(org.testng.IInvokedMethod, org.testng.ITestResult, org.testng.ITestContext)
	 */
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult arg1, ITestContext arg2) {
		String tmp = method.getTestMethod().getTestClass().getName();
		testCaseID = tmp.substring(tmp.indexOf("IGP")).trim();
		startTime = LocalDateTime.now();

		GlobalUtil.setTestData(new TestData());
		GlobalUtil.setTestResult(new TestResults());
		GlobalUtil.setTestData(ExcelDataUtil.getTestDataWithTestCaseID(testCaseID));

		// SetRun ID
		GlobalUtil.getTestResult().setRunId(GlobalUtil.getLastRunId());

		// Reset the screenshot path required otherwise it will write same into
		// test result files
		GlobalUtil.getTestResult().setScreenshotref("");

		// Set current suite name for reporting
		GlobalUtil.getTestData().setSuiteName(GlobalUtil.getCurrentSuiteName());
		// Set platform for reporting
		GlobalUtil.getTestData().setTestPlatformInfo(System.getProperty("os.name") + "-" + GlobalUtil.getCurrentBrowser());
		// Set Project ID
		GlobalUtil.getTestData().setProjectID(GlobalUtil.getCommonSettings().getProjectName());
		GlobalUtil.getTestResult().setRunDateTime(new Date());

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + testCaseID);

		LogUtil.infoLog(getClass(), "Test description: " + GlobalUtil.getTestData().getTestDesc());
		
		
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	public void onTestStart(ITestResult result) {

	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	public void onTestSuccess(ITestResult result) {

		endTime = LocalDateTime.now();
		Duration.between(startTime, endTime).getSeconds();
		LogUtil.infoLog(getClass().getSimpleName(),
				"Total Time taken in(seconds):" + +Duration.between(startTime, endTime).getSeconds());

		GlobalUtil.getTestResult().setStatus("PASS");
		GlobalUtil.getTestResult().setActualTime(Duration.between(startTime, endTime).getSeconds());

		// Update results in excel
		ExcelDataUtil.updateTestResults(GlobalUtil.getTestData(), GlobalUtil.getTestResult());

		// Update results in DB
		ReportFactoryDB.updateTestResultsDB(GlobalUtil.getTestData(), GlobalUtil.getTestResult());

		// End HTML Report for current test
		HtmlReportUtil.testPass(GlobalUtil.getTestData().getTestId());

		

	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	public void onTestFailure(ITestResult result) {
		endTime = LocalDateTime.now();
		Duration.between(startTime, endTime).getSeconds();
		LogUtil.infoLog(getClass().getSimpleName(),
				"Total Time taken in(seconds):" + +Duration.between(startTime, endTime).getSeconds());

		GlobalUtil.getTestResult().setStatus("FAIL");
		GlobalUtil.getTestResult().setActualTime(Duration.between(startTime, endTime).getSeconds());
		GlobalUtil.getTestResult().setRemarks(result.getThrowable().getMessage());

		LogUtil.errorLog(getClass().getSimpleName(),
				String.format("\n========ERROR IN TEST : %s =====================================", testCaseID));
		LogUtil.errorLog(getClass().getSimpleName(), result.getThrowable().getMessage());
		

		// Update results in excel
		ExcelDataUtil.updateTestResults(GlobalUtil.getTestData(), GlobalUtil.getTestResult());

		// Update results in db
		ReportFactoryDB.updateTestResultsDB(GlobalUtil.getTestData(), GlobalUtil.getTestResult());

		// Log Test Failed in HTML and End the Test
		HtmlReportUtil.testFail(GlobalUtil.getTestData().getTestId());
		//Update results in TestLink
				
		
		
		
		

	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	public void onTestSkipped(ITestResult result) {
		endTime = LocalDateTime.now();
		Duration.between(startTime, endTime).getSeconds();
		LogUtil.infoLog(getClass().getSimpleName(),
				"Test Skipped - Total Time taken in(seconds):" + Duration.between(startTime, endTime).getSeconds());

		GlobalUtil.getTestResult().setStatus("SKIPPED");
		GlobalUtil.getTestResult().setActualTime(Duration.between(startTime, endTime).getSeconds());

		ExcelDataUtil.updateTestResults(GlobalUtil.getTestData(), GlobalUtil.getTestResult());
		ReportFactoryDB.updateTestResultsDB(GlobalUtil.getTestData(), GlobalUtil.getTestResult());
		HtmlReportUtil.testSkipped(GlobalUtil.getTestData().getTestId(), GlobalUtil.getTestException());
		
	}

	// These methods are not used but required for Listner purpose
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
	 */
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	public void onStart(ITestContext context) {

	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	public void onFinish(ITestContext context) {

	}

	/* (non-Javadoc)
	 * @see org.testng.IInvokedMethodListener2#afterInvocation(org.testng.IInvokedMethod, org.testng.ITestResult, org.testng.ITestContext)
	 */
	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1, ITestContext arg2) {

	}

	/* (non-Javadoc)
	 * @see org.testng.IInvokedMethodListener#afterInvocation(org.testng.IInvokedMethod, org.testng.ITestResult)
	 */
	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {

	}

	/* (non-Javadoc)
	 * @see org.testng.IInvokedMethodListener#beforeInvocation(org.testng.IInvokedMethod, org.testng.ITestResult)
	 */
	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

	}

}// End class
