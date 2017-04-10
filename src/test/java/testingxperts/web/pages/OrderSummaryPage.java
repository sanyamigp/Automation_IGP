package testingxperts.web.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.KeywordUtil;
import utilities.LogUtil;

public class OrderSummaryPage extends HomePage {

	public static By txtOrderAmount = By.xpath("//h5[contains(.,'Total Amount Payable : Rs')]");
	public static By txtShippingCharge = By.xpath("//h5[contains(.,'Total Amount Payable : Rs')]/p");
	public static By linkApplyCoupen = By.linkText("Click here to apply");
	public static By inputCoupen = By.xpath("//*[@id='coupon']");
	public static By txtCoupenSuccessMessage =By.xpath("//p[@class='coupon-band-text']");
	public static By txtCoupenInvalidMessage =By.xpath("//*[@id='coupon-message']");
	public static By btnApplyCoupen = By.xpath("//button[text()='APPLY']");
	public static By txtAddressLine = By.xpath("//p[@class='address-line']");
	public static By linkEnterMessage = By.xpath("//*[text()='enter message']");
	public static By inputTo = By.xpath("//*[@id='to-field']");
	public static By inputMessage = By.xpath("//*[@id='msg-field']");
	public static By inputFrom = By.xpath("//*[@id='from-field']");
	public static By btnDone = By.xpath("//button[text()='Done']");
	public static By listTotalItems = By.xpath("//*[@class='order-item-row']");
	public static By btnPlaceOrder = By.linkText("PLACE ORDER");
	public static By fixedtimeDelivery=By.xpath("//label[contains(text(),'Fixed Time Delivery')]");
	public static By midnightDeliver=By.xpath("//label[contains(text(),'Midnight Delivery')]");

	public static HashMap<String, String> contacts=new HashMap<String, String>();	

	public static boolean isOrderSummaryPageLoaded() throws Exception{
		pause(3000);
		findWithFluintWait(txtOrderAmount, 60, 500);
		return verifyTextContains(txtOrderAmount,"Total Amount Payable");
	}
	public static String getAddressLine(){
		waitForVisibile(txtAddressLine);
		return getElementText(txtAddressLine);
	}

	public static boolean verifyEnterMessagePopUp() throws Exception{
		LogUtil.infoLog("OrderSummaryPage", "verifyEnterMessagePopUp");
		boolean result=false;
		executeStep(click(linkEnterMessage),"Click Enter message");
		pause(1000);

		result =(isWebElementVisible(inputTo) &&  
				isWebElementVisible(inputMessage) &&
				isWebElementVisible(inputFrom) &&
				isWebElementVisible(btnDone) );

		return result;
	}

	public static int getTotalItems(){
		return getDriver().findElements(listTotalItems).size();
	}

	public static boolean isShippingChargeDisplayed(){
		return waitForClickable(txtShippingCharge).isDisplayed();
	}

	public static String getShippingCharge(){
		return getElementText(txtShippingCharge);
	}

	public static void clickApplyCoupen() throws Exception{

		findWithFluintWait(linkApplyCoupen, 60, 500);
		click(linkApplyCoupen);
	}

	public static boolean verifyCoupenInputFieldIsDisplayed(){
		return waitForVisibile(inputCoupen).isDisplayed();
	}

	public static void inputCoupenAndApply(String coupen) throws InterruptedException{
		waitForVisibile(inputCoupen);
		inputText(inputCoupen, coupen);
		pause(1000);
		click(btnApplyCoupen);
		pause(3000);
	}

	public static boolean verifyCoupenIsSuccess(String coupen){
		String expectedMessage  =String.format("Coupon Code %s successfully applied.", coupen);
		logStep("Message: " + getElementText(txtCoupenSuccessMessage));

		return verifyTextContains(txtCoupenSuccessMessage, expectedMessage);	

	}

	public static boolean verifyCoupenIsInvalid(String coupen){
		String expectedMessage  ="Invalid coupon code";
		logStep("Message: " + getElementText(txtCoupenInvalidMessage));
		return verifyTextContains(txtCoupenInvalidMessage, expectedMessage);	

	}

	public static void clickRemoveItem_First() throws InterruptedException{
		logStep("Click Remove item X button");
		WebElement element =getListElements(listTotalItems).get(0);
		element.findElement(By.xpath("//span[contains(@class,'del-c-item')]")).click();
		pause(2000);
	}

