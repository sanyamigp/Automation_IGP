package testingxperts.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import utilities.ConfigReader;
import utilities.KeywordUtil;

public class HomePage extends KeywordUtil {

	public enum GiftBy { FLOWER_AND_CAKE, GIFT_HAMPER,PERSONALIZED} 

	public static By linkFlower_and_cake= By.xpath("//p[text()='Flowers & Cakes']");
	public static By logo = By.xpath("//*[@id='absolute-header']/div[1]/div/a/img");
	public static By personalized_gifts=By.xpath("//div[contains(@class,'icon')]/p[text()='Personalized Gifts']");
	public static By cartIcon=By.id("s-cart");
	public static By listCartItems = By.xpath("//ul[@class='cart-items-list']/li/span");

	public static String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public static void openHomePage() throws Exception {
		navigateToUrl(ConfigReader.getValue("BASE_URL"));

	}

	public static boolean isHomePageOpened() throws Exception {
		return getDriver().
				getCurrentUrl().
				equalsIgnoreCase(ConfigReader.getValue("BASE_URL"));
	}

	public static void clickLogo() throws Exception{
		executeStep(click(logo), "Click Logo");
		pause(1000);

	}

	public static boolean selectItemEditorPick(int index) throws InterruptedException{
		//First index is 5th So adding 4+index
		String xpathOfItem = String.format("(//*[@id='edp-default']//img)[position()=%d]", 4+index);
		By item=By.xpath(xpathOfItem);
		pause(3000);
		return click(item);

	}

	public static boolean sendgiftsWorldwide() throws InterruptedException
	{
		if(isWebElementPresent(By.xpath("//img[@alt='CCS Banner']")))
		{
			return clickAndWait(By.xpath("//img[@alt='CCS Banner']"));
		}
		else
			return false;
	}

	public static boolean freeShippingtoUSA() throws InterruptedException
	{
		if(isWebElementPresent(By.xpath("//img[@alt='USA free-shipping']")))
		{
			return clickAndWait(By.xpath("//img[@alt='USA free-shipping']"));
		}
		else
			return false;
	}

	public static boolean personalisedGifts() throws Exception
	{
		if(isWebElementPresent(personalized_gifts))
		{
			verifyStep(clickAndWait(personalized_gifts),"Click on Personalized gifts");
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean selectFlowersnCakes()
	{
		return click(linkFlower_and_cake);
	}

	public static void selectNextButton() throws Exception
	{
		executeStep(click(By.xpath("//div[@id='edp-default']/button[text()='Next']")),"Click on Next button");

	}

	public static boolean selectOptionFromBirthday(String recipient) throws Exception
	{
		pause(3000);
		WebElement element=getDriver().findElement(By.xpath("(//ul[@id='cards-default']//div[@class='card z-depth-1 hoverable'])[position()=1]"));
		Actions clicker = new Actions(getDriver());
		clicker.moveToElement(element);
		clicker.click(getDriver().findElement(By.xpath("//div[@class='card-name-w']/p[text()='Birthday Gifts']//p[contains(text(),'"+recipient+"')]"))).click().perform();
		//hoverElement(By.xpath("(//ul[@id='cards-default']//div[@class='card z-depth-1 hoverable'])[position()=1]"));
		pause(3000);
		return click(By.xpath("//div[@class='card-name-w']/p[text()='Birthday Gifts']//p[contains(text(),'"+recipient+"')]"));
	}

	public static boolean selectitemFromCard() throws InterruptedException
	{
		Actions builder = new Actions(getDriver()); 

		WebElement we = getDriver().findElement(By.xpath("(//ul[@id='cards-default']/li)[position()=1]"));

		builder.moveToElement(we).build().perform();
		pause(5000);
		return click((By.xpath("(//div[@class='card-image'])[position()=3]//p[contains(text(),'Men (20-50)')]")));
	}

	public static  boolean selectHomeProduct()
	{
		return click(By.xpath("//p[text()='Home and Living']"));
	}

	public static boolean selectFashionNLifestyle()
	{
		return click(By.xpath("//p[text()='Fashion and Lifestyle']"));
	}

	public static boolean clickOnCartLogo() throws InterruptedException
	{
		return clickAndWait(cartIcon);
	}

	public static boolean emptyCartItems() throws Exception
	{
		try
		{
			if(isWebElementVisible(By.className("e-cart-msg")))
			{

			}
		}
		catch(Exception e)
		{
			int size=getListElements(listCartItems).size();
			logStep("Empty cart. Total items# " +size);
			for(int i=0;i<=size-1;i++)
			{
				getWebElement(CartPage.listCartItems).click();
				pause(2000);
				getWebElement(By.xpath("//ul[@class='cart-items-list']/li//a[contains(.,'Remove')]")).click();
				pause(2000);
			}
		}
		

		pause(2000);
		return true;


	}
}