package testingxperts.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.ConfigReader;
import utilities.KeywordUtil;

public class HomePage extends KeywordUtil {
	
	public enum GiftBy { FLOWER_AND_CAKE, GIFT_HAMPER,GIFT_CATEGORY,PERSONALIZED_GIFTS,GIFT_RECIPIENT}  
	public static By linkFlower_and_cake= By.xpath("//p[text()='Flowers & Cakes']");
	public static By gifts_categories=By.xpath("//p[text()='Gifts by Product Category']");
	public static By gifts_recipients=By.xpath("//p[text()='Gifts by Recipient & Occasion']");
	public static By gifts_hamper=By.xpath("//div[contains(@class,'icon')]/p[text()='Gift Hampers']");
	public static By personalized_gifts=By.xpath("//div[contains(@class,'icon')]/p[text()='Personalized Gifts']");
	
	public static By logo = By.xpath("//*[@id='absolute-header']/div[1]/div/a/img");
	
	
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
	
	public static boolean selectItemEditorPick(int index){
		//First index is 5th So adding 4+index
		String xpathOfItem = String.format("(//*[@class[contains(.,'product-item')]]/div[1]/a)[position()=%d]", 4+index);
		 By item=By.xpath(xpathOfItem);
		 return click(item);
	}
	
	public static boolean selectCardName(String cardname)
	{
		return click(By.xpath("//p[@class='card-name'][text()='"+cardname+"']"));
	}
	
	public static boolean browseBycategory(String category)
	{
		return click(By.xpath("//p[@class='u-case'][text()='"+category+"']"));
	}
	
	public static boolean selectBestSellingGifts(int index)
	{
		String xpathOfItem = String.format("(//*[@class[contains(.,'product-item')]]/div[1]/a)[position()=%d]", 4+index);
		 By item=By.xpath(xpathOfItem);
		 return click(item);
	}
	
	public static boolean bestunder999(int index)
	{
		String xpathOfItem = String.format("(//section[@id='tpWrapper']//div[@class='slick-track']/div[position()=%d]", 4+index);
		By item=By.xpath(xpathOfItem);
		 return click(item);
	}
	
	public static boolean selectHomePageimage()
	{
		String homepagewindow=getDriver().getWindowHandle();
		return click(By.xpath("//div[contains(@class,'slick-list')]//a[@target]"));
	}
	
	
	
	public static boolean IsHoliPageopened()
	{
		String holipageWindow=getDriver().getWindowHandle();
		getDriver().switchTo().window(holipageWindow);
		return isWebElementPresent(By.xpath("//div[contains(text(),'Holi')]"));
	}
	
	

}