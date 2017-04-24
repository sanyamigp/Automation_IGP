package testingxperts.web.pages;

import org.openqa.selenium.By;

import utilities.ConfigReader;

public class ProductList extends HomePage{

	public static By flowersdisplayed=By.xpath("//span[text()='Flowers']");
	public static By stickyheader=By.id("header");
	
	public static boolean selectItem(int item)
	{
		if(isWebElementPresent(By.xpath("(//div[@class='slick-track']/li)[position()=5]")))
		{
			click(By.xpath("(//div[@class='slick-track']/li)[position()='"+(item+4)+"']"));
			return true;
		}

		else
		{
			return false;
		}

	}
	
	public static boolean searchList(int item)
	{
		if(isWebElementPresent(By.xpath("(//ul[@id='product-grid']//li[contains(@class,'product-grid')])[position()=5]")))
		{
			click(By.xpath("(//ul[@id='product-grid']//li[contains(@class,'product-grid')])[position()='"+(item+4)+"']"));
			return true;
		}

		else
		{
			return false;
		}
	}
	
	public static boolean selectCakeItem(int item)
	{
		if(isWebElementPresent(By.xpath("((//div[@class='slick-track'])[position()=2]/div[contains(@class,'product-grid-item col s3')])[position()=5]")))
		{
			click(By.xpath("((//div[@class='slick-track'])[position()=2]/div[contains(@class,'product-grid-item col s3')])[position()='"+(item+4)+"']"));
			return true;
		}

		else
		{
			return false;
		}

	}

	public static void openBirthdayListPage() throws Exception {
		navigateToUrl(ConfigReader.getValue("BIRTHDAY_CARD"));

	}
	
	public static boolean isBirthdayPageLoaded()
	{
		return isWebElementVisible(By.xpath("//h1[contains(text(),'Birthday Gifts')]"));
	}
	
	public static void openGiftsForWomenPage()
	{
		navigateToUrl(ConfigReader.getValue("GIFTS_FOR_WOMEN"));

	}
	
	public static void openAnniversaryGifts()
	{
		navigateToUrl(ConfigReader.getValue("ANNIVERSARY_GIFTS"));
	}
	
	public static boolean isAnniversaryPageLoaded()
	{
		return isWebElementPresent(By.xpath("//h1[contains(text(),'Anniversary Gifts for')]"));
	}
	
	public static boolean isUSAPageLoaded()
	{
		return isWebElementVisible(By.xpath("//h1[contains(text(),'USA')]"));
	}
	
	public static boolean isWomenPageLoaded()
	{
		return isWebElementVisible(By.xpath("//h1[contains(text(),'Gifts for')]"));
	}

	public static boolean isFlowersnCakesPageloaded()
	{
		return isWebElementVisible(flowersdisplayed);
	}

	public static boolean headerVisible() throws Exception
	{
		if(isWebElementVisible(stickyheader))
		{
			executeStep(isWebElementVisible(By.xpath("//input[contains(@class,'search-bar')]")), "SearchBox is present");
			executeStep(isWebElementVisible(By.xpath("//div[contains(@class,'slist-wrapper')]")),"Chat Element is visible");
			executeStep(clickAndWait(By.xpath("//div[contains(@class,'dial-pad')]")), "Click on Customer Support menu");
			pause(10000);
			for(int item=1;item<5;item++)
			{
				isWebElementPresent(By.xpath("(//header[@id='sticky-header']//div[contains(@class,'col t2 text-center')])[position()="+item+"]"));
			}
			clickAndWait(By.xpath("//div[contains(@class,'dial-pad')]"));
			executeStep(isWebElementVisible(By.xpath("//div[contains(@class,'top-actions')]/div[contains(@class,'s-cart')]")), "Cart button is visible");
			executeStep(isWebElementVisible(By.id("user-menu")), "User button is visible");
			executeStep(clickAndWait(By.id("ham-icon")), "Click on Sliding window");
			executeStep(isWebElementVisible(By.id("ham-menu")), "Sliding window is visble");
			executeStep(click(By.id("close-ham-menu")), "Click on close icon");
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean selectionbarItems() throws Exception
	{
		if(isWebElementVisible(By.xpath("//div[contains(@class,'col t7')]")))
		{
			executeStep(isWebElementVisible(By.xpath("//div[@data-selected-type='recipient']")), "Recipient filter is visible.");
			executeStep(isWebElementVisible(By.xpath("//div[@data-selected-type='relationship']")), "Relationship filter is visble.");
			executeStep(isWebElementVisible(By.xpath("//div[@data-selected-type='occasion']")), "Occasion filter is visble");
			executeStep(isWebElementVisible(By.xpath("//div[@data-selected-type='personality']")), "Personality filter is visible.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean verifyProductsVisibility()
	{
		return isWebElementVisible(By.xpath("//ul[@id='product-grid']/li[1]"));
	}
	
	public static boolean verifyBottomFooter()
	{
		return isWebElementPresent(By.xpath("//div[@class='row no-margin stats-row']"));
	}
	public static boolean verifyContentInFooter()
	{
		return isWebElementPresent(By.className("footer-bottom-strip"));
	}

	public static boolean showProductsButton()
	{
		return isWebElementPresent(By.xpath("//a[contains(text(),'Show More products')]"));
	}
	
	public static boolean verifyBreadCrumb()
	{
		return isWebElementPresent(By.xpath("//ol[@typeof='BreadcrumbList']"));
	}
	
}
