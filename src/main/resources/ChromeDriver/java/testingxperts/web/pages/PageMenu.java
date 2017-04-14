package testingxperts.web.pages;

import org.openqa.selenium.By;

public class PageMenu extends HomePage {
	public static By menuCart = By.xpath(".//*[@class='action-links']/li[3]/a");
	public static By searchmenu=By.xpath("//input[contains(@class,'search-bar')]");
	public static void enterSearchText(String searchtext) throws Exception
	{
		if(isWebElementVisible(searchmenu))
		{
			verifyStep(writeInInputCharByChar(searchmenu, searchtext), "Verify search text");
			getDriver().navigate().back();
			pause(3000);
			clickAndWait(By.xpath("//div[contains(@class,'search-icon')]"));
		}
	}
}
