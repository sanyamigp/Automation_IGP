package testingxperts.web.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PersonalizedGiftsPage extends HomePage{
	public static By btnDone = By.id("personalize-done");
	public static By inputMessage = By.xpath("//input[contains(@class,'pers-input-text')]");
	public static By btnEditPersonalize = By.xpath("//button[contains(.,'EDIT PERSONALIZATION')]");


	public enum Recipient {
		MEN, WOMEN
	}
	//*[@id='cards-landing']/li



	public static boolean isPersonalizedGiftsPageOpened(){
		return isWebElementVisible(By.xpath("//h1[contains(.,'Personalized Gifts')]"));

	}

	public static boolean isPersonalizedGiftsOptionsOpened(){
		return isWebElementVisible(By.xpath("//p[contains(.,'Personalisation options:')]"));
	}




	public static boolean selectItem(int index){
		//First index is 5th So adding 4+index
		String xpathOfItem = String.format("(//*[@class[contains(.,'product-item')]]/div[1]/a)[position()=%d]", index);
		By item=By.xpath(xpathOfItem);
		return click(item);
	}



	public static void selectItmeByRecipient(Recipient recipient,int itemIndex) throws Exception{
		clickAndWait(By.xpath("//div[@data-selected-type='recipient']"));

		switch(recipient){
		case MEN:
			executeStep(click(By.xpath("(//ul[@class='s-item-list'])[position()=1]//a[contains(@href,'personalized-gifts-for-men')]")),
					"Recipient - MEN")
			;
			break;
		case WOMEN:
			executeStep(click(By.xpath("(//ul[@class='s-item-list'])[position()=1]//a[contains(@href,'personalized-gifts-for-women')]")),"Recipient - WOMEN");
			break;
		}
		executeStep(selectItem(itemIndex), "Select item# "+ itemIndex);


	}

	public static boolean enterPersonalizedText(String personalizedtext) throws InterruptedException
	{
		try
		{

			if(isWebElementPresent(By.xpath("(//input[contains(@class,'pers-input-text')])[position()=1]")))
			{
				
					writeInInputCharByChar(By.xpath("//input[contains(@class,'pers-input-text')]"), "C-shaped");

			}
		}
		catch(Exception e)
		{
			return true;
		}
		return true;

	}



	public static boolean attachPersonalizedImage() throws AWTException, InterruptedException
	{

		try
		{
			if(isWebElementPresent(By.xpath("//button[text()='SELECT IMAGE']")))
			{
				
				
					clickAndWait(By.xpath("//button[text()='SELECT IMAGE']"));
					pause(2000);
					fileUpload("PersonalizedImage.jpg");
				
			}
		}
		catch(Exception e)
		{

		}
		return true;
	}

}//End class
