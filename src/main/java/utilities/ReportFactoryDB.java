/*  INTORDUCTION
 * ----------------------------------------------------------------------------------
 *  Start date: 31-08-2016
 *  Last modified: 02-11-2016
 *  
 * ReportFactoryDB is responsible to save test execution result in the database.
 * That can be a Sqlite flat database file or MySql Server database
 *-------------------------------------------------------------------------------*/

/*
 * Usage
 * -------------------
 * 1. Set the Database url/path - setDbUrl(String)
 * 2. Set Switch setDbSwitch(1) -> Possible values On/Off or 0/1
 * 3. Update Results
 * 4. Close Connection
 * */

package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

/**
 * @author SUKHJINDER SINGH
 * @version 1.0
 * @category Save Test execution Result to Database
 * 
 */
public class ReportFactoryDB {

	private static Logger logger = Logger.getLogger(ReportFactoryDB.class.getSimpleName());
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String dburl = "jdbc:mysql://";
	public static final String EXCEPTION_MESSAGE = "Error occured: ";
	public static final String DBSWITCHOFFERR ="DB is switched off! ";
	// Database credentials
	private static final String  USER = "sql6133996";
	private static String PASS = "2vtM3QiUQM";
	private static String excelTemplatePath = "";
	private static String excelDestPath = "";
	// Database objects
	private static Connection cnxn = null;
	
	private ReportFactoryDB(){
	}
	/**
	 * @param dB_URL
	 */
	public static void setDBURL(String dBURL) {
		dburl = dBURL;
	}

	/**
	 * @return
	 */
	public static String getPASS() {
		return PASS;
	}

	/**
	 * @param pass
	 */
	public static void setPASS(String pass) {
		PASS = pass;
	}

	/**
	 * @return
	 */
	public static String getExcelTemplatePath() {
		return excelTemplatePath;
	}

	/**
	 * @param excelTemplatePath
	 */
	public static void setExcelTemplatePath(String excelTemplatePath) {
		ReportFactoryDB.excelTemplatePath = excelTemplatePath;
	}

	/**
	 * @return
	 */
	public static String getExcelDestPath() {
		return excelDestPath;
	}

	private static void setExcelDestPath(String excelDestPath) {
		ReportFactoryDB.excelDestPath = excelDestPath;
	}

	Statement stmt = null;
	ResultSet rs = null;

	public enum Switch {
		OFF, ON
	}

	// Set initially Switch OFF
	private static Switch dbSwitch = Switch.OFF;

	/**
	 * @param dbDir
	 */
	public static synchronized  void initSqlite(String dbDir) {
		if (isSwitchOn()) {
			try {
				// Register Driver
				Class.forName("org.sqlite.JDBC");
				String dbUrl = "jdbc:sqlite:" + dbDir + "\\TestResults.db";

				// Open data base connection
				cnxn = DriverManager.getConnection(dbUrl);
				Statement stmt = null;
				if (cnxn != null) {
					logger.log(Level.FINEST, "Database successfully connected.");

					DatabaseMetaData dm = (DatabaseMetaData) cnxn.getMetaData();
					LogUtil.infoLog(ReportFactoryDB.class, "+=============Database started=============+");
					LogUtil.infoLog(ReportFactoryDB.class, "Database driver name: " + dm.getDriverName());
					String dbInfo = new String(String.format("Product name %s, Version %s", dm.getDatabaseProductName(),
							dm.getDatabaseProductVersion()));
					LogUtil.infoLog(ReportFactoryDB.class, dbInfo);
					LogUtil.infoLog(ReportFactoryDB.class, "+==========================================+\n");
					stmt = cnxn.createStatement();

					// Create table
					String sql = "CREATE TABLE IF NOT EXISTS TBL_RESULTS ("
							+ "RECORD_ID  INTEGER PRIMARY KEY   AUTOINCREMENT," + "PROJECT_ID  varchar(25) NOT NULL,"
							+ "SUITE_NAME  varchar(50) NOT NULL," + "TEST_ID  varchar(25) NOT NULL,"
							+ "TEST_DESC  varchar(500) NULL," + "TEST_ENVIRONMENT_INFO   varchar(50) NULL,"
							+ "COMPLEXITY  varchar(6) NULL DEFAULT 'Low'," + "RUN_ID  int NOT NULL DEFAULT 1,"
							+ "RUN_DATE_TIME  DATE NOT NULL," + "STATUS  varchar(10) NOT NULL,"
							+ "REMARKS  varchar(500) NULL," + "EXPECTED_TIME double(4,2) NULL DEFAULT 0.0,"
							+ "ACTUAL_TIME double(4,2) NULL DEFAULT 0.0," + "SCREENSHOT_REF varchar(200) NULL"
							+ ");";
					stmt.executeUpdate(sql);
					stmt.close();
				}

			} catch (Exception ex) {
				LogUtil.errorLog(ReportFactoryDB.class, EXCEPTION_MESSAGE, ex);
				ReportFactoryDB.switchOff();

			} // end finally try

		} else {
			LogUtil.errorLog(ReportFactoryDB.class, "Database reporter is switched off");
		}
	}

