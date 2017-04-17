package testingxperts.web.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners_RestTest;
import utilities.GlobalUtil;
import utilities.KeywordUtil;
import utilities.Webservices;

@Listeners({CustomListeners_RestTest.class})
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
			
			stepInfo="Test list of URL";
			
			
			
			
			List<String> urlList = new ArrayList<>();
			https://igp.com/
			urlList.add("https://google.com/");
			urlList.add("https://yahoo.com/");
			urlList.add("https://igp.com");
			
						
			for(String url : urlList){
				int statusCode;
				try {
					statusCode = Webservices.getStatusCode(url);
					System.out.println(statusCode);
					
					logStep(url +" - Status code: " + statusCode);
					if(statusCode==200){
					logStepPass("Verified url");	
					}else{
						logStepFail("Verified url");
						testStaus=false;
					}
					
					
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					throw e;
				}
					
				
			}//End for loop
			
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
