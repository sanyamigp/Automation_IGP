package testingxperts.web.pages;

import org.openqa.selenium.By;





public class PaymentPage extends HomePage{
	public enum PaymentOptions { CREDIT_CARD, DEBIT_CARD, NET_BANKING, WALLETS,PAYPAL}
	public static String paymentoption;
	public static By listPaymentMethod = By.xpath("//ul[@class='payment-method-list']");
	public static By txtTotalAmountPaybleLibe = By.xpath("//div[@class='payment-header']/span[contains(.,'Total amount payable')]");


	public static boolean isPaymentPageLoaded(){
		return isWebElementPresent(listPaymentMethod);
	}

	public static boolean totalAmountPayableIsDisplayed(){
		return isWebElementVisible(txtTotalAmountPaybleLibe);
	}
	public static String getTotalAmountPayableLine(){
		return getElementText(txtTotalAmountPaybleLibe);
	}

	public static void clickPaymentOption(PaymentOptions paymentOpt ) throws Exception{

		switch(paymentOpt){
		case CREDIT_CARD :

			executeStep(click(By.xpath("//li[@data-target='credit-card']")), "Click Debit card");
			break;
		case DEBIT_CARD :
			executeStep(click(By.xpath("//li[@data-target='debit-card']")), "Click Debit card");
			break;
		case NET_BANKING :
			executeStep(click(By.xpath("//li[@data-target='net-banking']")), "Click Net Banking");
			break;
		case WALLETS :
			executeStep(click(By.xpath("//li[@data-target='wallets']")), "Click Wallets");
			break;	
		case PAYPAL :
			executeStep(click(By.xpath("//li[@data-target='paypal']")), "Click Paypal");
			break;
		}
	}

	public static boolean verifyPaymentOptionIsDisplayed(PaymentOptions paymentOpt ){
		String option="";
		switch(paymentOpt){
		case CREDIT_CARD :
			option ="Credit Card";
			break;
		case DEBIT_CARD :
			option ="Debit Card";
			break;
		case NET_BANKING :
			option ="Net Banking";
			break;
		case WALLETS :
			option ="Wallets";
			break;	
		case PAYPAL :
			option =" Paypal";
			break;
		}
		String xpath =String.format("//div[contains(@class,'payment-method-wrapper')]/ul/li[contains(.,'%s')][contains(@class,'active')]", option);
		return  isWebElementPresent(By.xpath(xpath));
	}

	public static void verifyCreditCardInfoForm() throws Exception{
		clickPaymentOption(PaymentOptions.CREDIT_CARD);
		pause(1000);


	}

	public static void verifyDebitCardInfoForm() throws Exception{
		clickPaymentOption(PaymentOptions.DEBIT_CARD);
		pause(1000);
	}

	public static boolean verifyNetBankingPage() throws Exception
	{
		clickPaymentOption(PaymentOptions.NET_BANKING);
		return isWebElementVisible(By.xpath("//p[text()='Select from popular banks']"));
	}
	
	public static boolean verifyNetBankingoptions(String bankName)
	{
		return isWebElementPresent(By.xpath("//label[@for='"+bankName+"']"));
	}

	public static void inputCreditCardNumber(String number) throws Exception{
		executeStep(inputText(By.xpath("//*[@id='credit-card']//input[@name='ccnum']"), number), "Input credit card number: "+number);
	}

	public static boolean verifyCardTypeIsMasterCard() throws Exception{
		return isWebElementPresent(By.xpath("//*[@id='credit-card']//i[contains(@class,'mastercard')]"));
	}

	public static boolean netBankingOptions(String popularbanks) throws InterruptedException
	{
		if(isWebElementVisible(By.xpath("//label[@for='"+popularbanks+"']")))
		{
			return click(By.xpath("//label[@for='"+popularbanks+"']"));
			
		}
		else
			return false;	
	}
	
	public static boolean verifynetbankingdropdown()
	{
		if(isWebElementPresent(By.xpath("//select[@id='nb-bank-name']")))
		{
		   click(By.xpath("//select[@id='nb-bank-name']"));
		   return true;
		}
		else
			return false;
	}
	
	public static boolean netbankingdropdownfields(String popularbanks)
	{
		if(isWebElementPresent(By.xpath("//select[@id='nb-bank-name']")))
		{
			click(By.xpath("//select[@id='nb-bank-name']"));
			return selectByVisibleText(By.xpath("//select[@id='nb-bank-name']"),popularbanks );
		}
		else
			return false;	
	}
	//To verify that payment should be displayed inside MakePayment button.
	public static boolean verifyMakePaymentbutton(String modeOfPayment )
	{

		return isWebElementVisible(By.xpath("//div[@id='"+modeOfPayment+"']//button[@type='submit']"));
	}

	public static boolean clickonPaymentbutton(String modeOfPayment) throws Exception
	{
		click(By.xpath("//div[@id='"+modeOfPayment+"']//button[@type='submit']"));
		if (!getCurrentUrl().contains("https://www.igp.com/checkout#chkpayment"))
			return true;

		else
			return false;
	}

	public static boolean verifyAmountPayable()
	{

		int totalamountPayable=Integer.parseInt(getElementText(By.xpath("//span[@class='final-value']")));
		int makePayment=Integer.parseInt(getElementText(By.xpath("//form[@id='d-card-form']//span[@class='number final-value']")));
		if(totalamountPayable==makePayment)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean verifyInvalidCVVNumber(String CVVnumber) throws InterruptedException
	{
		writeInInputCharByChar(By.xpath("//form[contains(@id,'d-card-form')]//input[@placeholder='cvv']"), Constants.INVALID_CVV);
		clickAndWait(By.xpath("//div[@id='debit-card']//button"));
		if(isWebElementVisible(By.xpath("//input[contains(@class,'error')][@placeholder='cvv']")))
			return true;
		else
			return false;
	}




}
