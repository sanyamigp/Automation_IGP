package testingxperts.web.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testingxperts.web.pages.HomePage.GiftBy;
import utilities.ConfigReader;
import utilities.KeywordUtil;
import utilities.LogUtil;

/*
 * Cart Functionality
 * ------------------
 * */


/**
 * @author SUKHJINDER
 *
 */
public class CartPage extends HomePage{

	public static By btnAddToCart = By.xpath("//*[@id='add-cart']");
	public static By txtPinCode = By.xpath("//*[@id='pincode']");
	public static By btnCheckPin=By.xpath("//*[@id='pinCheck']");

	public static By enterPincode=By.id("full-size-pin-code");

	public static By btnCloseCart = By.xpath("//*[@id='close-cart']");
	public static By btnBuyNow = By.xpath("//button[@id='buy-now']");
	public static By btnPlaceOrder = By.xpath("//a[text()='Place Order']");
	public static By btnEditPinCode = By.xpath("//*[@class='fa fa-pencil']");
	public static By btnenterPinCode=By.xpath("//button[contains(text(),'Enter Pincode')]");
	public static By btnApply = By.xpath("(//button[contains(@class,'apply-pin')])[position()=1]");
	public static By UpdateApply=By.xpath("(//button[contains(@class,'apply-pin')])[position()=2]");
	public static String lastPinNumber ="";
	public static By btnDeleteSingleItem = By.xpath("//*[@id='site-wrapper']//div[@class='row c-item flex-row']//div[contains(@class,'close-menu-s')]");
	public static By alertMessageForDelete =By.xpath("//div[@class='c-o-content']/h5[contains(.,'Are you sure you want to remove the item from cart')]");
	public static By linkRemove = By.xpath("//div[@class='c-o-content']//p//span/a[contains(@id,'remove')]");
	public static By txtMessageWrongPin = By.xpath("//*[@id='pin-error-text']");
	public static By txtValidPinMessage = By.xpath("//*[@id='sameday-help-text']");

	public static By chkMidnightDelivery = By.xpath("//label[contains(.,'Midnight delivery')]");
	public static By selectMidnightDeliveryNight = By.xpath("//div[@id='mid-night']//input[@type='text']");
	public static By tblDateTimePicker = By.id("datepicker-midnight_table");

	public static By linkSameDayDelivery = By.xpath("//a[contains(.,'Same Day Delivery Gifts')]");
	public static By btnEditPersonalize = By.xpath("//button[contains(.,'EDIT PERSONALIZATION')]");
	public static By btnPersonalize=By.xpath("//button[text()='PERSONALIZE NOW']");


	public static void addItemInCart(GiftBy giftBy) throws Exception{
		if(giftBy==GiftBy.FLOWER_AND_CAKE){
			executeStep(click(linkFlower_and_cake), "Click:"+giftBy.toString());
		}

		executeStep(selectItem(1),
				"Select Item");
		waitForClickable(btnAddToCart);

		doubleClick(txtPinCode);
		executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
		click(btnCheckPin);
		pause(3000);

		executeStep(click(btnAddToCart), 
				"Click: "+"ADD TO CART");
	}

	public static boolean personalizedItem(GiftBy giftBy) throws InterruptedException, Exception
	{
		verifyStep(click(personalized_gifts), "Click:"+giftBy.toString());
		executeStep(selectItemEditorPick(4),
				"Select Item");
		clickAndWait(btnPersonalize);
		try
		{
			if(isWebElementPresent(By.xpath("(//button[text()='SELECT IMAGE'])[position()=1]")))
			{
				List<WebElement> images=getListElements(By.xpath("//button[text()='SELECT IMAGE']"));
				for(int i=1;i<images.size();i++)
				{
					clickAndWait(By.xpath("(//button[text()='SELECT IMAGE'])[position()="+i+"]"));
					pause(2000);
					fileUpload("PersonalizedImage.jpg");
				}
			}
		}
		catch(Exception e)
		{

		}
		try
		{

			if(isWebElementPresent(By.xpath("(//input[contains(@class,'pers-input-text')])[position()=1]")))
			{
				List<WebElement> enterText=getListElements(By.xpath("(//input[contains(@class,'pers-input-text')])"));
				for(int i=1;i<enterText.size();i++)
					writeInInputCharByChar(By.xpath("(//input[contains(@class,'pers-input-text')])[position()="+i+"]"), "C-shaped");

			}
		}
		catch(Exception e)
		{

		}

		return true;
	}


