package testingxperts.web.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.CheckOutPage;
import testingxperts.web.pages.Constants;
import testingxperts.web.pages.DeliveryPage;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.OrderSummaryPage;
import testingxperts.web.pages.PaymentPage;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_179 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_179",
			groups={"Checkout Page"}, 
			description="Sign Up:Ensure that a new user is able to sign in."
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
			
			stepInfo="Add product into cart.";
			logStep(stepInfo);
			CartPage.addItemInCart(HomePage.GiftBy.FLOWER_AND_CAKE);
			verifyStep(CartPage.isItemAdded(), stepInfo);
			CartPage.closeCartOverlay();
			
			
			stepInfo="Buy Now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			
			stepInfo="The page should navigate to cart page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(),stepInfo);
			
			stepInfo="Place order";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);
			
			stepInfo="The user should be navigated to checkout page.";
			verifyStep(CheckOutPage.isCheckOutPageLoaded(),
					stepInfo);
			
			stepInfo="Click on New User? Sign Up link.";
			logStep(stepInfo);
			verifyStep(CheckOutPage.verifySignInLink(), stepInfo);
			
			stepInfo="Enter the valid user details.";
			logStep(stepInfo);
			verifyStep(CheckOutPage.signUpDetails("sanyam123", "Mr.", "IGP", "Automation", "USA"),stepInfo);
			
				
			
			
			
			
			 
			//.........Script Start...........................
		}
		  catch (Exception e){
			   if(retryCount>0)
			   {
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID()+"_"+ retryingNumber,"Automation Bugs: "+stepInfo);

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
				   String imagePath = takeScreenshot(getDriver(), getTestCaseID(),"Automation Bugs: "+stepInfo);
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
