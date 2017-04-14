package testingxperts.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CardListPage extends HomePage {

	
	public static boolean verifyCardsVisible()
	{
		if(isWebElementVisible(By.xpath("//div[@class='card-group-wrapper']//li")))
		{
			List<WebElement> cardItems=getDriver().findElements(By.xpath("//div[@class='card-group-wrapper']//li"));
			for(int i=1;i<=cardItems.size();i++)
			{
				isWebElementPresent(By.xpath("(//div[@class='card-group-wrapper']//li)[position()="+i+"]"));
			}
			return true;			
		}
		else
		{
			return false;
		}
	}
	
	public static boolean verifyPageheadings()
	{
		if(isWebElementVisible(By.xpath("//h3[contains(@class,'title')]")))
		{

			List<WebElement> cardItems=getDriver().findElements(By.xpath("//h3[contains(@class,'title')]"));
			for(int i=1;i<=cardItems.size();i++)
			{
				isWebElementPresent(By.xpath("(//h3[contains(@class,'title')])[position()="+i+"]"));
			}
			return true;			
		}
		
		else
		{
			return false;
		}
	}
	
	public static boolean verifyCardProductItems()
	{
		if(isWebElementVisible(By.xpath("(//section[@class='bg-dark'])")))
		{

			List<WebElement> productItems=getDriver().findElements(By.xpath("(//section[@class='bg-dark'])"));
			for(int i=1;i<=productItems.size();i++)
			{
				isWebElementPresent(By.xpath("(//section[@class='bg-dark'])[position()="+i+"]//div[@class='slick-track']/div[position()=5]"));
			}
			return true;			
		}
		
		else
		{
			return false;
		}
	}
	
	
	
}
