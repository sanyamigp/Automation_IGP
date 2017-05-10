package testingxperts.web.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.taskdefs.Exec;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import utilities.ConfigReader;
import utilities.ExcelDataUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class CheckOutPage extends HomePage{
	
		 public static Object[][] str;
		public static ExcelDataUtil excel=new ExcelDataUtil();
	
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

	public static boolean verifyAddressInCard()
	{
		return isWebElementPresent(By.xpath("//h6[@id='a-n-address']"));
	}

	public static boolean verifyAddressBottom()
	{
		return isWebElementVisible(By.xpath("//h6[contains(@class,'add-new-add')]"));
	}

	public static boolean verifySignInLink()
	{
		if(isWebElementVisible(By.id("new-user-sp")))
		{
			click(By.id("new-user-sp"));
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean signUpDetails(String password,String salutation,String firstName,String lastName,String country) throws Exception
	{
		
		//str=ExcelDataUtil.readSheet("Email.xls", "Sheet1");
		 
		try
		{
			if(isWebElementVisible(By.id("c-signup-form-2")))
			{
				Object i=1;
				executeStep(inputText(By.id("c-s-user"),"austria@gmail.com"),"Emaild id");
				//excel.writeSheet("Email.xls", "Sheet1", (str[1][0]+i));
				executeStep(inputText(By.id("c-s-password"),password),"Password");
				executeStep(selectByValue(By.id("c-title"), salutation),"Select Salutation Type");
				executeStep(inputText(By.id("c-fname"), firstName),"Select first name");
				executeStep(inputText(By.id("c-lname"), lastName), "Select last name");
				executeStep(inputText(By.id("c-s-cname"), country), "Select country");
				click(By.xpath("//li[contains(@class,'Hover')]"));
				executeStep(inputText(By.id("user-e-m-2"),"9988471167"), "Mobile Number:");
				//excel.writeSheet("Email.xls", "Sheet1", str[1][1]+);
				click(By.id("c-l-submit"));
				isWebElementVisible(By.id("address-list"));
				return true;

			}
		}

		catch(Exception e)
		{

			isWebElementPresent(By.xpath("//div[@id='c-out-login-block']//p[text()='Email/Number already registered']"));
			return true;
		}
	
	return true;
}

public static boolean verifyfacebookLogin() throws InterruptedException
{
	String mainWindow=getDriver().getWindowHandle();
	String facebookWindow=null;
	click(By.xpath("//button[contains(@class,'btn-fb')]"));
	pause(5000);

	Set<String> handles = getDriver().getWindowHandles(); // get all window handles
	Iterator<String> iterator = handles.iterator();
	while (iterator.hasNext()){
		facebookWindow = iterator.next();
	}
	getDriver().switchTo().window(facebookWindow);
	String facebookUrl=getCurrentUrl();
	if(facebookUrl.contains("https://www.facebook.com/"))
	{
		getDriver().switchTo().window(mainWindow);
		return true;
	}
	else
	{
		return false;
	}

}

public static boolean verifyGoogleLogin() throws InterruptedException
{
	String mainWindow=getDriver().getWindowHandle();
	String facebookWindow=null;
	click(By.xpath("//button[contains(@class,'btn-gplus')]"));
	pause(5000);

	Set<String> handles = getDriver().getWindowHandles(); // get all window handles
	Iterator<String> iterator = handles.iterator();
	while (iterator.hasNext()){
		facebookWindow = iterator.next();
	}
	getDriver().switchTo().window(facebookWindow);
	String facebookUrl=getCurrentUrl();
	if(facebookUrl.contains("https://accounts.google.com/"))
	{
		getDriver().switchTo().window(mainWindow);
		return true;
	}
	else
	{
		return false;
	}
}

public static boolean verifyForgotPassword(String email) throws Exception
{
	if(isWebElementVisible(By.linkText("Forgot Password?")))
	{
		executeStep(click(By.linkText("Forgot Password?")), "Click on Forgot Password");
		executeStep(inputText(By.id("f-u-field"),email), "Enter emailId or mobile number");
		executeStep(click(By.xpath("//div[@id='c-out-fpass-block']//button[text()='Send OTP']")), "Click on Send OTP button");
		return true;

	}
	else
	{
		return false;
	}
}

public static boolean verifySuccessfulOTP()
{
	return isWebElementVisible(By.xpath("//div[@id='c-out-fpass-block']//p[text()='OTP sent successfully']"));
}

public static boolean errorWarningMessage()
{
	return isWebElementPresent(By.xpath("//p[contains(text(),'not applicable')]"));
}


}
