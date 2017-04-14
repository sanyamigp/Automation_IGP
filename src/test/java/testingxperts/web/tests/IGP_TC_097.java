package testingxperts.web.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.Constants;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.OrderDetailsPage;
import testingxperts.web.pages.HomePage.GiftBy;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_097 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

	@Test(
			testName="IGP_TC_097",
			groups={"Cart"}, 
			description="Place order with not deliverable pin code:Ensure that place order should not proceed when user provides not deliverable pin code."
			)
	public void test() throws Throwable {
		try{
			setTestCaseID(getClass().getSimpleName());
			//======================BASIC SETTING FOR TEST==========================================================
			if(retryingNumber==1)
				initTest();
			//================== END BASIC SETTING ============================================================
			/*
				How to Test steps
			 		1. Define step info
			 		2. Log to report and Logger
			 		3. Perform Action
			 		4. Verify Action
			 		
			 		==================================
			 		Login Steps
			 		==================================
			 			stepInfo="Login to application";
						logStep(stepInfo);
						LoginPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
						verifyStep(LoginPage.isLogin(), stepInfo);
					==================================
			 		Logout Steps
			 		==================================
		 				stepInfo="Logout from application";
		 				logStep(stepInfo);
		 				LoginPage.logOut();
		 				verifyStep(LoginPage.isLogout(), stepInfo);
		 	
			*/
			
		
			//.........Script Start...........................
			
			stepInfo="Open home page";
			logStep(stepInfo);
			HomePage.openHomePage();
			verifyStep(HomePage.isHomePageOpened(), stepInfo);
			
			
			stepInfo="Select Item to buy";
			logStep(stepInfo);
			executeStep(click(CartPage.linkFlower_and_cake), "Click:"+GiftBy.FLOWER_AND_CAKE.toString());
			executeStep(CartPage.selectItem(1),
					"Select Item");
			waitForClickable(CartPage.btnAddToCart);
			
			
			stepInfo="Enter invalid Pin code and validate";
			logStep(stepInfo);
			CartPage.inputPinCode(Constants.INVALID_PINCODE);
			CartPage.checkPinCode();
			logStep("Invalid Pin code message: " + CartPage.getPinCodeErrorMessage());
			verifyStep(CartPage.verifyPinCodeMessage(),
					stepInfo);
			
			
			stepInfo="Click Buy now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);

			
			stepInfo="Page should not navigate to further process.";
			logStep(stepInfo);
			verifyStep(CartPage.verifyPinCodeMessage(),
					stepInfo);
			
			
			
			//.........Script Start...........................
		}
		  catch (Exception e){
			   if(retryCount>0)
			   {
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID()+"_"+ retryingNumber);

				   logStepFail(stepInfo+" - "+KeywordUtil.lastAction);
				   logStepError(e.getMessage());
				   HtmlReportUtil.attachScreenshot(imagePath,false);
			    
				   GlobalUtil.getTestResult().setScreenshotref(imagePath);
			    
				   HtmlReportUtil.stepInfo("Trying to Rerun" + " "+getTestCaseID() +" for " + retryingNumber + " time");
				   retryCount--;
				   retryingNumber++;
				   utilities.LogUtil.infoLog(getClass(), "****************Waiting for " + getIntValue("retryDelayTime") +" Secs before retrying.***********");
				   delay(getIntValue("retryDelayTime"));
			    //Rerun same test
				   test();
			   }
			   else{
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID());
				   logStepFail(stepInfo+" - "+KeywordUtil.lastAction);
				   logStepError(e.getMessage());
				   HtmlReportUtil.attachScreenshot(imagePath,false);
			    
				   GlobalUtil.getTestResult().setScreenshotref(imagePath);
				   GlobalUtil.setTestException(e);
				   throw e;
			   }
		  }
}//End Test
	
	 
	
	
	
}