	public static boolean isCartPageLoaded()
	{
		return getDriver().
				getCurrentUrl().
				equalsIgnoreCase(ConfigReader.getValue("CART_URL"));


	}

	public static void inputPinCode(String pin) throws Exception{
		doubleClick(txtPinCode);
		executeStep(inputText(txtPinCode, pin),"Input pincode:"+pin);
	}

	public static void enterPincodesuccessfully(String pincode) throws Exception
	{
		executeStep(click(btnenterPinCode),"Click on Enter Pincode button");
		executeStep(writeInInputCharByChar(enterPincode, pincode),"Enter valid pincode");
		clickAndWait(btnApply);
	}


	public static void checkPinCode() throws Exception{
		click(btnCheckPin);
		pause(2000);
	}

	public static boolean changeCountry(String countryName) throws InterruptedException
	{
		writeInInputCharByChar(By.xpath("//input[contains(@class,'col s11 suggestCountry')]"), countryName);
		clickAndWait(By.xpath("//li/span[text()='USA']"));
		return true;
	}

	public static boolean verifyEnterPincodebutton() throws InterruptedException
	{
		return clickAndWait(btnenterPinCode);
	}
	public static int get_Qty() throws Exception
	{
		String qty=	getWebElement(By.xpath("//input[contains(@id,'c-item-qty')]")).getAttribute("value");
		return Integer.parseInt(qty);
	}

	public static boolean incrementOperator() throws InterruptedException
	{
		return clickAndWait(By.xpath("//span[contains(@id,'inc-quantity')]"));
	}

	public static boolean verifyPinCodeMessage() throws Exception{
		return verifyTextContains(txtMessageWrongPin, Constants.ERROR_MESSAGE_INVALID_PIN);
	}

	public static String getPinCodeErrorMessage(){
		return getElementText(txtMessageWrongPin);
	}

	public static String getPinCodeValidMessage(){
		return getElementText(txtValidPinMessage);
	}

	public static void selectItemToBuy(GiftBy giftBy) throws Exception{
		if(giftBy==GiftBy.FLOWER_AND_CAKE){
			executeStep(click(linkFlower_and_cake), "Click:"+giftBy.toString());
		}
		executeStep(selectItem(1),
				"Select Item");
		waitForClickable(btnAddToCart);
		doubleClick(txtPinCode);
		executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
		click(btnCheckPin);
		pause(3000);
	}


	public static boolean isItemAdded() throws Exception{
		executeStep(click(PageMenu.menuCart),
				"Click cart button");
		pause(1000);
		waitForClickable(listCartItems);
		return isWebElementPresent(listCartItems);
	} 

	public static boolean selectItem(int index){
		//First index is 5th So adding 4+index
		String xpathOfItem = String.format("(//*[@class[contains(.,'product-item')]]/div[1]/a)[position()=%d]", 4+index);
		By item=By.xpath(xpathOfItem);
		return click(item);
	}

	public static void closeCartOverlay() throws InterruptedException{
		//Close cart overlay
		click(btnCloseCart);
		pause(2000);
	}	

	public static boolean emptyCart() throws Exception{


		executeStep(click(PageMenu.menuCart),"Click cart UI");
		pause(2000);
		int size=getListElements(listCartItems).size();
		logStep("Empty cart. Total items# " +size);
		for(int i=0;i<=size-1;i++){
			getWebElement(CartPage.listCartItems).click();
			pause(2000);
			getWebElement(By.xpath("//ul[@class='cart-items-list']/li//a[contains(.,'Remove')]")).click();
			pause(2000);
		}

		pause(2000);
		return true;
	}



	public static boolean verifyCartIsEmptyMessage(){
		return verifyText(By.xpath("//*[@id='cart']/div[2]/div/h6"), "Cart is Empty");
	}
	public static boolean verifyContinueShoppingMessageDisplayed(){
		return isWebElementVisible(By.xpath("//a[contains(.,'Continue Shopping')]"));
	}

