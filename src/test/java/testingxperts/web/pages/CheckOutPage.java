package testingxperts.web.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import utilities.KeywordUtil;
import utilities.LogUtil;

public class CheckOutPage extends HomePage{
	public static By inputUserId = By.xpath("(//input[@name='email'][@placeholder='enter email address'])[position()=1]");
	public static By inputPassword = By.xpath("(//input[@name='passwd'][@placeholder='enter password'])[position()=2]");
	public static By btnLogin = By.xpath("(//button[@type='submit'][contains(.,'Login')])[position()=2]");
	public static By listHeadersTabs = By.xpath("//a[contains(@class,'checkout-tab-text')]");
	
	public static void doLogin(String userName, String password) throws Exception {
		LogUtil.infoLog("CheckOutPageLogin", "Login at checkout page");
		inputText(inputUserId, userName);
		inputText(inputPassword, password);
		pause(1000);
		click(btnLogin);
		pause(3000);
		
	}
	
	public static boolean isCheckOutPageLoaded() throws Exception {
		return getCurrentUrl().contains("checkout");
	}
	
	public static boolean verifyHeaderTabs() throws Exception {
		List<WebElement> listHeaders = getListElements(listHeadersTabs);
		List<String> expectedHeaderTextList = new ArrayList<>();
		expectedHeaderTextList.add("Login");
		expectedHeaderTextList.add("Delivery Information");
		expectedHeaderTextList.add("Order Summary");
		expectedHeaderTextList.add("Payment");
		
		List<String> actualHeaderTextList = new ArrayList<>();
		
		System.out.println("Header Tab items #" +listHeaders.size());
		listHeaders.forEach(elm->{
			actualHeaderTextList.add(elm.getText());
		});
		
		LogUtil.infoLog("Checkout Page", "Expected Headers" + expectedHeaderTextList);
		LogUtil.infoLog("Checkout Page", "Actual Headers" + actualHeaderTextList);
		
		boolean result=false;
		for(int i=0;i<expectedHeaderTextList.size();i++){
			result=actualHeaderTextList.get(i).contains(expectedHeaderTextList.get(i));
			}
		return result;
	}
	
	
}
