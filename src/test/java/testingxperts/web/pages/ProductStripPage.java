package testingxperts.web.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.ConfigReader;

public class ProductStripPage extends HomePage {


	public static void openStripPage(String stripName)
	{
		navigateToUrl(ConfigReader.getValue("BASE_URL")+stripName);
	}

	public static boolean isStripPageLoaded(String stripName)
	{
		return isWebElementVisible(By.xpath("//h1[contains(text(),'"+stripName+"')]"));
	}

	public static boolean selectionBarforFestival()
	{
		if(isWebElementVisible(By.xpath("//div[contains(@class,'selection-bar')]")))
		{
			List<WebElement> barItems=getDriver().findElements(By.xpath("//div[contains(@class,'selection-bar')]//li[@class='refine-list-item']"));
			for(int i=1;i<=barItems.size();i++)
			{
				isWebElementPresent(By.xpath("//div[contains(@class,'selection-bar')]//li[@class='refine-list-item'][position()="+i+"]"));
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyProductStrips()
	{
		if(isWebElementVisible(By.xpath("//h2[@class='gateway-c-title']")))
		{
			List<WebElement> productStrips=getDriver().findElements(By.xpath("//h2[@class='gateway-c-title']"));
			for(int i=1;i<=productStrips.size();i++)
			{
				isWebElementVisible(By.xpath("(//h2[@class='gateway-c-title'])[position()="+i+"]"));

			}
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean productsInProductStrips()
	{
		if(isWebElementPresent(By.xpath("//child::div[div[(h2[@class='gateway-c-title'])]][position()=1]/following-sibling::div[position()=1]")))
		{
			
				isWebElementVisible(By.xpath("//child::div[div[(h2[@class='gateway-c-title'])]][position()=1]/following-sibling::div[position()=1]//div[contains(@class,'slick-track')]/li[position()=5]"));
				
			
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyBackToTopbutton()
	{
		return isWebElementPresent(By.xpath("//p[text()='Back to top']"));
	}

	public static boolean verifyTopFooter()
	{
		return isWebElementPresent(By.xpath("//div[@class='top-footer']"));
	}



}