	public static boolean verifyRemoveItemPopUpIsDisplayed_WithCancelAndRemoveOptions(){
		return isWebElementVisible(By.xpath("//*[text()='Are you sure you want to remove the item from cart?']"))
				&& isWebElementVisible(By.xpath("//*[text()='Are you sure you want to remove the item from cart?']/..//span/a[text()='Cancel']"))
				&& isWebElementVisible(By.xpath("//*[text()='Are you sure you want to remove the item from cart?']/..//span/a[text()='Remove']"));
	}


	public static void clickCancel_PopUpLink() throws Exception{
		pause(1000);
		executeStep(click(By.xpath("//*[text()='Are you sure you want to remove the item from cart?']/..//span/a[text()='Cancel']")),
				"Click cancel popup link");
		pause(2000);
	}

	public static boolean clickRemove_PopUpLink() throws Exception{
		pause(2000);
		return click(By.xpath("//*[text()='Are you sure you want to remove the item from cart?']/..//span/a[text()='Remove']"));

	}

	public static int getQty_FirstItem() throws InterruptedException{
		WebElement element =getListElements(listTotalItems).get(0);
		pause(2000);
		String qty=	element.findElement(By.xpath("//span[@class='value']/input")).getAttribute("value");
		return Integer.parseInt(qty.trim());
	}


