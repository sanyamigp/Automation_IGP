package testingxperts.web.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners_RestTest;
import listeners.ExecutionStartEndListner;
import utilities.ExcelDataUtil;
import utilities.GlobalUtil;
import utilities.IGP_Url;
import utilities.KeywordUtil;
import utilities.LogUtil;
import utilities.Webservices;

@Listeners({CustomListeners_RestTest.class,ExecutionStartEndListner.class })
public class IGP_TC_URL extends KeywordUtil{
	String stepInfo="";

	@Test(
			alwaysRun=true,
			testName="IGP_TC_URL",
			groups={"Cart"}, 
			description="Validate urls"
			)
	public void test() throws Throwable {
		try{
			boolean testStaus=true;
			setTestCaseID(getClass().getSimpleName());
			//======================BASIC SETTING FOR TEST==========================================================
				initTest();
			//================== END BASIC SETTING ============================================================
			
			//.........Script Start...........................
			//Get Url List from Excel
				
			stepInfo="Test list of URL";
			List<IGP_Url> igpUrls = ExcelDataUtil.getUrlList();
			
			for(IGP_Url igpUrl:igpUrls){
				//IgpUrl properties
				// 1 urlDesc - 2. url - 3. executionFlag - 4. requiredStatus
				if(igpUrl.getExecutionFlag().equalsIgnoreCase("Y")){
					
					logStep("| "+igpUrl.getUrlDesc() +" | "+ igpUrl.getUrl()+" | Expected status: "+ igpUrl.getRequiredStatus() +" |");
					try {
						int statusCode = Webservices.getStatusCode(igpUrl.getUrl());
						if(statusCode==igpUrl.getRequiredStatus()){
							logStepPass(igpUrl.getUrlDesc() +" Actual status: " +statusCode);
						}else{
							logStepFail(igpUrl.getUrlDesc()+" Actual status: " +statusCode);
							testStaus=false;
						}
					} catch (Exception e) {
						LogUtil.errorLog("URL_TEST", igpUrl.getUrlDesc() +"--" +igpUrl.getUrl()+" -- Has error->  " +e.getMessage());
						testStaus=false;
						throw e;
					} 
				}
			
				
			}
			
			if(!testStaus){
				throw new TestException("Test has failed");
			}
			
			//.........Script Start...........................
		}
		  catch (Exception e)
			   {
				   logStepFail(stepInfo);
				   logStepError(e.getMessage());
				   GlobalUtil.setTestException(e);
				   throw e;
			   }
}
}
