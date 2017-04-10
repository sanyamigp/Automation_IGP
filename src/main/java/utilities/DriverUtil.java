package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This DriverUtil class refer to browsers, os details, browser versions and
 * will close all browsers
 *
 */

public class DriverUtil {
	
	public static final String IE = "IE";
	public static final String CHROME = "Chrome";
	private static Map<String, WebDriver> drivers = new HashMap<>();
	public static final WebDriver driver = null;
	private static HashMap<String, String> checkLogin = new HashMap<>();
	/**
	 * will use this getting browser(chrome, ie, ff)
	 * @param browserName
	 * @return
	 */
	private DriverUtil(){
		
	}
	/**
	 * @param browserName
	 * @return
	 */
	public static WebDriver getBrowser(String browserName) {
		WebDriver browser;
		if (browserName.equalsIgnoreCase(CHROME)) {
			// Write code for chrome
			browser = drivers.get(browserName);
			if (browser == null) {
				File chromeExecutable = new File(ConfigReader.getValue("ChromeDriverPath"));
				System.setProperty("webdriver.chrome.driver", chromeExecutable.getAbsolutePath());
				browser = new ChromeDriver();
				drivers.put("Chrome", browser);
			} // End if
		} else if (browserName.equalsIgnoreCase(IE)) {
			// Write code for chrome
			browser = drivers.get(browserName);
			if (browser == null) {
				File ieExecutable = new File(ConfigReader.getValue("IEDriverPath"));
				System.setProperty("webdriver.ie.driver", ieExecutable.getAbsolutePath());
				DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
				capabilitiesIE.setCapability("ie.ensureCleanSession", true);
				capabilitiesIE.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				browser = new InternetExplorerDriver(capabilitiesIE);
				drivers.put("IE", browser);
				checkLogin.put(browserName, "Y");
			}

		} else {
			// Getting Firefox Browser
			browser = drivers.get("Firefox");
			if (browser == null) {
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				File geckoExecutable = new File(ConfigReader.getValue("GeckoDriverPath"));
				System.out.println(geckoExecutable.getAbsolutePath());
				  System.setProperty("webdriver.gecko.driver",geckoExecutable.getAbsolutePath());
				  cap.setCapability("marionette", true);
				browser = new FirefoxDriver(cap);
				drivers.put("Firefox", browser);
			}
		}
		browser.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		//browser.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		browser.manage().deleteAllCookies();
		browser.manage().window().maximize();
		return browser;
	}// End of function
	/**
	 * will get browser type and version
	 * @param browser
	 * @param cap
	 * @return
	 */
	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) {
		String browserversion;
		String windows = "Windows";
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			LogUtil.infoLog(DriverUtil.class, uAgent);
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains(windows)) {
				browserversion = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf(windows) - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browserversion = "11.0";
			} else {
				browserversion = "0.0";
			}
		} else {
			// Browser version for Firefox and Chrome
			browserversion = cap.getVersion();
		}
		String browserversion1 = browserversion.substring(0, browserversion.indexOf('.'));
		return browsername + " " + browserversion1;
	}
	/**
	 * will get operating system information 
	 * @return
	 */
	
	/**
	 * close all browsersw
	 * @return
	 */
	public static void closeAllDriver() {
		LogUtil.infoLog(DriverUtil.class, "Closing Browsers");
		
		drivers.entrySet().forEach(key->
			{
				key.getValue().quit();
			key.setValue(null);
			}
		
		
	);
		
	}
}// End class
