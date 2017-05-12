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
		String xpathOfItem = String.format("(//div[@class='slick-track'])[position()=1]//li[%d]", 4 + index);
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
			if(isWebElementVisible(By.xpath("(//input[contains(@class,'pers-input-text')])")))
			{
				try
				{
					writeInInputCharByChar(By.xpath("(//input[contains(@class,'pers-input-text')])"), "Round-shaped");

				}
				catch(Exception e)
				{

				}

			}

			else if(isWebElementVisible(By.xpath("//div[@class='user-input-container']//input[contains(@class,'pers-input-text')]")))
			{
				try
				{
					writeInInputCharByChar(By.xpath("//div[@class='user-input-container']//input[contains(@class,'pers-input-text')]"), "Round-shaped");
				}
				catch(Exception e)
				{

				}
			}
		}
		catch(Exception e)
		{

		}

		return true;
	}
	public static boolean attachPersonalizedImage() throws AWTException, InterruptedException
	{
		try
		{

			clickAndWait(By.xpath("//input[@placeholder='Select Image']"));

			pause(5000);
			fileUpload("Personalized.jpg");
		}
		catch(Exception e)
		{

		}


		return true;
	}

	public static boolean personalizedMethod() throws AWTException, InterruptedException, Exception
	{
		try
		{
			if(isWebElementVisible(ProductDetailPage.btnPersonalizeNow))
			{

				executeStep(click(ProductDetailPage.btnPersonalizeNow),"Click PERSONALIZE NOW");
				verifyStep(PersonalizedGiftsPage.isPersonalizedGiftsOptionsOpened(),"Personalized Page opened");
				pause(2000);

				if(isWebElementVisible(By.xpath("//div[@class='step-details flex-disp']/div")))
				{
					List<WebElement> tabs=getListElements(By.xpath("//div[@class='step-details flex-disp']/div"));
					for(int i=1;i<=(tabs.size()-1);i++)
					{

						executeStep(PersonalizedGiftsPage.attachPersonalizedImage(), "Attach Image");
						executeStep(PersonalizedGiftsPage.enterPersonalizedText("Gift Hampers"), "Text");
						executeStep(click(PersonalizedGiftsPage.btnDone),"Click Done");

					}
				}

			}
		}
		catch(Exception e)
		{
			try
			{
				executeStep(PersonalizedGiftsPage.attachPersonalizedImage(), "Attach Image");
				executeStep(PersonalizedGiftsPage.enterPersonalizedText("Gift Hampers"), "Attach Image");
				executeStep(click(PersonalizedGiftsPage.btnDone),"Click Done");
			}
			catch(Exception e1)
			{

			}
		}



		pause(5000);
		return true;
	}

}//End class
