package testingxperts.web.tests;

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
import utilities.ConfigReader;
import utilities.GlobalUtil;
import utilities.HtmlReportUtil;
import utilities.KeywordUtil;

@Listeners({CustomListeners.class,ExecutionStartEndListner.class})
public class IGP_TC_175 extends KeywordUtil{
	String stepInfo="";
	int retryCount=getIntValue("retryCount");
	static int retryingNumber=1;
	
	@Test(
			testName="IGP_TC_175",
			groups={"Checkout Page"}, 
			description="Subtotal' in Product Description form: Ensure that 'Subtotal' has display the Price in the form of : '[(Cost of product*Quantity)+Gift Box Charges+Shipping charges]'"
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
			 		
			 		==================================
			 		Login Steps
			 		==================================
			 			stepInfo="Login to application";
						logStep(stepInfo);
						LoginPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
						verifyStep(LoginPage.isLogin(), stepInfo);
					==================================
			 		Logout Steps
			 		==================================
		 				stepInfo="Logout from application";
		 				logStep(stepInfo);
		 				LoginPage.logOut();
		 				verifyStep(LoginPage.isLogout(), stepInfo);
		 	
			*/
			
		
			//.........Script Start...........................
			
			stepInfo="Open home page";
			logStep(stepInfo);
			HomePage.openHomePage();
			verifyStep(HomePage.isHomePageOpened(), stepInfo);
			
			stepInfo="Add product into cart.";
			logStep(stepInfo);
			CartPage.addItemInCart(HomePage.GiftBy.FLOWER_AND_CAKE);
			CartPage.selectMidnightDeliveryWithDate();
			
			verifyStep(CartPage.isItemAdded(), stepInfo);
			CartPage.closeCartOverlay();
			
			//Select Midnight Delivery
			
			
			
			stepInfo="Buy Now";
			logStep(stepInfo);
			executeStep(CartPage.clikBuyNow(), stepInfo);
			
			stepInfo="The page should navigate to cart page";
			verifyStep(CartPage.verifyOrderDetailsPageLoaded(),stepInfo);
			
			stepInfo="Place order";
			logStep(stepInfo);
			CartPage.clickPlaceOrder();
			pause(2000);
			
			stepInfo="The user should be navigated to checkout page.";
			verifyStep(CheckOutPage.isCheckOutPageLoaded(),
					stepInfo);
			
			stepInfo="Login at checkout page";
			logStep(stepInfo);
			CheckOutPage.doLogin(ConfigReader.getValue("loginUser"), ConfigReader.getValue("loginPassword"));
			verifyStep(DeliveryPage.verifyDeliveryPageLoaded(),
					stepInfo);
			
			stepInfo="Click Deliver here";
			executeStep(click(DeliveryPage.btnDeliverHere), stepInfo);
			
			stepInfo="Verify user navigated to Order Summary page";
			logStep(stepInfo);
			verifyStep(OrderSummaryPage.isOrderSummaryPageLoaded(),stepInfo);
			
			stepInfo="Verify '[(Cost of product*Quantity)+Gift Box Charges+Shipping charges]'";
			
			//Get Cost
			int productCost = OrderSummaryPage.getProductActualCost();
			
			//Get Qty
			int productQty  =OrderSummaryPage.getQty_FirstItem();
			
			//Get Midnight Delivery Charge
			String deliveryCharge  = OrderSummaryPage.getDeliveryCharge();
			
			int beginIndex =(deliveryCharge.indexOf(" - ")+2);
			int endIndex =deliveryCharge.indexOf("(");
			deliveryCharge =deliveryCharge.substring(beginIndex, endIndex).trim();
			deliveryCharge =deliveryCharge.replaceAll("[^0-9]", "");
			
			int dlvCharge =0;
			
			if(!deliveryCharge.isEmpty())
			{dlvCharge =Integer.parseInt(deliveryCharge);
			}
			
			int productSubTotal =OrderSummaryPage.getProductSubtotal();
			logStep(String.format("Product actual cost: %d, Qty: %d, Delivery charge: %d" , productCost,productQty, dlvCharge));
			
			int expectedSubTotal =(productCost*productQty)+dlvCharge;
			
			logStep(String.format("Product sub total : %d" , productSubTotal));
			verifyStep(expectedSubTotal==productSubTotal, String.format("|Expected SubTotal((cost*qty)+delivery_cost): %d| |Actual SubTotal: %d|", expectedSubTotal,productSubTotal));
			
			
			/*
			 * Note this test case required to verify Gift box cost, But at the time writing script
			 * Gift box charge are not available and Delivery charge also not displaying
			 * So this Test is failing
			 * 
			 * */
			//Get GiftBox cost
			//Verify SubTotal = [cost*qty]+Delivery 
				
			String elementSShot=takeScreenshotWebElement(waitForVisibile(OrderSummaryPage.listTotalItems),"ProductDescriptionForm");
			HtmlReportUtil.attachScreenshotForInfo(elementSShot);
			
			
			getDriver().navigate().back();
		    getDriver().navigate().back();
			 
		
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
