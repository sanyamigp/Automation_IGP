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
public class IGP_TC_010 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_010",
			groups={"Sanity"}, 
			description="Check whether a new user is able to Sign Up."
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
			
			
			stepInfo="Check whether Gifts For Women Page is opened.";
			logStep(stepInfo);
			HomePage.selectOptionFromBirthday(3);
			verifyStep(ProductList.isAnniversaryPageLoaded(), stepInfo);
			
			stepInfo="Click on any Item";
			logStep(stepInfo);
			verifyStep(CardListPage.selectCardItems(),stepInfo);
			
			stepInfo="Enter valid pincode.";
			logStep(stepInfo);
			verifyStep(ProductDetailPage.isPincodeCorrect(), stepInfo);
			pause(3000);
			
			stepInfo="Buy Now";
			logStep(stepInfo);
			CartPage.clikBuyNow();
			pause(3000);
			
			stepInfo="The page should navigate to cart page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(),stepInfo);
			
			
			
			stepInfo="Place order";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);
			
			stepInfo="The user should be navigated to checkout page.";
			logStep(stepInfo);
			verifyStep(CheckOutPage.isCheckOutPageLoaded(),
					stepInfo);
			
			stepInfo="Click on New User? Sign Up link.";
			logStep(stepInfo);
			verifyStep(CheckOutPage.verifySignInLink(), stepInfo);
			
			stepInfo="Enter the valid user details.";
			logStep(stepInfo);
			verifyStep(CheckOutPage.signUpDetails("austria@gmail.com", "ashish", "Mr.", "IGP", "Automation", "Austria", "12121"),stepInfo);
			
		
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
