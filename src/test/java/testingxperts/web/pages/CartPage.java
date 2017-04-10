package testingxperts.web.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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
	public static By listCartItems = By.xpath("//ul[@class='cart-items-list']/li/span");
	public static By btnCloseCart = By.xpath("//*[@id='close-cart']");
	public static By btnBuyNow = By.xpath("//button[@id='buy-now']");
	public static By btnPersonalize=By.xpath("//button[text()='PERSONALIZE NOW']");
	public static By btnPlaceOrder = By.xpath("//a[text()='Place Order']");
	public static By btnEditPinCode = By.xpath("//*[@class='fa fa-pencil']");
	public static By btnApply = By.xpath("(//button[text()='Apply'])[position()=2]");
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


	public static void addItemInCartforFlowersnCakes(GiftBy giftBy) throws Exception
	{
		executeStep(click(linkFlower_and_cake), "Click:"+giftBy.toString());
		executeStep(selectItemEditorPick(1),
				"Select Item");
		waitForClickable(btnAddToCart);
		if(!verifyInputText(txtPinCode, Constants.PINCODE))
		{
			doubleClick(txtPinCode);
			executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
			click(btnCheckPin);
			pause(3000);
		}
		executeStep(click(btnAddToCart), 
				"Click: "+"ADD TO CART");
	}

	public static void addItemInCartforGiftHampers(GiftBy giftBy) throws Exception
	{
		executeStep(click(gifts_hamper), "Click:"+giftBy.toString());
		executeStep(selectItemEditorPick(1),
				"Select Item");
		waitForClickable(btnAddToCart);

		if(!verifyInputText(txtPinCode, Constants.PINCODE))
		{
			doubleClick(txtPinCode);
			executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
			click(btnCheckPin);
			pause(3000);
		}
		executeStep(click(btnAddToCart), 
				"Click: "+"ADD TO CART");

	}

	public static void addItemInCartforPersonalization(GiftBy giftBy) throws Exception
	{
		verifyStep(click(personalized_gifts), "Click:"+giftBy.toString());
		executeStep(selectItemEditorPick(1),
				"Select Item");
		clickAndWait(btnPersonalize);
		
		try
		{
			if(isWebElementVisible(By.xpath("//button[text()='SELECT IMAGE']")))
			{
				clickAndWait(By.xpath("//button[text()='SELECT IMAGE']"));
				pause(2000);
				fileUpload("Chrysanthemum.jpg");
			}
		}
		catch(Exception e)
		{

		}

		try
		{

			if(isWebElementPresent(By.xpath("//child::div[div[contains(text(),'Enter Text 1')]]//input")))
			{
				writeInInputCharByChar(By.xpath("//child::div[div[contains(text(),'Enter Text 1')]]//input"), "C-shaped");
				
			}
		}
		catch(Exception e)
		{

		}
		clickAndWait(By.xpath("//button[@id='personalize-done']"));
		if(!verifyInputText(txtPinCode, Constants.PINCODE))
		{
			doubleClick(txtPinCode);
			executeStep(inputText(txtPinCode, Constants.PINCODE),"Input pincode");
			click(btnCheckPin);
			pause(3000);
		}
		executeStep(clickAndWait(btnAddToCart), 
				"Click: "+"ADD TO CART");
	}

	public static void inputPinCode(String pin) throws Exception{
		doubleClick(txtPinCode);
		executeStep(inputText(txtPinCode, pin),"Input pincode:"+pin);
	}


	public static void checkPinCode() throws Exception{
		click(btnCheckPin);
		pause(2000);
	}

	public static boolean selectItem(int index){
		//First index is 5th So adding 4+index
		String xpathOfItem = String.format("(//*[@class[contains(.,'product-item')]]/div[1]/a)[position()=%d]", 4+index);
		By item=By.xpath(xpathOfItem);
		return click(item);
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
		executeStep(selectItemEditorPick(1),
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
		executeStep(verifyIsItemadded(),"Items Added");
		return isWebElementPresent(listCartItems);
	} 



	public static void closeCartOverlay() throws InterruptedException{
		//Close cart overlay
		doubleClick(btnCloseCart);

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

	public static boolean verifyIsItemadded()
	{
		int j=1;
		int addeditems=Integer.parseInt(getDriver().findElement(By.xpath("//span[@id='c-total-count']")).getText());
		try
		{
			if(addeditems==j)
				return true;
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		j++;
		return true;
	}
	public static boolean clikBuyNow() throws InterruptedException{

		return clickAndWait(btnBuyNow);

	}
	public static boolean verifyOrderDetailsPageLoaded(){
		return isWebElementVisible(btnBuyNow);
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
		click(btnApply);
		pause(2000);
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




}//End of class
