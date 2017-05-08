package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;

/**
 * @author TX
 *
 */
public class SendMail {
	
	
	protected static final Properties PROP = new Properties();
	public static final String USERNAME =  PROP.getProperty("USERNAME");
	public static final String PASSWORD = PROP.getProperty("PASSWORD");
	public static final String EMAILTO = GlobalUtil.getCommonSettings().getEmailIds();
	public static final String EMAILTOCC = PROP.getProperty("EMAILTOCC");
	public static  String STARTTLS;
	public static final String HOST = PROP.getProperty("HOST");
	public static final String PORT ="465";
	
	public static final String SOCKETFACTORYCLASS = PROP.getProperty("socketFactoryClass");
	public static final String FALLBACK = PROP.getProperty("fallback");
	public static final String PATH = null;
	public static final String MODULENAME = null;
	public static final int INDEXOFCOMMA = 0;
	public static final String USERFULLNAME = null;
	public static final String EMAIL_REGEX = "[a-z0-9\\_\\-\\.]+@[a-z0-9\\_\\-\\.]+\\.[a-z]+";
	public static final String REPORT_PATH="/ExecutionReports/ExecutionReports";
	public static final String DIR_PATH="user.dir";
	public static final String BLANK_VARIABLE="";
	//protected static final Properties PROP=System.getProperties();
	
	private SendMail()
	{
		
	}
	
	

	/**
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public  static void sendEmailToClient() throws IOException, MessagingException {
		String mailPropertiesFile = System.getProperty(DIR_PATH) + "/src/main/resources/ConfigFiles/mail.properties";
		PROP.load(new FileInputStream(mailPropertiesFile));

		//final String subject = PROP.getProperty("subject");

		PROP.put("mail.smtp.user", "abc");
		PROP.put("mail.smtp.HOST", "smtp.gmail.com");
		PROP.put("mail.smtp.auth", "true");

		if (!"".equals(PORT)) {
			PROP.put("mail.smtp.port", PORT);
			PROP.put("mail.smtp.socketFactory.port", PORT);
		}

		if (!"".equals(STARTTLS))
			PROP.put("mail.smtp.starttls.enable", STARTTLS);

		if (!"".equals("javax.net.ssl.SSLSocketFactory"))
			PROP.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		if (!"".equals(false))
			PROP.put("mail.smtp.socketFactory.fallback", false);

		Session session = Session.getDefaultInstance(PROP, null);
		session.setDebug(false);


			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(USERNAME, PROP.getProperty("userFullName")));
			//msg.setSubject(subject);

			if (!"".equals(EMAILTOCC)) {

				if (EMAILTO.contains(",")) {
					String[] multipleEmailTo = EMAILTO.split(",");
					for (int j = 0; j < multipleEmailTo.length; j++) {
						if (j == 0)
							msg.addRecipient(Message.RecipientType.TO, new InternetAddress(multipleEmailTo[j]));
						else
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress(multipleEmailTo[j]));
					}

				} else {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAILTO));
				}
			}

			

			else if (EMAILTOCC.equals(BLANK_VARIABLE) || EMAILTOCC == null)  {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAILTO));
			}

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("Hi client, \n Please find Email Report for suite:- "
					+ Utility.getValue("SuiteName") + " \n \n \n Thanks & Regards \n Test Engineer");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (new File(System.getProperty(DIR_PATH) + REPORT_PATH).exists()) {
				Utility.delDirectory(new File(System.getProperty(DIR_PATH) + REPORT_PATH));
			}

			if (GlobalUtil.getCommonSettings().getHtmlReport().contains("Y")) {
				copyDirectoryData("HtmlReport", "HtmlReport");
			}

			if (GlobalUtil.getCommonSettings().getXlsReport().contains("Y")) {
				copyDirectoryData("ExcelReport", "ExcelReport");
			}

			if (GlobalUtil.getCommonSettings().getTestLogs().contains("Y")) {
				copyDirectoryData("Logs", "Logs");
			}

			Utility.createZipFile();

			messageBodyPart = new MimeBodyPart();
			String path = System.getProperty(DIR_PATH) + "/ExecutionReports/ExecutionReports.zip";
			DataSource source = new FileDataSource(path);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ExecutionReports.zip");
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, USERNAME, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			Utility.delDirectory(new File(System.getProperty(DIR_PATH) + "/ExecutionReports/ExecutionReports"));
	 }
		
	

	/**
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectoryData(String sourceDir, String targetDir) throws IOException {
		File srcDir = new File(System.getProperty(DIR_PATH) + "/ExecutionReports/" + sourceDir);
		File destDir = new File(System.getProperty(DIR_PATH) + "/ExecutionReports/ExecutionReports/" + targetDir);
		FileUtils.copyDirectory(srcDir, destDir);
	}

}