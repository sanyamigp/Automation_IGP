package testingxperts.web.tests;

import javax.enterprise.event.Reception;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.CheckOutPage;
import testingxperts.web.pages.Constants;
import testingxperts.web.pages.CountriesPage;
import testingxperts.web.pages.DeliveryPage;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.HomePage.GiftBy;
import testingxperts.web.pages.LoginPage;
import testingxperts.web.pages.OrderSummaryPage;
import testingxperts.web.pages.PageMenu;
import testingxperts.web.pages.PaymentPage;
import testingxperts.web.pages.PersonalizedGiftsPage;
import testingxperts.web.pages.PersonalizedGiftsPage.Recipient;
import testingxperts.web.pages.ProductDetailPage;
import testingxperts.web.pages.ProductList;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_113 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_113",
			groups={"Product Description page."}, 
			description="Personalized Now-:Ensure that user should not navigate to the next page with out completing all the steps in 'personalize now'."
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
			*/
		
			//.........Script Start...........................
			
			stepInfo="Open home page";
			logStep(stepInfo);
			HomePage.openHomePage();
			verifyStep(HomePage.isHomePageOpened(), stepInfo);
			
			stepInfo="Select personalized gift category from Homepage.";
			logStep(stepInfo);
			verifyStep(HomePage.personalisedGifts(),stepInfo);
			
			verifyStep(PersonalizedGiftsPage.isPersonalizedGiftsPageOpened(),"Verify Personalized Gifts Page is loaded");
			stepInfo="Select Item from personalize items for Woman";
			logStep(stepInfo);
			PersonalizedGiftsPage.selectItmeByRecipient(Recipient.WOMEN, 1);
			
			stepInfo="Verify PERSONALIZE NOW button is present";
			logStep(stepInfo);
			verifyStep(isWebElementVisible(ProductDetailPage.btnPersonalizeNow),stepInfo);
			pause(2000);
			
			stepInfo="Personalization options should display by clicking on PERSONALIZE NOW";
			logStep(stepInfo);
			executeStep(click(ProductDetailPage.btnPersonalizeNow),"Click PERSONALIZE NOW");
			verifyStep(PersonalizedGiftsPage.isPersonalizedGiftsOptionsOpened(),stepInfo);
			pause(2000);
			
			
			stepInfo="Input message";
			logStep(stepInfo);

			executeStep(PersonalizedGiftsPage.attachPersonalizedImage(), stepInfo);
			executeStep(PersonalizedGiftsPage.enterPersonalizedText("Gift Hampers"), stepInfo);
			executeStep(click(PersonalizedGiftsPage.btnDone),"Click Done");
			
			stepInfo="Personalized tab should be changed to 'Edit personalization'";
			logStep(stepInfo);
			verifyStep(isWebElementVisible(PersonalizedGiftsPage.btnEditPersonalize),stepInfo);
						
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath(".//*[@id='site-wrapper']/section[1]/div[2]/div")),"Product Desc. page");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);
						 
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