	/**
	 * @param dbUrl
	 * @param user
	 * @param password
	 */
	public static synchronized  void initMySql(String dbUrl, String user, String password) {
		if (isSwitchOn()) {
			try {
				// Register Driver
				Class.forName("com.mysql.jdbc.Driver");
				ReportFactoryDB.dburl += dbUrl;

				// Open data base connection
				cnxn = DriverManager.getConnection(ReportFactoryDB.dburl, user, password);
				if (cnxn != null) {
					logger.log(Level.FINEST, "Database successfully connected.");

					DatabaseMetaData dm = (DatabaseMetaData) cnxn.getMetaData();
					LogUtil.infoLog(ReportFactoryDB.class, "+=============Report Database started=============+\n");
					createTable();
				}

			} catch (Exception ex) {
				LogUtil.errorLog(ReportFactoryDB.class, EXCEPTION_MESSAGE, ex);
				ReportFactoryDB.switchOff();
			} // end finally try
		} // End if Switch on
		else {
			LogUtil.errorLog(ReportFactoryDB.class, "Database reporter is switched off");
		}
	}// End init

	private static boolean isSwitchOn() {
		if (dbSwitch.equals(Switch.ON))
			return true;
		else
			return false;
	}

	/*
	 * START - All Getter And Setter
	 * /*-----------------------------------------------------------------------
	 * --------
	 */
	/**
	 * @return
	 */
	public String getDbSwitch() {
		return dbSwitch.toString();
	}

	/**
	 * 
	 */
	public static void switchOn() {
		ReportFactoryDB.dbSwitch = ReportFactoryDB.Switch.ON;
	}

	/**
	 * 
	 */
	public static void switchOff() {
		ReportFactoryDB.dbSwitch = ReportFactoryDB.Switch.OFF;
	}

	private static void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS TBL_RESULTS ("
				+ "	RECORD_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" + " PROJECT_ID nvarchar(25) NOT NULL,\n"
				+ "	SUITE_NAME nvarchar(75) NOT NULL,\n" + "	TEST_ID nvarchar(50) NOT NULL,\n"
				+ " TEST_DESC nvarchar(500) NOT NULL,\n" + " TEST_ENVIRONMENT_INFO nvarchar(50) NOT NULL,\n"
				+ " COMPLEXITY NVARCHAR(6) NOT NULL DEFAULT 'LOW',\n" + " RUN_ID INT NOT NULL,\n"
				+ " RUN_DATE_TIME DATE NOT NULL,\n" + " STATUS NVARCHAR(10) NOT NULL,\n" + " REMARKS NVARCHAR(1000),\n"
				+ "EXPECTED_TIME double(4,2) DEFAULT 0.0,\n" + "ACTUAL_TIME double(4,2) DEFAULT 0.0,\n"
				+ "SCREENSHOT_REF nvarchar(100) DEFAULT 'NA'" + ");";
		try {
			Statement stmt = cnxn.createStatement();
			boolean b = stmt.execute(sql);

			if (b = true) {
				logger.log(Level.INFO, "Table ready for data!");

			} else {
				logger.log(Level.SEVERE, "New Table creation Failed");
			}
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
			LogUtil.errorLog(ReportFactoryDB.class, "Table creation error -->" + ex);
		}
	}// End createTable()

