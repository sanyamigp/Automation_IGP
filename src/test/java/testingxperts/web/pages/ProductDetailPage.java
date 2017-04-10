package testingxperts.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ProductDetailPage extends HomePage{
	public static By txtPinCode = By.xpath("//*[@id='pincode']");

	public static boolean zoomOnImage() throws InterruptedException
	{
		//WebElement hoverimage=getDriver().findElement(By.xpath("(//div[contains(@class,'intrinsic')]/img)[position()='1']"));
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
		
		
//		JavascriptExecutor js = (JavascriptExecutor)getDriver();
//		js.executeScript("arguments[0].onmouseover()",hoverimage);
		//hoverElement(hoverelement);

		return verifyDisplayAndEnable(By.xpath("//div[@class='zoomWindow']"));	
	}

	public static boolean verifyZoomWindow()
	{
		return getDriver().findElement(By.xpath("//div[@class='zoomWindow']")).isDisplayed();
	}

	public static void mouseHoverJScript(By hoverelement) {
		try {
			if (getDriver().findElement(hoverelement).isDisplayed())
					{
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor)getDriver()).executeScript(mouseOverScript,
						hoverelement);
					} 
			else 
			{
				System.out.println("Element was not visible to hover " + "\n");

			}
		}
		catch (Exception e)
		{
			System.out.println("Element with " + hoverelement
					+ "is not attached to the page document"
					+ e.getStackTrace());
		} 
	}
}
