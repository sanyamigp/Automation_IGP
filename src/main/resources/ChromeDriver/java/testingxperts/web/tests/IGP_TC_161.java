package testingxperts.web.tests;

import java.util.zip.CheckedOutputStream;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.CheckOutPage;
import testingxperts.web.pages.DeliveryPage;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.LoginPage;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_161 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_161",
			groups={"Checkout Page"}, 
			description="Checkout page steps: Ensure that ‘Checkout’ page should contain 4 mandatory steps that are : Login, Delivery Information, Order summary and Payment."
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
			
			stepInfo="Checkout page should contains-> Login, Delivery Information, Order Summary, and Payment tabs";
			logStep(stepInfo);
			verifyStep(CheckOutPage.verifyHeaderTabs(), stepInfo);
			
			
			stepInfo="Login at checkout page";
			logStep(stepInfo);
			CheckOutPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			verifyStep(DeliveryPage.verifyDeliveryPageLoaded(),
					stepInfo);
			
			
			 getDriver().navigate().back();
			 getDriver().navigate().back();
			 
		
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
