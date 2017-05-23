package testingxperts.web.pages;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

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
	public static final By linkUser = By.xpath("//*[@id='user-menu']");
	public static final By loginOverlayActive=By.xpath("//div[@ class='login-overlay active']");
	
	public static final By linkLogout = By.xpath("//a[@href='/logout']");
	private static boolean loginStatus=false;
	public Properties prop=new Properties();
//	FileOutputStream out = new FileOutputStream("First.properties");
//    FileInputStream in = new FileInputStream("First.properties");
    String mailPropertiesFile = System.getProperty("user.dir") + "/src/main/resources/ConfigFiles/config.properties";
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
			click(By.id("user-menu"));
			LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
			Utility.pause(1000);
			click(linkLogout);
			LogUtil.infoLog("KeywordActions", KeywordUtil.lastAction );
			Utility.pause(1500);
			loginStatus =!isWebElementPresent(By.id("user-menu"));
						
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isLogout(){
		return !loginStatus;
		
	} 
	
	public static boolean newUserLink() throws Exception
	{
		 click(By.xpath("//a[contains(text(),'Sign Up')]"));
		 executeStep(isWebElementPresent(By.id("sign-up-form")), "Sign Up Page displayed");
		 return true;
	}
	
	public static boolean duplicateSignUpDetails() throws InterruptedException, Exception
	{
		executeStep(writeInInputCharByChar(By.id("email"), "sanyam@igp.com"),"Enter email id");
		executeStep(writeInInputCharByChar(By.xpath("//form[@id='sign-up-form']//input[@id='passwd']"), "2112121"), "Enter password");
		executeStep(inputText(By.id("cname"), "USA"), "Select country");
		executeStep(inputText(By.id("mob"), "69879"), "Enter mobile number");
		executeStep(clickAndWait(By.xpath("//button[@data-target='sign-up-2']")),"Click on Submit button");
		return isWebElementPresent(By.xpath("//div[text()='Email/Number already registered']"));
	}
	
	public static boolean enterSignUpDetails(String pwd,String country,String mobNumber) throws Exception
	{
		String mailPropertiesFile = System.getProperty("user.dir") + "/src/main/resources/ConfigFiles/config.properties";
		FileInputStream in = new FileInputStream(mailPropertiesFile);
		
		Properties prop=new Properties();
		 
		prop.load(in);
		
		if(isWebElementVisible(By.id("sign-up-form")))
		{
			int i=Integer.parseInt(prop.getProperty("email"));
			executeStep(writeInInputCharByChar(By.id("email"), "austria20"+i+"@gmail.com"),"Enter email id");
			//in.close();
			i=i+1;
			String email=Integer.toString(i);
			prop.setProperty("email", email);
			executeStep(writeInInputCharByChar(By.xpath("//form[@id='sign-up-form']//input[@id='passwd']"), pwd), "Enter password");
			executeStep(inputText(By.id("cname"), country), "Select country");
			executeStep(inputText(By.id("mob"), mobNumber+i), "Enter mobile number");
			executeStep(click(By.xpath("//button[@data-target='sign-up-2']")),"Click on Submit button");
			OutputStream out = new FileOutputStream(mailPropertiesFile);
			prop.store(out, "Hello");
	        //out.close();
		}
		 isWebElementVisible(By.name("fname"));
		 return true;
		}
		
	
	
	public static boolean userSignUpDetails(String fname,String lname) throws InterruptedException
	{
		selectByIndex(By.xpath("//select[contains(@class,'signupTitle')]"), 1);
		inputText(By.name("fname"), fname);
		inputText(By.name("lname"), lname);
		return clickAndWait(By.xpath("//button[text()='Sign me up!']"));
	}
	
	public static boolean forgotPassword() throws Exception
	{
		if(isWebElementVisible(By.linkText("Forgot Password?")))
		{
			executeStep(clickAndWait(By.linkText("Forgot Password?")), "Click on Forgot Password");
			executeStep(inputText(By.id("f-email"), "sanyam.arora@indiangiftsportal.com"),"Enter valid email id");
			executeStep(clickAndWait(By.xpath("//form[@id='f-pass-form']//button[text()='Submit']")),"Click on Submit button");
			
		}
		return isWebElementVisible(By.xpath("//div[@class='login']//p[@class='f-pass-info']"));
	}
	
}//
