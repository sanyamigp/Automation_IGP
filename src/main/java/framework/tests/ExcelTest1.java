package framework.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;

import utilities.ExcelDataUtil;
import utilities.LogUtil;
import utilities.TestData;
import utilities.TestResults;

/**
 * This is excel test class
 *
 */
public class ExcelTest1 {
	String suiteName = "Suite1"; //Sheet Name
	String testCaseID = "IGP_TC_090";
	boolean isRun;
	static TestResults testesult;

	/**
	 * @throws Exception
	 */
	@Test()
	public void test1() throws Exception {

		
			LogUtil.infoLog(getClass(), "Test is Started and looging start");
			TestData testData = ExcelDataUtil.getTestDataWithTestCaseID(testCaseID);
			
			LogUtil.infoLog(getClass(), "ID: " +testData.getTestId());
			LogUtil.infoLog(getClass(), "Desc: " +testData.getTestDesc());
		
		} // End try
		

	

}
