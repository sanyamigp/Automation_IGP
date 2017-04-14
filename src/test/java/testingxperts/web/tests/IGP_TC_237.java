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
import testingxperts.web.pages.LoginPage;
import testingxperts.web.pages.OrderSummaryPage;
import testingxperts.web.pages.PageMenu;
import testingxperts.web.pages.PaymentPage;
import testingxperts.web.pages.PersonalizedGiftsPage;
import testingxperts.web.pages.PersonalizedGiftsPage.Recipient;
import testingxperts.web.pages.ProductDetailPage;
import testingxperts.web.pages.ProductList;
import testingxperts.web.pages.ProductStripPage;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_237 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_237",
			groups={"Product Strip"}, 
			description="Navigate to Product Strip page:User is able to see all the details on Product Strip  Page for same delivery gifts."
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
			

			
			stepInfo="Open Same Day Delivery Gifts Page.";
			logStep(stepInfo);
			ProductStripPage.openStripPage("same-day-delivery-gifts");
			verifyStep(ProductStripPage.isStripPageLoaded("Same Day Delivery Gifts"), stepInfo);
			
			
			stepInfo="Check that the following elements are present  as per the requirements:Header visible ";
			logStep(stepInfo);
			verifyStep(ProductList.headerVisible(),"Header is visible");
			
			stepInfo="Check that the following elements are present  as per the requirements:Product Strips visible";
			logStep(stepInfo);
			verifyStep(ProductStripPage.verifyProductStrips(), stepInfo);
			
			stepInfo="Check whether the products are coming for particular strips.";
			logStep(stepInfo);
			verifyStep(ProductStripPage.productsInProductStrips(), stepInfo);
			
			stepInfo="Check the presence of Back To Top button.";
			logStep(stepInfo);
			verifyStep(ProductStripPage.verifyBackToTopbutton(), stepInfo);
			
			stepInfo="Check that the following elements are present  as per the requirements:Footer visible";
			logStep(stepInfo);
			verifyStep(ProductStripPage.verifyTopFooter(), "Top Footer is visible");
			verifyStep(ProductList.verifyBottomFooter(), "Bottom Footer is visible");
			verifyStep(ProductList.verifyContentInFooter(), "Footer Content is visible");
			pause(3000);
			
		
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath("//div[@id='site-wrapper']")),"Product Strip page");
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
