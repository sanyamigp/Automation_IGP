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
import testingxperts.web.pages.OrderSummaryPage;
import testingxperts.web.pages.PaymentPage;
import testingxperts.web.pages.PaymentPage.Bank;
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({ CustomListeners.class, ExecutionStartEndListner.class })
public class IGP_TC_212 extends KeywordUtil {
	String stepInfo = "";
	int retryCount = getIntValue("retryCount");
	static int retryingNumber = 1;

	@Test(testName = "IGP_TC_212", 
			groups = {"Payment" }, 
			description = "Net Banking: Another bank drop down- : Ensure that The ‘selected popular bank' name should be reflected in 'select another bank' dropdown box.")
	public void test() throws Throwable {
		try {
			setTestCaseID(getClass().getSimpleName());
			// ======================BASIC SETTING FOR
			// TEST==========================================================
			if (retryingNumber == 1)
				initTest();
			// ================== END BASIC SETTING
			// ============================================================
			/*
			 * How to Test steps 1. Define step info 2. Log to report and Logger
			 * 3. Perform Action 4. Verify Action
			 * 
			 */

			// .........Script Start...........................

			stepInfo = "Open home page";
			logStep(stepInfo);
			HomePage.openHomePage();
			verifyStep(HomePage.isHomePageOpened(), stepInfo);

			stepInfo = "Select product from best selling";
			logStep(stepInfo);
			verifyStep(HomePage.selectItemEditorPick(2), stepInfo);

			stepInfo = "Enter valid Pin code and validate";
			logStep(stepInfo);
			CartPage.inputPinCode(Constants.PINCODE);
			CartPage.checkPinCode();
			logStep("Valid Pin code message: " + CartPage.getPinCodeValidMessage());
			verifyStep(isWebElementVisible(CartPage.txtValidPinMessage), stepInfo);

			stepInfo = "Buy Now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);

			stepInfo = "The page should navigate to cart page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(), stepInfo);

			stepInfo = "Place order";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);

			stepInfo = "The user should be navigated to checkout page.";
			verifyStep(CheckOutPage.isCheckOutPageLoaded(), stepInfo);

			stepInfo = "Login at checkout page";
			logStep(stepInfo);
			CheckOutPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			verifyStep(DeliveryPage.verifyDeliveryPageLoaded(), stepInfo);

			stepInfo = "Click Deliver here";
			executeStep(click(DeliveryPage.btnDeliverHere), stepInfo);

			stepInfo = "Verify user navigated to Order Summary page";
			logStep(stepInfo);
			verifyStep(OrderSummaryPage.isOrderSummaryPageLoaded(), stepInfo);

			stepInfo = "Verify User should Navigate to Payment page";
			logStep(stepInfo);
			executeStep(click(OrderSummaryPage.btnPlaceOrder), "Click place order");
			pause(3000);
			verifyStep(PaymentPage.isPaymentPageLoaded(), stepInfo);

			stepInfo="Verify net banking option";
			logStep(stepInfo);
			PaymentPage.clickPaymentOption(PaymentPage.PaymentOptions.NET_BANKING);
			pause(1000);
			verifyStep(
			PaymentPage.verifyPaymentOptionIsDisplayed(PaymentPage.PaymentOptions.NET_BANKING),
			stepInfo);
			pause(1000);
			
			stepInfo="Select 'HDFC Bank' in 'select from popular banks'";
			logStep(stepInfo);
			executeStep(click(By.xpath("//*[@id='net-banking']//img[contains(@src,'hdfc.png')]")), "Click HDFC BANK");
			pause(1000);
			verifyStep(verifyDropdownSelectedValue(PaymentPage.ddNetBanking_AnotherBank,"HDFC Bank"),
					stepInfo);
			
			
			
			String elementSShot = takeScreenshotWebElement(waitForVisibile(By.cssSelector(".payment-block")),
					"PaymentMethodsNetBanking_SelectAnotherBank");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);

			
			// .........Script Start...........................
		} catch (Exception e) {
			if (retryCount > 0) {
				String imagePath = takeScreenshot(getDriver(), getTestCaseID()+"_"+ retryingNumber,"Automation Bugs: "+stepInfo);

				logStepFail(stepInfo + " - " + KeywordUtil.lastAction);
				logStepError(e.getMessage());
				HtmlReportUtil.attachScreenshot(imagePath, false);

				GlobalUtil.getTestResult().setScreenshotref(imagePath);

				HtmlReportUtil.stepInfo("Trying to Rerun" + " " + getTestCaseID() + " for " + retryingNumber + " time");
				retryCount--;
				retryingNumber++;
				utilities.LogUtil.infoLog(getClass(), "****************Waiting for " + getIntValue("retryDelayTime")
						+ " Secs before retrying.***********");
				delay(getIntValue("retryDelayTime"));
				// Rerun same test
				test();
			} else {
				String imagePath = takeScreenshot(getDriver(), getTestCaseID(),"Automation Bugs: "+stepInfo);
				logStepFail(stepInfo + " - " + KeywordUtil.lastAction);
				logStepError(e.getMessage());
				HtmlReportUtil.attachScreenshot(imagePath, false);

				GlobalUtil.getTestResult().setScreenshotref(imagePath);
				GlobalUtil.setTestException(e);
				throw e;
			}
		}
	}// End Test

}
