package testingxperts.web.pages;

import java.awt.Robot;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utilities.KeywordUtil;
import utilities.LogUtil;
import utilities.Utility;

public class DeliveryPage extends HomePage{

	
	public static By txtName = By.xpath("(//div[contains(@id,'address-card')])[1]/h6[1]");
	public static By txtAddress = By.xpath("//p[contains(@class,'address-location number')]");
	
	public static By txtcountry = By.xpath("(//div[contains(@id,'address-card')])[1]//p[3]");
	public static By txtCityAndPin = By.xpath("(//div[contains(@id,'address-card')])[1]//p[2]");
	public static By inputCountry = By.xpath("//input[@placeholder='Country']");
	public static By inputName = By.xpath("//input[@placeholder='full name']");
	public static By inputAddress = By.xpath("//textarea [@placeholder='address']");
	public static By btnSave = By.xpath("//button[text()='Save']");
	
	public static By btnEditAddress = By.xpath("//div//span[@data-tooltip='Edit Address']");
	public static By btnDeleteAddress = By.xpath("//div//span[@data-tooltip='Delete Address']");
	
	public static By inputPin = By.xpath("//input[@placeholder='pincode']");
	public static By btnDeliverHere = By.xpath("//a[contains(.,'Deliver Here')]");
	public static By btnSaveDeliveryInfo = By.xpath("//button[contains(@id,'e-a-save')]");
	public static By btnAddNewAddress = By.xpath("//*[@id='a-n-address-w']/div/div/h6");
	public static By inputFullName_NewAddress = By.xpath(".//form[@id='create-s-address']//input[@name='fname']");
	public static By inputAddress_NewAddress = By.xpath(".//form[@id='create-s-address']//textarea[@name='saddr']");
	public static By inputCountry_NewAddress = By.xpath(".//form[@id='create-s-address']//input[@name='cname']");
	public static By inputPin_NewAddress = By.xpath(".//form[@id='create-s-address']//input[@name='pcode']");
	public static By inputMobile_NewAddress = By.xpath(".//form[@id='create-s-address']//input[@name='mob']");
	public static By btnSaveAddress_NewAddress = By.xpath("//button[text()='Save Address']");
	
	
	public static String getName(){
		return getDriver().findElement(txtName).getText();
	}
	
	public static String getAddress(){
		return getDriver().findElement(txtAddress).getText();
	}
	
	public static String getCountry(){
		return getDriver().findElement(txtcountry).getText();
	}
	
	public static String getCityAndPin(){
		return getDriver().findElement(txtCityAndPin).getText();
	}
	
	
	public static boolean verifyDeliveryPageLoaded () throws Exception{
		findWithFluintWait(btnAddNewAddress);
		return getDriver().findElement(btnAddNewAddress).isDisplayed();
	}
	
	public static boolean addNewAddress(String fullName, String address, String country, String pin,String mobile) throws InterruptedException{
		LogUtil.infoLog("DeliveryPage", "Add new Address");
		click(btnAddNewAddress);
		pause(1000);
		
		inputText(inputFullName_NewAddress, fullName);
		pressTabKey(inputFullName_NewAddress);
		pause(1000);
		
		inputText(inputAddress_NewAddress, address);
		pressTabKey(inputAddress_NewAddress);
		pause(1000);
		
		inputText(inputCountry_NewAddress, country);
		pressTabKey(inputCountry_NewAddress);
		pause(1000);
		
		inputText(inputPin_NewAddress, pin);
		pressTabKey(inputPin_NewAddress);
		pause(1000);
		
		inputText(inputMobile_NewAddress, mobile);
		pressTabKey(inputMobile_NewAddress);
		pause(1000);
		
		click(btnSaveAddress_NewAddress);
		pause(5000);
		
		return true;
	}
	
	public static boolean deleteDeliveryAddress() throws Exception{
		LogUtil.infoLog("DeliveryPage", "deleteDeliveryAddress");
		hoverElement(txtName);
		
		executeStep(clickJS(btnDeleteAddress), "Click Delete address icon");
		pause(1000);
		waitForVisibile(By.xpath("//*[text()='Are you sure you want to delete the address?']"));
		executeStep(click(By.xpath("//*[text()='Are you sure you want to delete the address?']/..//a[text()='Yes']")), "Click Yes");
		return true;
	}
	
	public static boolean changeCountry(String country) throws Exception{
		clickEditAddress();
		
		inputText(inputCountry, country);
		pause(1000);
		getDriver().findElement(inputCountry).sendKeys(Keys.TAB);
		pause(2000);
		
		String newCountry =findWithFluintWait(inputCountry).getAttribute("value");
		
		logStep("Updated country:" +newCountry);
		return newCountry.equalsIgnoreCase(country);
		
	}
	
	
	public static boolean changePin(String pin) throws Exception{
		clickEditAddress();
		inputText(inputPin, pin);
		pause(1000);
		getDriver().findElement(inputPin).sendKeys(Keys.TAB);
		pause(2000);
		
		String newPin =findWithFluintWait(inputPin).getAttribute("value");
		
		logStep("Updated pin:" +newPin);
		return newPin.equalsIgnoreCase(pin);
		
	}
	
	public static void clickEditAddress() throws Exception{
		LogUtil.infoLog("DeliveryPage", "Click Edit address icon");
		
	//Move mouse Down
		 Robot robot = new Robot();
		 robot.mouseMove(0, 0);
		 pause(1000);
		WebElement name = waitForVisibile(btnDeliverHere);
		Actions action = new Actions(getDriver());
		action.moveToElement(name)
		.build()
		.perform();
		pause(1000);
		clickJS(btnEditAddress);
		pause(2000);
		
	}
	
	public static void updateAddress(String name, String address, String country, String pin) throws Exception{
		
		logStep("Update address");
		
		clickEditAddress();
		
		inputText(inputName, name);
		pause(1000);
		getDriver().findElement(inputName).sendKeys(Keys.TAB);
		pause(1000);
		
		inputText(inputAddress, address);
		pause(1000);
		getDriver().findElement(inputAddress).sendKeys(Keys.TAB);
		pause(1000);
		
		inputText(inputCountry, country);
		pause(1000);
		getDriver().findElement(inputCountry).sendKeys(Keys.TAB);
		pause(1000);
		
		inputText(inputPin, pin);
		pause(1000);
		getDriver().findElement(inputPin).sendKeys(Keys.TAB);
		pause(1000);
		
		click(btnSave);
		pause(3000);
		
		
	}
	
	
	public static void saveDeliveryInfo() throws Exception{
		click(btnSaveDeliveryInfo);
		pause(1500);
	}

	
}//End class
