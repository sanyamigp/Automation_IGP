package testingxperts.web.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listeners.CustomListeners;
import listeners.ExecutionStartEndListner;
import testingxperts.web.pages.CartPage;
import testingxperts.web.pages.DeliveryPage;
import testingxperts.web.pages.HomePage;
import testingxperts.web.pages.LoginPage;
import testingxperts.web.pages.ProductList;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_101 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;


	@Test(
			testName="IGP_TC_101",
			groups={"Cart"}, 
			description="Add quantity:Ensure that user can increase the quantity of the product."
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

			stepInfo="Increase the quantity of the product using increment/decrement option.";
			logStep(stepInfo);
			int old_Qty=CartPage.get_Qty();
			pause(2000);
			verifyStep(CartPage.incrementOperator(),stepInfo);
			int new_qty=CartPage.get_Qty();

			verifyStep(new_qty>old_Qty, stepInfo);
			pause(2000);

			

			

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
