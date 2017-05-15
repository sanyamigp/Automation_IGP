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
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_099 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;


	@Test(
			testName="IGP_TC_099",
			groups={"Cart"}, 
			description="Place Order for Courier products:Ensure that the user is able to place order by clicking on 'Place order' button."
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
			HomePage.selectFashionNLifestyle();
			verifyStep(HomePage.selectItemEditorPick(1),stepInfo);

			stepInfo="Buy Now";
			logStep(stepInfo);
			CartPage.addProductInCart();
			verifyStep(CartPage.isItemAdded(), stepInfo);
			CartPage.closeCartOverlay();
			executeStep(CartPage.clikBuyNow(), stepInfo);
			
			stepInfo="The page should navigate to order details page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(),stepInfo);

			stepInfo="Click on 'place order' button.";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);

			stepInfo="The user should be navigated to delivery page.";
			verifyStep(DeliveryPage.verifyDeliveryPageLoaded(),
					stepInfo);
			
			getDriver().navigate().back();
			
			
			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath(".//*[@id='site-wrapper']")),"Cart page");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);


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
	}//End test


}
