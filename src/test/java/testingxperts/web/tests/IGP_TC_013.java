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
public class IGP_TC_013 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;

	@Test(
			testName="IGP_TC_013",
			groups={"Home"}, 
			description="Forgot Password:Ensure that user should be able to reset password."
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


			stepInfo="Click on user-menu icon.";
			logStep(stepInfo);
			clickAndWait(LoginPage.linkLogin);

			stepInfo="Click on Login with Email button.";
			logStep(stepInfo);
			click(LoginPage.btnLogin);
			
			stepInfo="Click on Forgot Password link.";
			logStep(stepInfo);
			verifyStep(LoginPage.forgotPassword(),stepInfo);


			String elementSShot=takeScreenshotWebElement(waitForVisibile(By.xpath(".//*[@id='r-pass']/div[2]/div")),"SignUpPage");
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
	}//End Test





}
