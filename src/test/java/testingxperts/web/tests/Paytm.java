package testingxperts.web.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Paytm {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "D:\\Software\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://paytm.com/shop/p/apple-macbook-mjy32hn-a-notebook-intel-dual-core-m-5y10c-8-gb-256-gb-12-inch-mac-os-x-yosemite-grey-LAPAPPLE-MACBOOSMAR4447130F9BF42?src=brandstore&tracker=%7C%7C%7C%7C@apple-Apple%20MacBooks%7C25755%7C1%7C");
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//img[@class='_3v_O']"))).build().perform();

		Thread.sleep(10000);
		WebElement ele_zoom = driver.findElement(By.xpath("//div[@class='_1-Zc']"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(ele_zoom));

		if(ele_zoom.isDisplayed())
			System.out.println("The zoom is visible");  
		else
			System.err.println("The zoom isn't visible!!");
	}


}


