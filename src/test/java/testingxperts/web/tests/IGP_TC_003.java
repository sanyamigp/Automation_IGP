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
public class IGP_TC_003 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_003",
			groups={"Sanity"}, 
			description="Check whether logged in user is able to make payment for international products."
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
			
			stepInfo="Login to application";
			logStep(stepInfo);
			LoginPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			verifyStep(LoginPage.isLogin(), stepInfo);

			stepInfo="Empty Cart";
			logStep(stepInfo);
			HomePage.clickOnCartLogo();
			verifyStep(HomePage.emptyCartItems(), stepInfo);
			CartPage.closeCartOverlay();
			
			stepInfo="Select any product other than Flowers and Cakes under Best Selling Category .";
			logStep(stepInfo);
			HomePage.selectHomeProduct();
			verifyStep(HomePage.selectItemEditorPick(3),stepInfo);
			
			stepInfo="Select International Shipping option";
			logStep(stepInfo);
			verifyStep(ProductDetailPage.clickinternationalshipping(), stepInfo);
			
			stepInfo="Enter country name in the textbox and then click on Check button.";
			logStep(stepInfo);
			verifyStep(ProductDetailPage.deliverychargePresent("Australia"), stepInfo);
			
			stepInfo="Buy Now";
			logStep(stepInfo);
			CartPage.clikBuyNow();

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
			
			stepInfo="Click Deliver here";
			logStep(stepInfo);
			verifyStep(DeliveryPage.selectCountryAddress("Australia"), stepInfo);
			
			stepInfo="Verify user navigated to Order Summary page";
			logStep(stepInfo);
			verifyStep(OrderSummaryPage.isOrderSummaryPageLoaded(),stepInfo);
			
			stepInfo="Verify click on apply coupen ";
			logStep(stepInfo);
			OrderSummaryPage.clickApplyCoupen();
			verifyStep(OrderSummaryPage.verifyCoupenInputFieldIsDisplayed(), stepInfo);
			
			stepInfo="Enter new coupen and verify: "+Constants.VALID_COUPEN;
			logStep(stepInfo);
			OrderSummaryPage.inputCoupenAndApply(Constants.VALID_COUPEN);
			verifyStep(OrderSummaryPage.verifyCoupenIsSuccess(Constants.VALID_COUPEN), stepInfo);
			
			stepInfo="Verify User should Navigate to Payment page";
			logStep(stepInfo);
			executeStep(click(OrderSummaryPage.btnPlaceOrder), "Click place order");
			verifyStep(PaymentPage.isPaymentPageLoaded(),stepInfo);
			
			
		
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.cssSelector(".payment-block")),"Sanity");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);
			
			 getDriver().navigate().back();
			 OrderSummaryPage.removeCoupon();
			
			 
						 
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
