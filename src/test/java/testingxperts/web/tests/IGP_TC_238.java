package testingxperts.web.tests;

import javax.enterprise.event.Reception;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CardListPage;
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
public class IGP_TC_238 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_238",
			groups={"Card List"}, 
			description="Navigate to Card List page:User is able to see all the details on Card List Page for Birthday."
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
			

			
			stepInfo=" Open Birthday Card Page.";
			logStep(stepInfo);
			ProductStripPage.openStripPage("birthday-gifts");
			verifyStep(ProductStripPage.isStripPageLoaded("Birthday Gifts"), stepInfo);
			
			
			stepInfo="Check that the following elements are present  as per the requirements:Header visible ";
			logStep(stepInfo);
			verifyStep(ProductList.headerVisible(),"Header is visible");
			
			stepInfo="Check that the following elements are present  as per the requirements:Selection Bar visible";
			logStep(stepInfo);
			verifyStep(ProductList.selectionbarItems(), "Selection Bar items are visible");
			
			stepInfo="Check the presence of Bithday Card";
			logStep(stepInfo);
			verifyStep(CardListPage.verifyCardsVisible(), stepInfo);
			
			stepInfo="Check all the headings present in the page.";
			logStep(stepInfo);
			verifyStep(CardListPage.verifyPageheadings(), stepInfo);
			
			stepInfo=" Check the images present for the particular headings.";
			logStep(stepInfo);
			verifyStep(CardListPage.verifyCardProductItems(), stepInfo);
			
			stepInfo="Check that the following elements are present  as per the requirements:Footer visible";
			logStep(stepInfo);
			verifyStep(ProductStripPage.verifyTopFooter(), "Top Footer is visible");
			verifyStep(ProductList.verifyBottomFooter(), "Bottom Footer is visible");
			verifyStep(ProductList.verifyContentInFooter(), "Footer Content is visible");
			pause(3000);
			
		
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath("//div[@id='site-wrapper']")),"Card Listing page");
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
