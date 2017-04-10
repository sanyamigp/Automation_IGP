package testingxperts.web.pages;

import org.openqa.selenium.By;

public class CountriesPage extends HomePage {

	public static void isCountriesPageLoaded() throws Exception
	{
		executeStep(isWebElementPresent(By.xpath("//h3[text()='Top Countries']")), "Navigated to Worldwide Page");

	}

	public static boolean verifyCountryentered() throws InterruptedException
	{
		writeInInputCharByChar(By.xpath("//input[@placeholder='Enter a country']"),"Singapore");
		return click(By.linkText("Singapore"));
	}

	public static boolean selectItemFromList(int index)
	{
		String xpathOfItem = String.format("//div[contains(@class,'edp-group-wrapper')][position()=3]//div[@class='slick-track']/div[position()=%d]", 4+index);
		By item=By.xpath(xpathOfItem);
		return click(item);
	}
	
	public static boolean freeShippingProducts(int index)
	{
		String xpathOfItem = String.format("//div[contains(@class,'edp-group-wrapper')][position()=1]//div[@class='slick-track']/div[position()=%d]", 4+index);
		By itemName=By.xpath(xpathOfItem);
		return click(itemName);
	}

}
