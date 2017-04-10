package testingxperts.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



public class ProductDetailPage extends HomePage {
	public static By txtPinCode = By.xpath("//*[@id='pincode']");
	public static By btnpinCheck=By.id("pinCheck");
	public static By btnColorSelection =By.xpath("//*[contains(@class,'color-selector')]");
	public static By btnPersonalizeNow = By.xpath("//button[contains(.,'PERSONALIZE NOW')]");


	public static boolean verifyShippingOptions()
	{
		if(isWebElementVisible(By.linkText("Shipping within India")) && isWebElementVisible(By.linkText("International Shipping")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	public static boolean clickShoppingwithIndia()
	{
		return click(By.linkText("Shipping within India"));
	}

	public static boolean verifypincode(String pincode) throws Exception
	{
		writeInInputCharByChar(txtPinCode, pincode);
		click(btnpinCheck);

		if(pincode.length()>6 ||pincode.contains("^[0-9_]"))
		{
			return isWebElementPresent(By.xpath("//input[contains(@class,'pincode error')]"));
		}

		else 
		{
			return isWebElementVisible(txtPinCode);
		}

	}

	public static boolean verifydeliverylocation(String pincode) throws InterruptedException
	{
		writeInInputCharByChar(txtPinCode, pincode);
		clickAndWait(btnpinCheck);
		if(isWebElementPresent(By.xpath("//p[@id='pin-error-text'][@class='text-dblack']")))
			return true;
		else
			return false;
	}

	public static boolean clickinternationalshipping() throws InterruptedException
	{
		if(isWebElementPresent(By.xpath("//li[contains(@class,'active')][@data-target='#international-del']")))
		{
			return true;
		}
		else if(!isWebElementPresent(By.xpath("//li[contains(@class,'active')][@data-target='#international-del']")))
		{
			clickAndWait(By.linkText("International Shipping"));
			return true;
		}
		else
			return false;
	}

	public static boolean enterInvalidcountry(String country) throws Exception
	{

		doubleClick(By.id("country"));
		executeStep(writeInInputCharByChar(By.id("country"), country),"Input country");
		pause(10000);
		clickAndWait(By.id("countryCheck"));
		pause(3000);
		
		if(isWebElementVisible(By.xpath("//input[contains(@class,'error')]")))
		{
			return true;	
		}
		else
			return false;

	}
	
	public static boolean notDeliverableProducts(String country) throws InterruptedException, Exception
	{
		doubleClick(By.id("country"));
		executeStep(writeInInputCharByChar(By.id("country"), country),"Input country");
		pause(10000);
		clickAndWait(By.id("countryCheck"));
		pause(3000);
		
		if(isWebElementPresent(By.xpath("//div[contains(text(),'This product is not shippable to ')]")))
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}

	public static boolean verifyCountryEntered(String country) throws InterruptedException, Exception
	{
		doubleClick(By.id("country"));
		executeStep(writeInInputCharByChar(By.id("country"), country),"Input country");
		pause(10000);
		clickAndWait(By.id("countryCheck"));
		pause(3000);
		return isWebElementPresent(By.xpath("//div[@class='row no-margin intl-del-help']"));
	}

	public static boolean deliverychargePresent(String countryName) throws InterruptedException, Exception
	{
		verifyCountryEntered(countryName);
		if(getElementText(By.xpath("//span[@class='intl text-red number']")).contains("Rs"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	public static boolean verifyimageangles() throws InterruptedException
	{

		if(isWebElementPresent(By.xpath("//div[contains(@class,'thumbnail-img')][position()=1]")))

		{

			for(int i=1;i<=5;i++)
			{
				if(isWebElementPresent(By.xpath("//div[contains(@class,'thumbnail-img')][position()="+i+"]")))
				{
					clickAndWait(By.xpath("//div[contains(@class,'thumbnail-img')][position()="+i+"]"));
					isWebElementVisible(By.xpath("//div[@class='intrinsic intrinsic-square']/img[position()="+i+"]"));
					pause(2000);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean zoomOnImage() throws InterruptedException
	{
		WebElement Image = getDriver().findElement(By.xpath("//div[@class='zoomContainer']"));

		//Get width of element.
		int ImageWidth = Image.getSize().getWidth();
		System.out.println("Image width Is "+ImageWidth+" pixels");

		//Get height of element.
		int ImageHeight = Image.getSize().getHeight();        
		System.out.println("Image height Is "+ImageHeight+" pixels");

		Thread.sleep(5000);

		Actions clicker = new Actions(getDriver());

		clicker.moveToElement(Image).moveByOffset(ImageWidth/2, ImageHeight/2).click().perform();

		Thread.sleep(2000);
		return verifyDisplayAndEnable(By.xpath("//div[@class='zoomWindow']"));	
	}

	public static boolean verifyNamendPrice(int index)
	{

		String xpathOfItem = String.format("//div[contains(@class,'edp-group-wrapper')][position()=3]//div[@class='slick-track']/div[position()=%d]", 4+index);
		String Name=String.format("//div[contains(@class,'edp-group-wrapper')][position()=3]//div[@class='slick-track']/div[position()=%d]//p[@class='product-name']/a",4+index);
		String price=String.format("//div[contains(@class,'edp-group-wrapper')][position()=3]//div[@class='slick-track']/div[position()=%d]//p[@class='product-price']/span",4+index);
		By item=By.xpath(xpathOfItem);
		String productName= getElementText(By.xpath(Name));
		String productprice=getElementText(By.xpath(price));
		click(item);
		String itemName=getElementText(By.xpath("//h1[@class='pdp-product-name']"));
		String itemPrice=getElementText(By.xpath("//span[contains(@class,'product-price')]"));
		if(itemName.contentEquals(productName) && itemPrice.contentEquals(productprice))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	public static boolean verifySizeoption()
	{
		return isWebElementVisible(By.xpath("//div[@class='customize-attr size-select']"));

	}

	public static boolean needHelpOption(String reason,String query) throws Exception
	{
		if(isWebElementVisible(By.linkText("Need Help ?")))
		{
			executeStep(click(By.linkText("Need Help ?")),"Click on Need Help column");
			selectByVisibleText(By.id("other_query5"), reason);
			writeInInputCharByChar(By.id("contact-umsg"), query);
			clickAndWait(By.id("send-btn"));
			verifyStep(isWebElementVisible(By.xpath("//div[text()='Thanks for the support. Your message have been sent. You will be contacted soon by our customer service.']")), "Query message received");
			return true;
		}
		else
			return false;
	}

	public static boolean verifyBreadcrumbs()
	{
		return isWebElementVisible(By.className("bcrumb"));
	}

	public static boolean verifyProductName()
	{
		return isWebElementPresent(By.xpath("//h1[@class='pdp-product-name']"));
	}

	public static boolean verifyProductPrice()
	{
		return isWebElementVisible(By.xpath("//span[contains(@class,'number product-price')]"));
	}

	public static boolean verifyProductImage()
	{
		return isWebElementPresent(By.className("zoomContainer"));
	}

	public static boolean errorMessageforFlowersnCakes()
	{
		return isWebElementPresent(By.xpath("//p[text()='We currently do not support international deliveries for this product']"));
	}

	public static boolean verifyDeliveryOptions() throws InterruptedException
	{
		if(isWebElementVisible(By.xpath("//label[contains(@class,'sameday-amt')]")))
		{
			clickAndWait(By.xpath("//label[contains(text(),'Fixed date delivery')]"));
			clickAndWait(By.xpath("//label[contains(text(),'Fixed time delivery')]"));
			clickAndWait(By.xpath("//label[contains(text(),'Midnight delivery')]"));
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean enterFixedDateDelivery(int date,String deliveryMonth) throws InterruptedException
	{
		clickAndWait(By.xpath("//label[contains(text(),'Fixed date delivery')]"));
		clickAndWait(By.id("datepicker-fixed-date"));
		String part1="//table[contains(@aria-controls,'fixed-date')]//tbody//td/div[text()='";
		String part2="']";
		
		List<WebElement> calendarDates=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else if(deliveryMonth=="Present Month")
		{
			for(int i=0;i<calendarDates.size();i++)
			{
				if(calendarDates.get(i).isDisplayed())
					return true;
			}
			
			
				getDriver().findElement(By.xpath(part1+date+part2)).click();
				pause(3000);
			
		}
		else if(deliveryMonth=="Next Month")
		{
			clickAndWait(By.xpath("//div[contains(@id,'fixed-date')]//div[@title='Next month']"));
			List<WebElement> calendarDate=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
			for(int i=0;i<calendarDate.size();i++)
			{
				if(calendarDate.get(i).isDisplayed())
					return true;
			}
			getDriver().findElement(By.xpath(part1+date+part2)).click();
			pause(3000);
		}
		return isWebElementVisible(By.id("fixed-date-help-text"));
		
	}
	
	public static boolean enterFixedTimeDelivery(int date,String deliveryMonth) throws InterruptedException
	{
		clickAndWait(By.xpath("//label[contains(text(),'Fixed time delivery')]"));
		clickAndWait(By.id("datepicker-fixed-time"));
		String part1="//table[contains(@aria-controls,'fixed-time')]//tbody//td/div[text()='";
		String part2="']";
		List<WebElement> calendarDates=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else if(deliveryMonth=="Present Month")
		{
			for(int i=0;i<calendarDates.size();i++)
			{
				if(calendarDates.get(i).isDisplayed())
					return true;
			}
			
			
				getDriver().findElement(By.xpath(part1+date+part2)).click();
				pause(3000);
			
		}
		else if(deliveryMonth=="Next Month")
		{
			clickAndWait(By.xpath("//div[contains(@id,'fixed-time')]//div[@title='Next month']"));
			List<WebElement> calendarDate=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
			for(int i=0;i<calendarDate.size();i++)
			{
				if(calendarDate.get(i).isDisplayed())
					return true;
			}
			getDriver().findElement(By.xpath(part1+date+part2)).click();
			pause(3000);
		}
		
		selectByIndex(By.xpath("//select[@class='number time-picker']"), 1);
		return isWebElementVisible(By.id("fixed-help-text"));
		
	}
	
	public static boolean enterMidNightDelivery(int date,String deliveryMonth) throws InterruptedException
	{
		clickAndWait(By.xpath("//label[contains(text(),'Midnight delivery')]"));
		clickAndWait(By.id("datepicker-midnight"));
		String part1="//table[contains(@aria-controls,'midnight')]//tbody//td/div[text()='";
		String part2="']";
		List<WebElement> calendarDates=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
		if(!getDriver().findElement(By.xpath(part1+date+part2)).isDisplayed())
		{

			return false; 
		}
		else if(deliveryMonth=="Present Month")
		{
			for(int i=0;i<calendarDates.size();i++)
			{
				if(calendarDates.get(i).isDisplayed())
					return true;
			}
			
			
				getDriver().findElement(By.xpath(part1+date+part2)).click();
				pause(3000);
			
		}
		else if(deliveryMonth=="Next Month")
		{
			clickAndWait(By.xpath("//div[contains(@id,'midnight')]//div[@title='Next month']"));
			List<WebElement> calendarDate=getDriver().findElements(By.xpath("//div[@aria-disabled='true'][text()='"+date+"']"));
			for(int i=0;i<calendarDate.size();i++)
			{
				if(calendarDate.get(i).isDisplayed())
					return true;
			}
			getDriver().findElement(By.xpath(part1+date+part2)).click();
			pause(3000);
		}
		return isWebElementVisible(By.id("midnight-help-text"));
	}
	
	public static boolean verifyProductDescriptionTab()
	{
		return isWebElementVisible(By.id("prod-desc"));
	}
	
	public static boolean headerVisible()
	{
		if(isWebElementVisible(By.xpath("//h3[text()='Similar Gift Recommendations']")))
		{
			 isWebElementVisible(By.xpath("(//div[@class='slick-track'])/div[position()=5]"));
			 return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean footerContent() throws Exception
	{
		executeStep(isWebElementVisible(By.xpath("//div[contains(@class,'stats-row')]")),"Check stats content");
		executeStep(isWebElementVisible(By.xpath("(//div[contains(@class,'newsletter')])[position()=2]")), "Check footer content");
		return true;
	}
	public static boolean verifyclikBuyNowbutton(){
		
		return click(CartPage.btnBuyNow);
		
	}
	
	public static boolean verifyProductVariety() throws InterruptedException
	{
		if(isWebElementPresent(By.xpath("//div[contains(@class,'upsell-container')]")))
		{
			List<WebElement> varities=getListElements(By.xpath("//div[contains(@class,'upsell-container')]/a"));
			
			for(int i=1;i<=varities.size();i++)
			{
				click(By.xpath("(//div[contains(@class,'upsell-container')]/a)[position()="+i+"]"));
				pause(3000);
			}
			return true;
		}
		else if(!isWebElementPresent(By.xpath("//div[contains(@class,'upsell-container')]")))
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public static boolean chooseBaseType() throws InterruptedException
	{
		if(isWebElementVisible(By.xpath("//div[contains(@class,'sel-peripheral')]")))
		{
			List<WebElement> baseTypes=getListElements(By.xpath("//div[@class='col s3 ppl-check']"));
			for(int i=1;i<=baseTypes.size();i++)
			{
				clickAndWait(By.xpath("(//div[@class='col s3 ppl-check'])[position()="+i+"]"));
			}
			return true;
		}
		
		else if(!isWebElementVisible(By.xpath("//div[contains(@class,'sel-peripheral')]")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}
