package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ExcelDateUtil class is refer to read and write in excel
 *
 */
public class ExcelDataUtil {
	
	private static FileInputStream fs = null;
	private static Workbook workbook = null;
	private static Sheet sheet=null;

	private static int columnToLookTestCaseID = Integer.parseInt(ConfigReader.getValue("columnToLookTestCaseID"));
	private static String testDatafilePath = ConfigReader.getValue("testDataExcelPath");
	private static String testDataSheetName = ConfigReader.getValue("testDataSheet");
	private static boolean isCopyTemplate = false;
	private static String filePath = "";
	protected static List<String> testsList = new ArrayList<>();
	private static String excelextensionxlsx = ".xlsx";
	public static final String TESTRESULTSHEET = "testResultSheet";
	public static final String Y = "Y";
	public static final String EXCEPTIONCAUGHT = "Exception caught";
	private static String excelextensionxls = ".xls";
	private static String automationcontrolexcelpath = "AutomationControlExcelPath";
	private static final String INVALID_SHEET_MESSAGE="Error! No such sheet available in Excel file";

	/**
	 * <H1>Excel initialize</H1>
	 * 
	 * @param filePath
	 * @param sheetName
	 */
	public static void init(String filePath, String sheetName) {
		String fileExtensionName = filePath.substring(filePath.indexOf('.'));
		try {
			fs = new FileInputStream(filePath);
			if (fileExtensionName.equals(excelextensionxlsx)) {
				// If it is xlsx file then create object of XSSFWorkbook class
				workbook = new XSSFWorkbook(fs);
			}
			// Check condition if the file is xls file
			else if (fileExtensionName.equals(excelextensionxls)) {
				// If it is xls file then create object of XSSFWorkbook class
				workbook = new HSSFWorkbook(fs);
			}
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
		}

	}

