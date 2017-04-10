package testingxperts.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.ConfigReader;
import utilities.KeywordUtil;
import utilities.LogUtil;
import utilities.Utility;

/**
 * @author SUKHJINDER
 *
 */
public class LoginPage extends HomePage {
	
	public static final By linkLogin = By.xpath("//*[@id='user-menu']"); 
	public static final By linkLoginWithEmail = By.xpath("//a[text() [contains(.,'Login with email') ] ]");
	public static final By txtUserName = By.id("com-e-m-field");
	public static final By txtPassword = By.xpath("//*[@id='passwd']");
	public static final By btnLogin = By.xpath("//*[@id='e-login-btn']");
	public static final By btnSubmit = By.xpath("//button[contains(.,'Login')]"); 
	public static final By linkUser = By.xpath("//a[@id='user-menu']");
	public static final By loginOverlayActive=By.xpath("//div[@ class='login-overlay active']");
	
	public static final By linkLogout = By.xpath("//a[@href='/logout']");
	private static boolean loginStatus=false;
	
	/**
	 * @param driver
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public static void doLogin(String userName, String password) throws Exception {
		LogUtil.infoLog("KeywordActions", "Do Login");
		boolean check= click(linkLogin);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction +" - "+ check);
		waitForClickable(linkLoginWithEmail);
		check= click(linkLoginWithEmail);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction +" - "+ check);
		
	if(check){	
		enterUserName(userName);
		pause(1000);
		enterPassword(password);
		pause(1000);
		clickLoginButton();
		pause(3000);
		
		waitForInVisibile(loginOverlayActive);
		
		if(isWebElementPresent(linkUser)){
			loginStatus =!getWebElement(linkUser).getText().equalsIgnoreCase("Login");
						
		}
	}
		
	}
	
	public static void enterUserName(String uName) {
			waitForVisibile(txtUserName);
			KeywordUtil.inputText(txtUserName, uName);
			LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction);
	}
	
	public static void enterPassword(String password) throws Exception{
		waitForVisibile(txtPassword);
		KeywordUtil.inputText(txtPassword, password);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
	}
	
	public static void clickLoginButton() throws Exception{
		click(btnSubmit);
		LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
	}

	public static boolean isLogin(){
		return loginStatus;
		
	} 
	
	public static void logOut() throws Exception{
		try {
			LogUtil.infoLog("KeywordActions", "Logout from application" );
			click(By.xpath("//li[@id='user-menu-short']"));
			LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
			Utility.pause(1000);
			click(linkLogout);
			LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
			Utility.pause(1500);
			loginStatus =!isWebElementPresent(By.xpath("//li[@id='user-menu-short']//div[@class='hidden'][contains(.,'Login')]"));
						
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isLogout(){
		return !loginStatus;
		
	} 
	
}//
