package testingxperts.web.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import bsh.org.objectweb.asm.Constants;
import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.CountriesPage;
import testingxperts.web.pages.DeliveryPage;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.LoginPage;
import testingxperts.web.pages.ProductList;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_256 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;


	@Test(
			testName="IGP_TC_256",
			groups={"Cart"}, 
			description="Delivery supported in India: Ensure that the user experiences a warning message when delivery is not supported in places other than India."
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

			stepInfo="Add product into cart.";
			logStep(stepInfo);
			CartPage.addItemInCart(HomePage.GiftBy.FLOWER_AND_CAKE);
			verifyStep(CartPage.isItemAdded(), stepInfo);
			CartPage.closeCartOverlay();


			stepInfo="Click Buy now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			pause(2000);

			stepInfo="Go to HomePage.";
			logStep(stepInfo);
			HomePage.openHomePage();
			pause(2000);

			stepInfo="Click on 'Free Shipping to USA' image from Homepage.";
			logStep(stepInfo);
			verifyStep(HomePage.freeShippingtoUSA(),stepInfo);
			executeStep(ProductList.isUSAPageLoaded(), stepInfo);

			stepInfo="Select any product from Product Listing page.";
			logStep(stepInfo);
			verifyStep(ProductList.selectItem(2), stepInfo);
			
			stepInfo="Click Buy now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			pause(2000);
			
			stepInfo="Go to HomePage.";
			logStep(stepInfo);
			HomePage.openHomePage();
			pause(2000);
			
			stepInfo="Click on 'Send Gifts Worldwide' from Homepage.";
			logStep(stepInfo);
			verifyStep(HomePage.sendgiftsWorldwide(),stepInfo);
			CountriesPage.isCountriesPageLoaded();
			
			stepInfo="Click on any name of the City/Country gifts. ";
			logStep(stepInfo);
			verifyStep(CountriesPage.verifyCountryentered(),stepInfo);
			pause(3000);
			
			stepInfo="Select the item. ";
			logStep(stepInfo);
			verifyStep(CountriesPage.freeShippingProducts(1), stepInfo);
			
			stepInfo="Click Buy now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			pause(5000);
			
			stepInfo="Choose Country as USA.";
			logStep(stepInfo);
			CartPage.enterPincodesuccessfully(testingxperts.web.pages.Constants.PINCODE);
			verifyStep(CartPage.verifyErrorforInternational(), stepInfo);

			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath(".//*[@id='site-wrapper']")),"Cart page");
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
	}//End test


}