	/**
	 * <H1>Get test data with test case id</H1>
	 * 
	 * @param testCaseID
	 * @return
	 */
	public static TestData getTestDataWithTestCaseID(String testCaseID) {
		boolean found = false;
		TestData testdata = new TestData();
		// Initialize class
		// Get Path and Sheet Name from Property File
		init(testDatafilePath, testDataSheetName);
		Iterator<Row> rowIterator = sheet.iterator();
		try {
			while (rowIterator.hasNext()) {
				Row row =  rowIterator.next();
				if (row.getCell(columnToLookTestCaseID).getStringCellValue().equalsIgnoreCase(testCaseID)) {
					ArrayList<String> currentRowData = new ArrayList<>();
					found = true;
					row.forEach(cell->
							currentRowData.add(""+cell)
					);
					
					testdata.setSuiteName(currentRowData.get(0));
					testdata.setTestId(currentRowData.get(1));
					testdata.setTestDesc(currentRowData.get(2));
					testdata.setComplexity(currentRowData.get(3));
					testdata.setExpectedTime(Double.parseDouble(currentRowData.get(4)));

					break;
				} // End if Found an row

			} //// Row Iterator

			fs.close();

		} catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, "caught exception", e);
			
		}

		if (!found)
			LogUtil.infoLog(ExcelDataUtil.class, "No data found with given key-> " + testCaseID);

		return testdata;

	}// End of getSheetData
	

	/**
	 * <H1>Get copy of template file</H1>
	 * 
	 * @exception @return
	 */
	public static File getCopyOfTemplateFile() {

		// Copy a fresh Result ExcelFile from Master
		File dest = null;
		File source = null;
		try {
			String workingDir = System.getProperty("user.dir");
			String sourcefilepath = ConfigReader.getValue("Template_testResultExcelPath");
			source = new File(sourcefilepath);
			String fileName = "";

			String fileExtensionName = sourcefilepath.substring(sourcefilepath.indexOf('.'));

			if (fileExtensionName.equals(excelextensionxlsx)) {
				// If it is xlsx file then create object of XSSFWorkbook class
				fileName = "TestResult" + excelextensionxlsx;
			}

			// Check condition if the file is xls file
			else if (fileExtensionName.equals(excelextensionxls)) {
				// If it is xls file then create object of XSSFWorkbook class
				fileName = "TestResult" + excelextensionxls;
			}
			String destfilepath = workingDir + "\\ExecutionReports\\ExcelReport\\" + fileName;
			dest = new File(destfilepath);
			FileUtils.copyFile(source, dest);
			return dest;
		} catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
		}
		return dest;
	}

	/**
	 * <H1>Updating test results</H1>
	 * 
	 * @param testData
	 * @param testResult
	 */
	public static void updateTestResults(TestData testData, TestResults testResult) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// Now format the date
		String executionDate = dateFormat.format(date);

		
		
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		FileOutputStream fos = null;
		String sheetName;
		
		if (!isCopyTemplate) {
			// This method will get the Result Excel file
			filePath = getCopyOfTemplateFile().getAbsolutePath();
			isCopyTemplate = true;
		}

		try(FileInputStream fis= new FileInputStream(filePath);
				Workbook wb= WorkbookFactory.create(fis);) {
						
			String fileExt = FilenameUtils.getExtension(filePath);
			
			

			// Create Excel workbook based on file type
						sheetName = ConfigReader.getValue(TESTRESULTSHEET);
			
			
						
			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				LogUtil.errorLog(ExcelDataUtil.class, INVALID_SHEET_MESSAGE + sheetName);
				throw new InvalidSheetException(sheetName +" No such sheet available");
			}
			
			sheet = wb.getSheet(sheetName);
			fos = new FileOutputStream(filePath);
			int startRow;
			LogUtil.infoLog(ExcelDataUtil.class, ConfigReader.getValue(TESTRESULTSHEET));
			startRow = sheet.getLastRowNum();
			startRow++;

			// Fill in a row of Result Set
			// Set test suite name
			sheet.createRow(startRow).createCell(0).setCellValue(testData.getSuiteName());

			// Set Test ID
			sheet.getRow(startRow).createCell(1).setCellValue(testData.getTestId());

			// Set Test description
			sheet.getRow(startRow).createCell(2).setCellValue(testData.getTestDesc());

			// Set Enviornment info
			sheet.getRow(startRow).createCell(3).setCellValue(testData.getTestPlatformInfo());

			// Set Date of Excecution
			sheet.getRow(startRow).createCell(4).setCellValue(executionDate);

			// Set Complexity
			sheet.getRow(startRow).createCell(5).setCellValue(testData.getComplexity());

			// Set Status of test
			sheet.getRow(startRow).createCell(6).setCellValue(testResult.getStatus());

			// Set Expected time
			sheet.getRow(startRow).createCell(7).setCellValue(testData.getExpectedTime());

			// Set total time taken
			sheet.getRow(startRow).createCell(8).setCellValue(testResult.getActualTime());

			// Set remarks
			sheet.getRow(startRow).createCell(9).setCellValue(testResult.getRemarks());

			// Set Screenshot ref
			sheet.getRow(startRow).createCell(10).setCellValue(testResult.getScreenshotref());

			fos.flush();
			wb.write(fos);

			wb.close();
			fis.close();
			fos.close();
		} catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);

		}
	}

	/**
	 * <H1>Get common settings</H1>
	 * 
	 * @return
	 */
	public static CommonSettings getCommonSettings() {
		// 1. Read Excel File
		CommonSettings commonSettings = new CommonSettings();

		String sheetName = ConfigReader.getValue("AutomationControlSheet");
		
		
		try(FileInputStream fis =new FileInputStream(ConfigReader.getValue(automationcontrolexcelpath)); 
				Workbook wb = WorkbookFactory.create(fis);) {
			
			

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				LogUtil.infoLog(ExcelDataUtil.class, INVALID_SHEET_MESSAGE + sheetName);
				
			}

			Sheet sheet = wb.getSheet(sheetName);

			// Set Project name from Column B1
			commonSettings.setProjectName(sheet.getRow(0).getCell(1).getStringCellValue());

			// Set Fixed Common Settings

			// 1. Set Application type Column[B17] Row =16, Column =1
			String val = sheet.getRow(16).getCell(1).getStringCellValue();
			commonSettings.setAppType(val);

			// 2. Set Application environment type Column[B18] Row =18, Column
			// =1
			val = sheet.getRow(17).getCell(1).getStringCellValue();
			commonSettings.setAppEnviornment(val);

			// 3. Set Email output(Y/N) Column[B20] Row =19, Column =1
			val = sheet.getRow(19).getCell(1).getStringCellValue();
			commonSettings.setEmailOutput(val);

			// 4. Set Email Id Comma List Column[B25] Row =24, Column =1
			val = sheet.getRow(24).getCell(1).getStringCellValue();
			commonSettings.setEmailIds(val);

			// 5. Set Html report (Y/N) Column[B26] Row =25, Column =1
			val = sheet.getRow(25).getCell(1).getStringCellValue();
			commonSettings.setHtmlReport(val);

			// 6. Set XLS report (Y/N) Column[B27] Row =26, Column =1
			val = sheet.getRow(26).getCell(1).getStringCellValue();
			commonSettings.setXlsReport(val);

			// 7. Set Test Logs (Y/N) Column[B28] Row =27, Column =1
			val = sheet.getRow(27).getCell(1).getStringCellValue();
			commonSettings.setTestLogs(val);
			
		} // End try
		catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);

		}  
		return commonSettings;

	}

	/**
	 * <H1>Get suiterun flag</H1>
	 * 
	 * @return
	 */
	public static List<TestConfig> getSuitesToRun() {
		// 1. Read Excel File
		List<TestConfig> listTestConfigs = new ArrayList<>();

		int startRowNum = ConfigReader.getIntValue("rowToStart");
		int columnToLookFlag = ConfigReader.getIntValue("columnToLookFlag");
		String sheetName = ConfigReader.getValue("AutomationControlSheet");

		
		
		try (FileInputStream fis=new FileInputStream(ConfigReader.getValue(automationcontrolexcelpath));Workbook wb=WorkbookFactory.create(fis);){

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				LogUtil.infoLog(ExcelDataUtil.class, INVALID_SHEET_MESSAGE + sheetName);
				
			}

			Sheet sheet = wb.getSheet(sheetName);

			while (true) {
				if (sheet.getRow(startRowNum) == null || "".equalsIgnoreCase(sheet.getRow(startRowNum).getCell(columnToLookFlag).getStringCellValue()) )
					break;
				else {
					// System.out.println("YN field "

					if (sheet.getRow(startRowNum).getCell(columnToLookFlag).getStringCellValue()
							.equalsIgnoreCase(Y)) {

						TestConfig testConfigs = new TestConfig();
						testConfigs.setSuiteName(sheet.getRow(startRowNum).getCell(0).getStringCellValue());
						testConfigs.setBrowserName(sheet.getRow(startRowNum).getCell(2).getStringCellValue());
						testConfigs.setSuiteId(sheet.getRow(startRowNum).getCell(3).getStringCellValue());

						if (testConfigs.getSuiteId().isEmpty())
							break;

						// Get Test List based on Suite Id that is enabled (Y)

						if (wb.getSheetIndex(
								wb.getSheet(sheet.getRow(startRowNum).getCell(3).getStringCellValue())) == -1) {
							LogUtil.infoLog(ExcelDataUtil.class,INVALID_SHEET_MESSAGE + testConfigs.getSuiteId());
							throw new InvalidSheetException(INVALID_SHEET_MESSAGE);
						
						}

						Sheet suiteSheet = wb.getSheet(testConfigs.getSuiteId());

						// Loop Through Rows and get All enabled tests
						Iterator<Row> rowItr = suiteSheet.rowIterator();
						List<String> testsList = new ArrayList<>();

						while (rowItr.hasNext()) {
							Row r =  rowItr.next();

							if (r.getCell(1).getStringCellValue().equalsIgnoreCase(Y)) {
								// If Any Test has Yes Flag Then add to List
								String testId;
								testId = r.getCell(0).getStringCellValue();
								testsList.add(testId);
							}

						} // End while

						// Add TestId to testConfig
						testConfigs.setTestsList(testsList);

						// Add testConfig To Lis of configs
						listTestConfigs.add(testConfigs);

					}
				}
				// Move to next Row
				startRowNum++;
			} // End while

		} // End try
		catch (Exception e) {
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);

		} 
		return listTestConfigs;

	}

	/**
	 * <H1>Get Flag from excel</H1>
	 * @param sheetName
	 * @param searchValue
	 * @return
	 */
	public static String getFlagExcel(String sheetName, String searchValue) {
		int sheetSize = 0; 
		int rowNum = 1;
		String strVal = "";
		String strflag = "NA";
		try(FileInputStream fis = new FileInputStream(ConfigReader.getValue(automationcontrolexcelpath));
				Workbook wb = WorkbookFactory.create(fis);	) {
			
			

			if (wb.getSheetIndex(wb.getSheet(sheetName)) == -1) {
				LogUtil.infoLog(ExcelDataUtil.class, INVALID_SHEET_MESSAGE + sheetName);
				throw new InvalidSheetException("No such sheet available in Excel file: " + sheetName);
			}

			Sheet sheet = wb.getSheet(sheetName);
			sheetSize = sheet.getLastRowNum();

			for (int i = rowNum; i <= sheetSize; i++) {

				strVal = sheet.getRow(i).getCell(0).getStringCellValue();

				if (searchValue.equalsIgnoreCase(strVal)) {
					strflag = wb.getSheet(sheetName).getRow(i).getCell(1).getStringCellValue();
					break;
				}
			}
		
				 
		} catch (Exception e) {
			strflag = "NA";
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
		}

		return strflag;
	}

	// Read Data from Excel File AutomationControlSheet.xls(SuiteControlSheet),
	// Reading Y/N for including a test case in suite to run.
	/**
	 * <H1>Read Data from Excel File AutomationControlSheet.xls(SuiteControlSheet)</H1>
	 * @param suiteName
	 * @return
	 */
	public static boolean isSuiteRunnable(String suiteName) {
		boolean isRunnable = false;
		String strVal;
		try {
			strVal = getFlagExcel("SuiteControlSheet", suiteName);
			if (strVal.equalsIgnoreCase(Y)) {
				isRunnable = true;
			} else {
				isRunnable = false;
			}
		} catch (Exception e) {
			isRunnable = false;
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
		}

		return isRunnable;
	}

	// Read Excel file for Script to run. Like Regression, Smoke, Functional
	/**
	 * <H1>Read Excel file for Script to run. Like Regression, Smoke, Functional</H1>
	 * @param suiteName
	 * @param scriptName
	 * @return
	 */
	public static boolean isScriptRunnable(String suiteName, String scriptName) {
		boolean isRunnable = false;
		String strVal = null;
		try {
			strVal = getFlagExcel(suiteName, scriptName);
			if (strVal.equalsIgnoreCase(Y)) {
				isRunnable = true;
			} else {
				isRunnable = false;
			}
		} catch (Exception e) {
			isRunnable = false;
			LogUtil.errorLog(ExcelDataUtil.class, EXCEPTIONCAUGHT, e);
		}
		return isRunnable;
	}
	/**
	 * <H1>Print test status</H1>
	 * @param suiteName
	 * @param searchValue
	 * @param testStatus
	 * @exception
	 */
	
	/**
	 * <H1>Print test status</H1>
	 * @param suiteName
	 * @param testCaseID
	 * @exception
	 */
	public static boolean getControls(String suiteName, String testCaseID) {

		if (suiteName.trim().isEmpty()) {
			LogUtil.infoLog(ExcelDataUtil.class, "Suite name not found!!!");
			return false;
		}

		if (testCaseID.trim().isEmpty()) {
			LogUtil.infoLog(ExcelDataUtil.class, "Test name is not specified!!!");
			return false;
		}

		return ExcelDataUtil.isScriptRunnable(suiteName.trim(), testCaseID.trim());
	}

	// Get browsers List
	/**
	 * <H1>Get browsers List</H1>
	 * @exception
	 * @return
	 */
	
		
	
	
	
}// End Excel class

class InvalidSheetException extends Exception{  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	InvalidSheetException(String s){  
	  super(s);  
	 }  
	}  
