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
import testingxperts.web.pages.LoginPage;
import testingxperts.web.pages.OrderDetailsPage;
import testingxperts.web.pages.OrderSummaryPage;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_NewOrderSummary extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

	@Test(
			testName="IGP_TC_NewPayment",
			groups={"Order Summary"}, 
			description="Buying 3 items from different tabs viewing in Order Summary Page."

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
			
			stepInfo="Click on Add Cart for flowers and cakes.";
			logStep(stepInfo);
			CartPage.addItemInCartforFlowersnCakes(HomePage.GiftBy.FLOWER_AND_CAKE);
			verifyStep(CartPage.isItemAdded(), stepInfo);
			pause(3000);
			CartPage.closeCartOverlay();
			
			stepInfo="The page should navigate back to product description page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(),stepInfo);
			
			getDriver().navigate().back();
			getDriver().navigate().back();
			
			stepInfo="Click on Gift hampers.";
			CartPage.addItemInCartforGiftHampers(HomePage.GiftBy.GIFT_HAMPER);
			verifyStep(CartPage.isItemAdded(), stepInfo);
			pause(3000);
			CartPage.closeCartOverlay();
			
			getDriver().navigate().back();
			getDriver().navigate().back();
			
			stepInfo="Click on Personalized gifts.";
			CartPage.addItemInCartforPersonalization(HomePage.GiftBy.PERSONALIZED_GIFTS);
			verifyStep(CartPage.isItemAdded(), stepInfo);
			pause(3000);
			CartPage.closeCartOverlay();
			pause(3000);
			
			stepInfo="Buy Now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			
			stepInfo="Place order";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);
			
			stepInfo="Login at checkout page";
			logStep(stepInfo);
			CheckOutPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			verifyStep(DeliveryPage.verifyDeliveryPageLoaded(),
					stepInfo);
			
			stepInfo="Click Deliver here";
			executeStep(click(DeliveryPage.btnDeliverHere), stepInfo);
			
			stepInfo="Increasing item for personalized product";
			logStep(stepInfo);
			executeStep(OrderSummaryPage.IncreaseQty_Positions(1),stepInfo);
			
			
			
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath("//div[@class='row no-margin']")),"Order Detail");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);
			 
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
