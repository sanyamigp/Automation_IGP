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
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({ CustomListeners.class, ExecutionStartEndListner.class })
public class IGP_TC_203 extends KeywordUtil {
	String stepInfo = "";
	int retryCount = getIntValue("retryCount");
	static int retryingNumber = 1;

	@Test(testName = "IGP_TC_203", 
			groups = {"Payment" }, 
			description = "Credit Card & Debit card: Name On the card -: ")
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
			executeStep(click(OrderSummaryPage.btnPlaceOrder), "Click place order");
			pause(3000);
			verifyStep(PaymentPage.isPaymentPageLoaded(), stepInfo);

			stepInfo = "Enter Invalid format for card on name field say <b>'123ABC#$'</b>  and verify, It should not accept it. ";
			logStep(stepInfo);
			PaymentPage.clickPaymentOption(PaymentPage.PaymentOptions.DEBIT_CARD);
			pause(2000);
			
			PaymentPage.selectDebitCardType_Visa();
			PaymentPage.inputDebitCardNumber(Constants.VISA_CARD_NUMBER);
			PaymentPage.inputDebitCardNameOnCard("123ABC#$");
			pressTabKey(PaymentPage.inputDebitCardNameOnCard);
			pause(1000);
			String name=getInputText(PaymentPage.inputDebitCardNameOnCard);
			
			String spclChar =name.replaceAll("[A-Za-z0-9]", "");
			verifyStep(spclChar.isEmpty(),"Name should not contains special characters");

			spclChar =name.replaceAll("[^0-9]", "");
			
			verifyStep(spclChar.isEmpty(),"Name should not contains numbers");
						
			//PaymentPage.clickMakePayment_ForDebitCard();
			
			pause(1000);
			
			//Verify cvv error
			verifyStep(isWebElementVisible(By.xpath("//*[@id='d-card-form'] //input[contains(@class,'error')]")), stepInfo);
			
			String elementSShot = takeScreenshot(getDriver(),"PaymentBANK_MOBIKWIK");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);

			// .........Script Start...........................
		} catch (Exception e) {
			if (retryCount > 0) {
				String imagePath = takeScreenshot(getDriver(), getTestCaseID() + "_" + retryingNumber);

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
				String imagePath = takeScreenshot(getDriver(), getTestCaseID());
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