	private static void deleteTable() {
		String sql = "DROP TABLE IF EXISTS TBL_RESULTS; ";
		try {
			Statement stmt = cnxn.createStatement();
			boolean b = stmt.execute(sql);
			if (b = true) {
				logger.log(Level.INFO, "Table deleted!");

			} else {
				logger.log(Level.SEVERE, "Table delete Failed");
			}
			if (stmt != null)
				stmt.close();

		} catch (Exception ex) {
			LogUtil.errorLog(ReportFactoryDB.class, "Table delete error -->" + ex);
		}
	}// End deleteTable()

	/**
	 * @param td
	 * @param tr
	 */
	public static synchronized void updateTestResultsDB(TestData td, TestResults tr) {
		/*
		 * Table Schema-Columns RECORD_ID PROJECT_ID SUITE_NAME TEST_ID
		 * TEST_DESC TEST_PLATFORM_INFO COMPLEXITY RUN_ID RUN_DATE_TIME
		 * LAST_UPDATE STATUS REMARKS EXPECTED_TIME ACTUAL_TIME SCREENSHOT_REF
		 */

		if (isSwitchOn()) {
			if (cnxn != null) {
				String sql = "INSERT INTO  `TBL_RESULTS` ( " + "`PROJECT_ID` , " + "`SUITE_NAME` ," + "`TEST_ID` , "
						+ " `TEST_DESC` , " + " `TEST_ENVIRONMENT_INFO` ," + " `COMPLEXITY` ," + "`RUN_ID` , "
						+ "`RUN_DATE_TIME` ," + "`STATUS` ,  " + "`REMARKS` ,  " + "`EXPECTED_TIME` ,  "
						+ "`ACTUAL_TIME`,`SCREENSHOT_REF`) " + "VALUES (?,  ?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?, ?, ?,?);";
				try (PreparedStatement pstmt = cnxn.prepareStatement(sql);) {
					pstmt.setString(1, td.getProjectID());
					pstmt.setString(2, td.getSuiteName());
					pstmt.setString(3, td.getTestId());
					pstmt.setString(4, td.getTestDesc());
					pstmt.setString(5, td.getTestPlatformInfo());
					pstmt.setString(6, td.getComplexity());
					pstmt.setInt(7, tr.getRunId());

					SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd");
					pstmt.setString(8, dateformat.format(tr.getRunDateTime()).toString());

					pstmt.setString(9, tr.getStatus());
					pstmt.setString(10, tr.getRemarks());
					pstmt.setDouble(11, td.getExpectedTime());
					pstmt.setDouble(12, tr.getActualTime());
					pstmt.setString(13, tr.getScreenshotref());
					pstmt.execute();

					int rowEffected = pstmt.getUpdateCount();
					if (rowEffected > 0) {
						
					}
					
					if (!pstmt.isClosed())
						pstmt.close();
				} // End try
				catch (SQLException e) {
					LogUtil.errorLog(ReportFactoryDB.class, EXCEPTION_MESSAGE, e);
					logger.log(Level.SEVERE, e.getMessage());
				} // End catch
			} // Connection is ready
			else {
				logger.log(Level.SEVERE, "Database switch is on but there is no Connection with database");
			}
		} // Database switch is on
	}// End updateTestResults()

