package framework.tests;

import java.nio.file.Paths;

import utilities.ConfigReader;
import utilities.ReportFactoryDB;

/**
 * This class is database related
 *
 */
public final class DBTest {

	/**
	 * This class is database related
	 *
	 */
	public static void main(String[] args) {

		String query = "SELECT * FROM `TBL_RESULTS` WHERE 1 Order By RUN_DATE_TIME DESC";
		ReportFactoryDB.switchOn();
		ReportFactoryDB.initSqlite(System.getProperty("user.dir") + "\\ExecutionReports\\DB");

		ReportFactoryDB.setExcelTemplatePath(ConfigReader.getValue("Template_testResultExcelPath"));

		System.out.println(ReportFactoryDB.getLastRunID());

		String excelDestPath = Paths.get("").toAbsolutePath() + "\\ExecutionReports\\DB\\Excel\\TestResults.xls";
		ReportFactoryDB.exportDataToExcel(query, excelDestPath);

		String csvDestPath = Paths.get("").toAbsolutePath() + "\\ExecutionReports\\DB\\CSV\\TestResults.csv";
		ReportFactoryDB.exportDataToCsv(query, csvDestPath);

		ReportFactoryDB.closeDBConnection();
	}

}
