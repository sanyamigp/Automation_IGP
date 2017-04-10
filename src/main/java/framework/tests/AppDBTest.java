package framework.tests;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import utilities.ReportFactoryDB;
import utilities.TestData;
import utilities.TestResults;

/**
 * This AppDBTest
 *
 */
public class AppDBTest {

	
	static String query = "";

	/**
	 * this method we use to data into the database
	 * @throws Exception 
	 *
	 */
	//public static void main(String[] args) throws ParseException {
	@Test
	public static void appdb() throws Exception{
		ReportFactoryDB.switchOn();
		ReportFactoryDB.initMySql("sql6.freemysqlhosting.net/sql6135035", "sql6135035", "ubg1YMgphQ");

		int lastRunId = ReportFactoryDB.getLastRunID() + 1;

		for (int i = 2; i <= 5; i++) {
			TestData td = new TestData();
			TestResults tr = new TestResults();

			td.setProjectID("DAMCO");
			td.setSuiteName("Regression");
			td.setTestId("TC40" + i);
			td.setTestDesc("Verify Register page");
			td.setTestPlatformInfo("Win7, Firefox");
			td.setComplexity("HIGH");
			td.setExpectedTime((25.50) + i);

			// ---------------------------------------------------

			tr.setRunId(lastRunId);
			//

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			String autodate = new String(String.format("2016-%d-%d", 1, 1));

			date = df.parse(autodate);

			tr.setRunDateTime(date);
			tr.setStatus("Fail");
			tr.setRemarks("Element Not found");
			tr.setActualTime(20.20);
			tr.setScreenshotref("c:\\a.jpg");
			// ---------------------------------------------------

			ReportFactoryDB.updateTestResultsDB(td, tr);

		}

		String excelTemplatePath = Paths.get("").toAbsolutePath()
				+ "\\src\\test\\resources\\ExcelFiles\\Template_TestResults_Automation.xls";
		ReportFactoryDB.setExcelTemplatePath(excelTemplatePath);
		query = "SELECT * FROM `TBL_RESULTS` WHERE 1 Order By RUN_DATE_TIME DESC";

		ReportFactoryDB.printDataToScreen(query);

		String csvDestPath = Paths.get("").toAbsolutePath() + "\\ExecutionReports\\DB\\CSV\\TestResults.csv";
		ReportFactoryDB.exportDataToCsv(query, csvDestPath);

		String excelDestPath = Paths.get("").toAbsolutePath() + "\\ExecutionReports\\DB\\Excel\\TestResults.xls";
		ReportFactoryDB.exportDataToExcel(query, excelDestPath);

		ReportFactoryDB.closeDBConnection();

	}
}
