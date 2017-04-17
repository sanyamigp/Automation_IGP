package suite.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import utilities.ExcelDataUtil;
import utilities.GlobalUtil;
import utilities.TestConfig;

/**
 * @author TX
 *
 */
public class CustomSuiteRunner {
	
	/**
	 * 
	 */
	@Test
	public static void suiteRunner() {
		
		/* This is Test runner Utility that run all the tests based on enabled suites and their enabled tests 
		 * ===========================================================
		 * 
		 *		PROCESS - 1 Get information from excel file
		 *	-----------------------------------------------
		 *	
		 *  1.  Read Common settings from excel file
		 *  	1. Application Type
		 *  	2. Environment(UAT,STAGING etc.)
		 *  	3. Browser
		 *  	4. Email output (Y/N)
		 *  			If Yes
		 *  			4-1. Email ID comma separated list
		 *  			4-2. HTML Report (Y/N)
		 *    			4.3. XLS Report (Y/N)
		 *    			4.4. Test Logs (Y/N)
		 *    
		 * 		5. Defect management integration
		 * 		6. Test management integration
		 * 
		 *  2.	Go to Control Sheet Read Suite Name and Information Information required to run suite -:
		 *			1. Suite Name (Only If it is Yes to run)
		 *			2. Suite Id (That is sheet name)
		 *			3. Browser Name
		 *			4. List of test cases that are enabled in Excel sheet
		 * 
		 * */
		
		// 1.  Read Common settings from excel file
		GlobalUtil.setCommonSettings(ExcelDataUtil.getCommonSettings());
		

		System.out.println("\n+===================================================+");
		System.out.println("Project name:\t\t\t" + GlobalUtil.getCommonSettings().getProjectName());
		System.out.println("Application type:\t\t" + GlobalUtil.getCommonSettings().getAppType());
		System.out.println("Environment(UAT,STAGING etc.):  " + GlobalUtil.getCommonSettings().getAppEnviornment());
		System.out.println("Email output(Y/N):\t\t" + GlobalUtil.getCommonSettings().getEmailOutput());
		System.out.println("+===================================================+");
		
		List<TestConfig> listOfTestConfigs = ExcelDataUtil.getSuitesToRun();
		
		//Create A XML Suites based on List of 
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		for (TestConfig tc : listOfTestConfigs)
		{
			//Create a suite with name from Excel
			XmlSuite suite = new XmlSuite();
			suite.setName(tc.getSuiteName());
			System.out.println("\nSuite name: " + tc.getSuiteName());		

			//Set Suite parameters
			Map<String,String> parameters = new HashMap<>();
			parameters.put("Browser", tc.getBrowserName());
			System.out.println("Browser name: " + tc.getBrowserName());
			
			//Set Suite Parameter		
			suite.setParameters(parameters);
			
			//Add a test name for Current suite
			XmlTest test = new XmlTest(suite);
			test.setName(tc.getSuiteName() +"-Tests");

			//Get Package where all tests resides
			XmlPackage xmlpkg = new XmlPackage("testingxperts.web.tests");

			//Get All classes from that package
			List<XmlClass> testClasses = new ArrayList<XmlClass>();
			//Get all the test classes from package
			testClasses=xmlpkg.getXmlClasses();
			
			
			//Create a List of valid test classes as per excel test list
			List<XmlClass> validtestClasses = new ArrayList<XmlClass>();
			
			for(XmlClass xmlTestClass : testClasses){
				
				//Get a List of Valid test
				for (String testName : tc.getTestsList()){
					
					if(xmlTestClass.getName().contains(testName))
					{
						validtestClasses.add(xmlTestClass);
					}
										
				}//End for Test list 
				
			}//End for test class list
			
			
			//Set all classes for test 
			test.setXmlClasses(validtestClasses) ;

			System.out.println("Suite Id: " + tc.getSuiteId());
			
			
			Iterator<String> tcIt = tc.getTestsList().iterator();
			
			System.out.println("Enabled Tests # "+tc.getTestsList().size() );
			
			while (tcIt.hasNext())
			{
				String testId = (String) tcIt.next();
				System.out.print(" |" +testId + "|");
				
			}
			System.out.println("\n---------------------------------------------------------");
			//suite.addTest(test);
			
			suites.add(suite);
			
			
		}//End Loop
		
		System.out.println("Suite files are created.....");
		System.out.println("Total suites are : "+suites.size() );
		GlobalUtil.setTotalSuites(suites.size());
		
		System.out.println("Suite names: ");
		for(XmlSuite mysuite:suites){
			
			System.out.print(" |" +mysuite.getName()+"|");
			
			
		}
		
		//Run TestNg Suites
		
		TestNG testNgRun = new TestNG();
		//Set suite
		testNgRun.setXmlSuites(suites);
		
		testNgRun.initializeSuitesAndJarFile();
		
		//testNgRun.setParallel(XmlSuite.ParallelMode.CLASSES);
		
		//Run 
		testNgRun.run();
		
		
		
		
	}

}