	/**
	 * @param sql
	 * @return
	 */
	public static synchronized ResultSet selectResultsDB(String sql) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				// SELECT * FROM `TBL_RESULTS` WHERE 1
				try (PreparedStatement pstmt = cnxn.prepareStatement(sql);) {
					return pstmt.executeQuery();
				} catch (Exception ex) {
					LogUtil.errorLog(ReportFactoryDB.class, EXCEPTION_MESSAGE, ex);
					logger.log(Level.SEVERE, "Error while fetching records--->" + ex.getMessage());
				}
			} // End if cnxn
		} // End is switch on
		else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
		return null;
	}// End selectResultsDB()

	/**
	 * @return
	 */
	public static synchronized int getLastRunID() {
		int id = 0;
		if (isSwitchOn()) {
			if (cnxn != null) {
				// SELECT * FROM `TBL_RESULTS` WHERE 1
				try (PreparedStatement pstmt = cnxn
						.prepareStatement("SELECT MAX(RUN_ID) AS HighestID FROM TBL_RESULTS");) {
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						id = rs.getInt("HighestID");
					}
				} catch (Exception ex) {
					LogUtil.errorLog(ReportFactoryDB.class, "Error while fetching records-->", ex);
					logger.log(Level.SEVERE, "Error while fetching records-->" + ex.getMessage());
				}
			} // End if cnxn
		} // End is switch on
		else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
		return id;
	}// End getLastRunID()

	/**
	 * @param sql
	 * @param filename
	 */
	@SuppressWarnings("resource")
	public static synchronized void exportDataToCsv(String sql, String filename) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					if (!filename.toLowerCase().endsWith(".csv")) {
						throw new Exception("Invalid csv file type");
					}
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();

					final CSVFormat format = CSVFormat.DEFAULT.withHeader(rs);
					final CSVPrinter printer = new CSVPrinter(new FileWriter(filename), format);
					printer.printRecords(rs);
					printer.flush();
				} catch (Exception e) {
					LogUtil.errorLog(ReportFactoryDB.class, EXCEPTION_MESSAGE, e);
				}
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
	}// End exportDataToCsv()

	/**
	 * @param sql
	 * @param excelFilePath
	 */
	public static void exportDataToExcel(String sql, String excelFilePath) {
		ReportFactoryDB.setExcelDestPath(excelFilePath);
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					if (ReportFactoryDB.getExcelTemplatePath().isEmpty()) {
						throw new IllegalArgumentException("Excel template is not specified");
					}
					if (ReportFactoryDB.getExcelDestPath().isEmpty()) {
						throw new IllegalArgumentException("Excel destination is not specified");
					}
					if (!(ReportFactoryDB.getExcelDestPath().toLowerCase().endsWith("xls")
							|| ReportFactoryDB.getExcelDestPath().toLowerCase().endsWith("xlsx"))) {
						throw new IllegalArgumentException("Correct Excel file not specified");
					}
					// Copy File for results
					File srcFile = new File(ReportFactoryDB.getExcelTemplatePath());
					File destFile = new File(ReportFactoryDB.getExcelDestPath());
					FileUtils.copyFile(srcFile, destFile);
					if (!FileUtils.getFile(destFile).exists()) {
						throw new IllegalArgumentException("Excel destination file not found...");
					}
					logger.log(Level.INFO, "Excel file is ready to write...");

					// Start Writing data to excel
					FileInputStream fis = null;
					Workbook wb = null;
					FileOutputStream fos = null;
					fis = new FileInputStream(destFile);
					wb = WorkbookFactory.create(fis);
					Sheet sheet = wb.getSheetAt(0);

		
					int startRow;
					startRow = sheet.getLastRowNum();

					startRow++;
					Row row = null;
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();

					CellStyle style = wb.createCellStyle();
					Font font = wb.createFont();
					font.setBoldweight(Font.BOLDWEIGHT_BOLD);
					style.setFont(font);

					boolean firstRun = false;
					SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-YYYY");

					while (rs.next()) {
						if (sheet.getRow(startRow) == null)
							row = sheet.createRow(startRow);
						if (firstRun == false) {
							sheet.getRow(0).createCell(5).setCellStyle(style);
							sheet.getRow(0).getCell(5).setCellValue("Project Id - " + rs.getString("PROJECT_ID"));
							sheet.getRow(0).createCell(6).setCellStyle(style);
							sheet.getRow(0).getCell(6)
									.setCellValue("Test Env - " + rs.getString("TEST_ENVIRONMENT_INFO"));
							sheet.getRow(0).createCell(8).setCellStyle(style);
							sheet.getRow(0).getCell(8)
									.setCellValue("Dated - " + date_format.format(new Date()).toString());
							firstRun = true;
						}
						row.createCell(0).setCellValue(rs.getString("SUITE_NAME"));
						row.createCell(1).setCellValue(rs.getString("TEST_ID"));
						row.createCell(2).setCellValue(rs.getString("TEST_DESC"));

						DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat destFormat = new SimpleDateFormat("dd-MM-yyyy");

						row.createCell(3)
								.setCellValue(destFormat.format(sourceFormat.parse(rs.getString("RUN_DATE_TIME"))));
						row.createCell(4).setCellValue(rs.getString("COMPLEXITY"));
						row.createCell(5).setCellValue(rs.getString("STATUS"));
						row.createCell(6).setCellValue(rs.getDouble("EXPECTED_TIME"));
						row.createCell(7).setCellValue(rs.getDouble("ACTUAL_TIME"));
						row.createCell(8).setCellValue(rs.getString("REMARKS"));
						row.createCell(9).setCellValue(rs.getString("SCREENSHOT_REF"));

						// Move to next row
						startRow++;
					} // End while

					fos = new FileOutputStream(destFile);
					wb.write(fos);
					fos.flush();
					if (wb != null)
						wb.close();
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // if Cnxn not null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized...");
			}
		} // End isSwitch
		LogUtil.infoLog(ReportFactoryDB.class,DBSWITCHOFFERR);
	}// End exportDataToExcel()

	/**
	 * @param sql
	 */
	public static synchronized void printDataToScreen(String sql) {
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					PreparedStatement pstmt = cnxn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();

					System.out.println("Column Numbers:" + columnsNumber);

					int i = 1;
					while (rs.next()) {
					LogUtil.infoLog(ReportFactoryDB.class, "\n---------------RECORD_ID# %s -------------------------+\n" +
								rs.getString(1));
					LogUtil.infoLog(ReportFactoryDB.class, "PROJECT_ID   :" + rs.getString(2) + "\tSUITE_NAME :" + rs.getString(3));
					LogUtil.infoLog(ReportFactoryDB.class, "TEST_ID      :" + rs.getString(4) + "\t\tTEST_DESC  :" + rs.getString(5));
					LogUtil.infoLog(ReportFactoryDB.class, "COMPLEXITY   :" + rs.getString(7) + "\t\tTEST_ENVIRONMENT_INFO : " + rs.getString(6));
					LogUtil.infoLog(ReportFactoryDB.class, "RUN_ID       :" + rs.getString(8) + "\t\tRUN_DATE_TIME : " + rs.getString(9));	
					LogUtil.infoLog(ReportFactoryDB.class, "EXPECTED_TIME:" + rs.getString(12) + "\t\tACTUAL_TIME : " + rs.getString(13));
					LogUtil.infoLog(ReportFactoryDB.class, "REMARKS      : " + rs.getString(11) + "\tSCREENSHOT_REF :" + rs.getString(14));
					LogUtil.infoLog(ReportFactoryDB.class, "+---------------STATUS: %s -------------------------+\n :" + rs.getString(10));
					}
				} catch (Exception e) {
					LogUtil.errorLog(ReportFactoryDB.class, "Exception caught", e);
				}
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized....");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
	}

	/**
	 * @param sql
	 */
	public static void deleteRecordFromDB(String sql) {


		if (isSwitchOn()) {
			if (cnxn != null) {
				try (PreparedStatement preparedStmt = cnxn.prepareStatement(sql);) {
					preparedStmt.execute(sql);
				} catch (SQLException e) {
					LogUtil.errorLog(ReportFactoryDB.class, "Exception caught", e);
				}
			} // cnxn not null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized.....");
			}

		} else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
	}// End deleteRecordFromDB()

	// This function is responsible to create a comparison report based on last
	// three run
	public static void getComparisonReport(String projectId) {
		SetMultimap<String, String> testMap = HashMultimap.create();
		String query = "Select * from TBL_RESULTS where `PROJECT_ID`= '" + projectId + "' AND `RUN_ID`  In "
				+ "(SELECT `RUN_ID` FROM (Select RUN_ID from TBL_RESULTS Group By Run_ID order by RUN_ID desc Limit 0,3) as T )Order By RUN_ID desc";
		// Template file path
		String template = ConfigReader.getValue("ComparisonTemplatePath");

		// New Excel file path
		String excelFilePath = Paths.get("").toAbsolutePath()
				+ "\\ExecutionReports\\DB\\Excel\\ComparisonTestResults.xls";
		if (isSwitchOn()) {
			if (cnxn != null) {
				try {
					// Copy Template
					FileUtils.copyFile(FileUtils.getFile(template), FileUtils.getFile(excelFilePath));

					PreparedStatement pstmt = cnxn.prepareStatement(query);
					ResultSet rs = pstmt.executeQuery();

					Workbook workbook = null;
					FileInputStream fis = null;
					fis = new FileInputStream(excelFilePath);
					workbook = WorkbookFactory.create(fis);
					if (workbook.getSheetIndex(workbook.getSheet("TestResults")) == -1) {
						throw new Exception("No sheet found with name - TestResults");
					}

					Sheet sheet = workbook.getSheet("TestResults");
					Row hRow = sheet.getRow(0);
					if (hRow == null) {
						LogUtil.infoLog(ReportFactoryDB.class, "Header row is not present... ");
						hRow = sheet.createRow(0);
						hRow.createCell(0).setCellValue("ProjectID");
						hRow.createCell(1).setCellValue("TestID");
						hRow.createCell(2).setCellValue("Test_Desc");
						hRow.createCell(3).setCellValue("RunDate");
						hRow.createCell(4).setCellValue("Status");
						hRow.createCell(5).setCellValue("Run_ID");
					}

					SimpleDateFormat sourceDateFormat = new SimpleDateFormat("YYYY-MM-dd");
					SimpleDateFormat destDateFormat = new SimpleDateFormat("dd/MM/YYYY");

					String lastprojectId = "";
					while (rs.next()) {
						// Create Unique Test ID And Run ID
						testMap.put(rs.getString("RUN_ID"), rs.getString("TEST_ID"));
						Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

						if (!lastprojectId.equalsIgnoreCase(rs.getString("PROJECT_ID"))) {
							dataRow.createCell(0).setCellValue(rs.getString("PROJECT_ID"));
						}
						lastprojectId = rs.getString("PROJECT_ID");

						dataRow.createCell(1).setCellValue(rs.getString("TEST_ID"));
						dataRow.createCell(2).setCellValue(rs.getString("TEST_DESC"));

						String strDate = rs.getString("RUN_DATE_TIME").split(" ")[0].trim();
						DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate date = LocalDate.parse(strDate, fromFormatter);
						// Convert to dd-mm-yyyy
						strDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();

						dataRow.createCell(3).setCellValue(strDate);
						dataRow.createCell(4).setCellValue(rs.getString("STATUS"));
						dataRow.createCell(5).setCellValue(rs.getString("RUN_ID"));
					}
					// Write data to Excel file
					FileOutputStream fileOut = new FileOutputStream(excelFilePath);
					workbook.write(fileOut);
					fileOut.flush();
					workbook.close();
					fileOut.close();
					ReportFactoryDB.writeToExcelFile(excelFilePath, "TestComparisonSheet", testMap);
					LogUtil.infoLog(ReportFactoryDB.class, "Data to excel written successfully");
				} catch (Exception e) {
					LogUtil.errorLog(ReportFactoryDB.class, "Exception caught", e);
				}
				// Write Test ID to New Excel file
				// Write All TestID to Excel
			} // cnxn is null
			else {
				logger.log(Level.WARNING, "Error! Database connection is not initialized..");
			}
		} // Is Switch on
		else {
			logger.log(Level.WARNING, DBSWITCHOFFERR);
		}
	}// End getComparisonReport()

	/**
	 * @param destFilePath
	 * @param sheetName
	 * @param testMap
	 */
	@SuppressWarnings("resource")
	public static void writeToExcelFile(String destFilePath, String sheetName, SetMultimap<String, String> testMap) {
		Workbook workbook = null;
		FileInputStream fis = null;
		FileOutputStream fileOut = null;
		try {
			fis = new FileInputStream(destFilePath);
			workbook = WorkbookFactory.create(fis);
			fileOut = new FileOutputStream(destFilePath);
			Sheet sheetComparison = null;
			if (sheetName.isEmpty())
				sheetComparison = workbook.createSheet();

			else if (workbook.getSheetIndex(workbook.getSheet(sheetName)) == -1)
				sheetComparison = workbook.createSheet(sheetName);
			else
				sheetComparison = workbook.getSheet(sheetName);

			// Create Header
			Row row = sheetComparison.getRow(2);
			if (row == null) {
				throw new Exception("Row not available!!! ");
			}

			Sheet sheetTestResult = workbook.getSheet("TestResults");
			int columnsStart = 2;
			Set<String> keysTestMap = testMap.keySet();
			int keysize = keysTestMap.size();


			Map<String, Integer> columnIndexForUniqueRunId = new HashMap<String, Integer>();
			int idCount = 1;
			for (String key : keysTestMap) {

				row.getCell(columnsStart).setCellValue("Run" + idCount + "(" + key + ")");
				columnIndexForUniqueRunId.put(key, columnsStart);
				columnsStart++;
				idCount++;
			}

			SortedSet<String> testIds = new TreeSet<String>();
			// Get Unique sorted Test
			for (String key : keysTestMap) {
				Stream<String> tid = testMap.get(key).stream();
				tid.forEach(id -> testIds.add(id));
			} // End for

			// Write Unique TestID to Comparison sheet
			for (String testId : testIds) {
				sheetComparison.createRow(sheetComparison.getLastRowNum() + 1).createCell(0).setCellValue(testId);
			}

			// Get Date based on the run id and Store it in the comparison sheet
			Map<Integer, String> dateFromRunId = new HashMap<Integer, String>();
			for (String key : keysTestMap) {
				int rowIndex = findRow(sheetTestResult, key);
				if (rowIndex != -1) {
					// Get Dates from
					dateFromRunId.put(rowIndex, sheetTestResult.getRow(rowIndex).getCell(3).getStringCellValue());
					// Set Dates
					sheetComparison.getRow(3).getCell(columnIndexForUniqueRunId.get(key))
							.setCellValue(dateFromRunId.get(rowIndex));
				}
			}

			// Get descriptions and store in comparison sheet
			Map<String, String> descFromTestId = new HashMap<String, String>();
			for (String testId : testIds) {
				int rowIndex = findRow(sheetTestResult, testId);

				if (rowIndex != -1) {
					// Get Descrption
					String desc = sheetTestResult.getRow(rowIndex).getCell(2).getStringCellValue();
					// Find Row in Test Comparison and Store Desc
					int rowIndexComp = findRow(sheetComparison, testId);
					if (rowIndexComp != -1) {

						Row r = sheetComparison.getRow(rowIndexComp);
						if (r == null)
							r = sheetComparison.createRow(rowIndexComp);
						r.createCell(1).setCellValue(desc);
					}
				}
			}
			int lastRowOfComShet = sheetComparison.getLastRowNum();
			for (int rowIndex = 4; rowIndex <= lastRowOfComShet; rowIndex++) {
				Row r = sheetComparison.getRow(rowIndex);
				for (int CellIndex = 2; CellIndex <= 4; CellIndex++) {
					if (r.getCell(CellIndex) == null) {
						r.createCell(CellIndex).setCellValue("NA");
					}
				}
			}
			// Get all the information for the test
			Multimap<String, List<String>> multimapRecords = ArrayListMultimap.create();
			for (String key : keysTestMap) {
				List<Integer> rowIndexs = findRows(sheetTestResult, key);
		
				for (int idx : rowIndexs) {
					List<String> record = new ArrayList<>();
					// Get TestID
					String testID = sheetTestResult.getRow(idx).getCell(1).getStringCellValue();
					record.add(testID);

					// Get TestDesc
					String testDesc = sheetTestResult.getRow(idx).getCell(2).getStringCellValue();
					record.add(testDesc);

					// Get TestStatus
					String testStatus = sheetTestResult.getRow(idx).getCell(4).getStringCellValue();

					record.add(testStatus);
					multimapRecords.put(key, record);
				} // End for loop inner
			} // End for loop outer

			// Write records for further processing
			workbook.write(fileOut);
			fileOut.flush();
			workbook.close();
			fileOut.close();
			fis.close();

			// Open Updated File again
			fis = new FileInputStream(destFilePath);
			workbook = WorkbookFactory.create(fis);
			fileOut = new FileOutputStream(destFilePath);
			sheetTestResult = workbook.getSheet("TestResults");
			sheetComparison = workbook.getSheet(sheetName);

			// Get a TestID's based on RunID from TestResult sheet
			for (String key : keysTestMap) {
				Iterator rowItIndex = findRows(sheetTestResult, key).iterator();
				while (rowItIndex.hasNext()) {
					// Get status from of test based on the current run id
					int rowIndex = (int) rowItIndex.next();
					String status = sheetTestResult.getRow(rowIndex).getCell(4).getStringCellValue();
					String tId = sheetTestResult.getRow(rowIndex).getCell(1).getStringCellValue();
					int rowIndexComp = findRow(sheetComparison, tId);

					// Set status for the test id in comparison sheet for the
					// current run id
					if (rowIndexComp != -1) {
						Row r = sheetComparison.getRow(rowIndexComp);
						if (r == null)
							r = sheetComparison.createRow(rowIndexComp);
						// Cell Index for current run id
						int cellIndex = columnIndexForUniqueRunId.get(key);
						r.createCell(cellIndex).setCellValue(status);
					}
				}
			}
			if (keysize < 3) {
				row.getCell(4).setCellValue("Run3(NA)");
				sheetComparison.getRow(3).getCell(4).setCellValue("NA");
			}
			if (keysize < 2) {
				row.getCell(3).setCellValue("Run2(NA)");
				sheetComparison.getRow(3).getCell(3).setCellValue("NA");
			}
			if (keysize < 1) {
				row.getCell(2).setCellValue("Run1(NA)");
				sheetComparison.getRow(3).getCell(2).setCellValue("NA");
			}
			// Write Records
			workbook.write(fileOut);
			fileOut.flush();
			workbook.close();
			fileOut.close();
			LogUtil.infoLog(ReportFactoryDB.class, "Comparison sheet written successfully");
		} catch (Exception e1) {
			LogUtil.errorLog(ReportFactoryDB.class, "Exception caught", e1);
		}
	}// End writeToExcelFile()

	private static int findRow(Sheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						return row.getRowNum();
					}
				}
			}
		}
		return -1;
	}// End findRow()

	private static List<Integer> findRows(Sheet sheet, String cellContent) {
		List<Integer> rows = new ArrayList<>();
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						rows.add(row.getRowNum());
					}
				}
			}
		}
		return rows;
	}// End findRows()

	// Close database connection
	/**
	 * 
	 */
	public static void closeDBConnection() {
		try {
			if (cnxn != null) {
				cnxn.close();
				dbSwitch = Switch.OFF;
			}
		} catch (Exception ex) {
			LogUtil.errorLog(ReportFactoryDB.class, "Exception caught", ex);
		}
	}// End closeDBConnection()

}// End of class