	public static void removeItem() throws InterruptedException{
		waitForClickable(btnDeleteSingleItem);
		click(btnDeleteSingleItem);
		pause(1500);
	}

	public static void clickRemoveLink() throws InterruptedException{
		waitForClickable(linkRemove);
		click(linkRemove);
		pause(1500);
	}

	public static boolean verifyItemIsRemoved(){
		WebElement element =waitForVisibile(By.xpath("//*[@id='site-wrapper']//h5[contains(.,'Cart is Empty')]"));
		return element.isDisplayed();
	}

	public static boolean verifyIsRemoveItemAlertDisplayed(){
		boolean status=false;
		try {
			WebElement element=	findWithFluintWait(alertMessageForDelete);
			status= element.isDisplayed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public static boolean clikBuyNow(){

		return click(btnBuyNow);

	}
	public static boolean verifyOrderDetailsPageLoaded(){
		return isWebElementVisible(btnPlaceOrder);
	}

	public static void clickPlaceOrder() throws Exception{
		if(isWebElementVisible(btnPlaceOrder))
		{
			executeStep(click(btnPlaceOrder), "Click place order");
			pause(2000);
		}


	}
	public static void updatePinCode(String pinCode) throws InterruptedException{
		click(btnEditPinCode);
		pause(1000);
		waitForClickable(By.xpath("//input[@id='pin-code']"));
		LogUtil.infoLog("UpdatePin", KeywordUtil.lastAction);
		inputText(By.xpath("//input[@id='pin-code']"), pinCode);
		pause(2000);
		click(UpdateApply);
		pause(4000);
		lastPinNumber=pinCode;
	}

	public static void selectMidnightDeliveryWithDate() throws Exception{
		waitForClickable(chkMidnightDelivery);
		executeStep(click(chkMidnightDelivery), "Click midnight delivery option");
		pause(1000);
		executeStep(click(selectMidnightDeliveryNight), "Select date");
		//List<WebElement> dateTable =getListElements(tblDateTimePicker);
		pause(2000);
		executeStep(click(By.xpath("//*[@id='datepicker-midnight_table']//td[div[contains(@class,'today')]]/following-sibling::td[2]")), "Select tomorrow date");
		pause(2000);

	}

	public static void addProductInCart() throws Exception
	{

		waitForClickable(btnAddToCart);

		doubleClick(txtPinCode);
		executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
		click(btnCheckPin);
		pause(3000);

		executeStep(click(btnAddToCart), 
				"Click: "+"ADD TO CART");
	}

	public static boolean verifyDeliveryLocation(String deliveryLocation)
	{
		if(getElementText(By.xpath("(//p[@class='c-item-d-pin'])[position()=1]")).contains(deliveryLocation))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyFlowersErrorMessage()
	{
		return isWebElementVisible(By.xpath("(//div[contains(@class,'row c-item flex-row')])[position()=2]//p[text()='This item is not deliverable to the selected pincode.']"));
	}

	public static boolean verifyErrorforInternational()
	{
		if(isWebElementPresent(By.xpath("(//div[contains(@class,'row c-item flex-row')])[position()=1]//p[text()='This item is not deliverable to the selected pincode.']")) && isWebElementPresent(By.xpath("(//div[contains(@class,'row c-item flex-row')])[position()=3]//p[text()='This item is not deliverable to the selected pincode.']")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyWorldWideErrorMessage()
	{
		if(isWebElementPresent(By.xpath("(//div[contains(@class,'row c-item flex-row')])[position()=2]//p[text()='This item is not deliverable to the selected pincode.']")) && isWebElementPresent(By.xpath("(//div[contains(@class,'row c-item flex-row')])[position()=3]//p[text()='This item is not deliverable to the selected pincode.']")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyChatPopUp() throws InterruptedException, Exception
	{
		executeStep(click(By.xpath("//p[@class='help-text']/a[@class='chat-me']")), "Click on Chat link");
		return isWebElementVisible(By.id("lc_chat_layout"));
	}

	public static boolean verifyRemoveLink() throws Exception
	{
		return	click(By.xpath("//div[@class=' v-bottom']/a[text()='Remove']"));
	}


}//End of class