	public static boolean  IsIncreaseAndDecraseUIDisplayed() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//span[contains(@id,'inc-quantity')]")).isDisplayed()
				&&	element.findElement(By.xpath("//span[contains(@id,'des-quantity')]")).isDisplayed();
	}

	public static boolean  IsProductImageDisplayed() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//*[@class='order-item-row']//img")).isDisplayed();

	}

	public static int  getProductActualCost() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		LogUtil.infoLog("OrderSummaryPage", "Product actual cost: "+element.findElement(By.xpath("//p//span[@class='number']")).getText() );
		return Integer.parseInt(element.findElement(By.xpath("//p//span[@class='number']")).getText().trim());

	}

	public static boolean  IsProductActualCostDisplayed() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//span[@class='number']")).isDisplayed();

	}

	public static boolean  IsProductDeliveryDetailsInfoDisplayed() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//div[contains(@class,'c-item-delivery')]")).isDisplayed();
	}
	public static boolean  IsProductSubtotalDisplayed() throws InterruptedException{
		waitForPresent(listTotalItems);
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//div[@class='c-item-d-type']")).isDisplayed();
	}	

	public static void increaseQty_FirstItem() throws InterruptedException{
		logStep("Increase Qty of First Item");
		WebElement element =getListElements(listTotalItems).get(0);
		element.findElement(By.xpath("//span[contains(@id,'inc-quantity')]")).click();
		pause(3000);

	}


	public static void DecreaseQty_FirstItem() throws InterruptedException{
		WebElement element =getListElements(listTotalItems).get(0);
		element.findElement(By.xpath("//*[@class='order-item-row']//span[contains(@id,'des-quantity')]")).click();
		pause(3000);
	}

	public static String getOrderTotalAmount(){
		WebElement element =getListElements(listTotalItems).get(0);
		waitForClickable(txtOrderAmount);
		return getDriver().findElement(By.xpath("//h5[contains(.,'Total Amount Payable : Rs')]/span[@class='number']")).getText().trim();
	} 

	public static String getDeliveryCharge(){
		WebElement element =getListElements(listTotalItems).get(0);
		return element.findElement(By.xpath("//div[contains(@class,'c-item-delivery')]//p[@class='c-item-d-type']")).getText().trim(); 
	}

	public static int getProductSubtotal(){
		WebElement element =getListElements(listTotalItems).get(0);

		return Integer.parseInt(element.findElement(By.xpath("//span[contains(@id,'s-total-val')]")).getText().trim()); 
	}

	public static String getDeliveryDateForItem(int index){
		return  getListElements(By.xpath("//div[contains(@class,'c-item-delivery')]//p[@class='c-item-d-type']"))
				.get(index-1).getText();
	}

	//Saynam
	public static boolean changecontactDetails(String firstname,String lastname,String country,String contactnumber) throws InterruptedException
	{
		contacts.put(firstname,"//input[@placeholder='Enter First Name']");
		contacts.put(lastname,"//input[@placeholder='Enter Last Name']");
		contacts.put(country,"//input[@placeholder='Enter a country']");
		contacts.put(contactnumber,"//input[@placeholder='Enter Mobile Number']");
		Iterator<Entry<String,String>> it=contacts.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String, String> fieldinfo=it.next();
			String fieldkey=fieldinfo.getKey();
			String fieldtype=fieldinfo.getValue();
			if(fieldkey!=null)
			{
				return writeInInputCharByChar(By.xpath(fieldtype),fieldkey);
			}

		}
		return click(By.xpath("//input[@value='Save']"));	

	}

	//Sanyam
	public static boolean setfuturedeliveryDate(int date,String deliveryMonth) throws Exception
	{

		click(By.xpath("//span[@class='date-svg change-date undefined']"));
		clickAndWait(By.xpath("//input[contains(@id,'fixed-date')]"));

		String part1="//table[contains(@aria-controls,'fixed-date')]//tbody//td/div[contains(text(),'";
		String part2="')]";
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else if(deliveryMonth=="Present Month")
		{
			if(isWebElementVisible(By.xpath("//div[@aria-disabled]")))
			{
				return true;
			}
			else
			{
				getDriver().findElement(By.xpath(part1+date+part2)).click();
				pause(3000);
			}
		}
		else if(deliveryMonth=="Next Month")
		{
			clickAndWait(By.xpath("//child::div[h6[text()='Fix Date Delivery']]//div[@title='Next month']"));
			if(isWebElementVisible(By.xpath("//div[@aria-disabled]")))
			{
				return true;
			}
			else
			{
				getDriver().findElement(By.xpath(part1+date+part2)).click();
				pause(3000);
			}
		}
		click(By.xpath("//child::div[h6[text()='Fix Date Delivery']]//button[text()='Select']"));
		return true;
	}

	//Sanyam
	public static boolean fixedtimedeliveryoption(int date) throws InterruptedException
	{
		click(fixedtimeDelivery);
		clickAndWait(By.xpath("//input[contains(@id,'fixed-dp')]"));
		String part1="//table[contains(@aria-controls,'fixed-dp')]//tbody//td/div[contains(text(),'";
		String part2="')]";
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else 
		{
			getDriver().findElement(By.xpath(part1+date+part2)).click();
			pause(3000);


			click(By.xpath("//child::div[h6[text()='Fixed Time Delivery']]//button[text()='Select']"));
			selectByValue(By.xpath("//select[contains(@id,'fixed-tp')]"), "16:00 hrs - 18:00 hrs");
			return true;
		}
	}
	public static boolean verifydeliveryoption(String deliverymethod,int date) throws InterruptedException
	{
		if(deliverymethod=="Fixed Time Delivery")
		{
			return fixedtimedeliveryoption(date);
		}

		if(deliverymethod=="Midnight Time Delivery")
		{
			return midnightdeliveryoptions(date);
		}
		else
		{
			return false;
		}
	}

	public static Boolean midnightdeliveryoptions(int date) throws InterruptedException
	{
		click(midnightDeliver);
		clickAndWait(By.xpath("//input[contains(@id,'mid-dp')]"));
		String part1="//table[contains(@aria-controls,'mid-dp')]//tbody//td/div[contains(text(),'";
		String part2="')]";
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else if(getDriver().findElement(By.xpath("//div[contains(@class,'disabled')]")).isDisplayed())
		{
			return true;
		}
		else 
		{
			getDriver().findElement(By.xpath(part1+date+part2)).click();
			pause(3000);
			click(By.xpath("//child::div[h6[text()='Midnight Delivery']]//button[text()='Select']"));
			return true;
		}
	}

	public static boolean validatecouponcoderesult(String couponcode) throws Exception
	{
		inputCoupenAndApply(couponcode);
		if(getWebElement(txtCoupenSuccessMessage).getText().contains("Coupon Code"))
		{

			return verifyCoupenIsSuccess(couponcode);
		}
		else if(getWebElement(txtCoupenInvalidMessage).getText().contains("Invalid"))
		{
			return verifyCoupenIsInvalid(couponcode);
		}
		else
		{
			return false;
		}
	}

	public static boolean entermessagedetails(String to,String message,String from) throws Exception
	{
		if(to.equals("")|| message.equals("")|| from.equals(""))
		{
			writeInInputCharByChar(inputTo, to);
			writeInInputCharByChar(inputMessage, message);
			writeInInputCharByChar(inputFrom, from);
			click(btnDone);
			return waitForPresent(By.xpath("//div[@class='msg-container active']//*[contains(@class,'error')]")).isDisplayed();
		}
		else
		{
			writeInInputCharByChar(inputTo, to);
			writeInInputCharByChar(inputMessage, message);
			writeInInputCharByChar(inputFrom, from);
			click(btnDone);
			return waitForPresent(By.xpath("//p[text()='Edit Message']")).isDisplayed();
		}


	}



}
